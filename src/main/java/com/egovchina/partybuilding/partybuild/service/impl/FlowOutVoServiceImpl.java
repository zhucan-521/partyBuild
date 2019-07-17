package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.FlowOutMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.FlowOutMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowInMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowOutMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.FlowOutVoService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.util.UserTagType;
import com.egovchina.partybuilding.partybuild.vo.FlowOutMemberVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FlowOutVoServiceImpl implements FlowOutVoService {

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Autowired
    TabPbFlowOutMapper tabPbFlowOutMapper;

    @Autowired
    TabPbFlowInMapper tabPbFlowInMapper;

    @Autowired
    UserTagService userTagService;

    /**
     * 流出党员列表条件查询
     *
     * @param flowOutMemberQueryBean
     * @return
     */
    @Override
    public PageInfo<FlowOutMemberVO> getFlowOutVoList(FlowOutMemberQueryBean flowOutMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        List<FlowOutMemberVO> list = tabPbFlowOutMapper.selectActiveFlowOutVo(flowOutMemberQueryBean);
        return (PageInfo<FlowOutMemberVO>) new PageInfo(list);
    }

    /**
     * 登记流出党员信息(市外流动党员录入)
     *
     * @param flowOutMemberDto
     * @return
     */
    @Override
    public int addFlowOutMemberDTO(FlowOutMemberDTO flowOutMemberDto) {
        //填充用户表 流入/流出（党组织名称、id字段和组织联系人、电话）8个字段  如果市外则直接添加该党员
        SysUser sysUser = tabSysUserMapper.selectUserByIdCardNo(flowOutMemberDto.getIdCardNo());
        if (sysUser == null) {
            sysUser = new SysUser()
                    .setIdCardNo(flowOutMemberDto.getIdCardNo())
                    .setGender(flowOutMemberDto.getGender())
                    .setRealname(flowOutMemberDto.getUsername())
                    .setUsername(flowOutMemberDto.getUsername())
                    .setFlowStatus(CommonConstant.FLOW)
                    .setIdentityType(flowOutMemberDto.getIdentityType());
            PaddingBaseFieldUtil.paddingBaseFiled(sysUser);
            tabSysUserMapper.insertSelective(sysUser);
            sysUser = tabSysUserMapper.selectUserByIdCardNo(flowOutMemberDto.getIdCardNo());
        }
        Long userId = sysUser.getUserId();
        SysUser sysUserUpdate = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(flowOutMemberDto, SysUser.class, true);
        sysUserUpdate.setUserId(userId);
        sysUserUpdate.setFlowFromOrgId(flowOutMemberDto.getOrgId());
        sysUserUpdate.setFlowToOrgId(flowOutMemberDto.getFlowToOrgnizeId());
        tabSysUserMapper.updateByPrimaryKeySelective(sysUserUpdate);
        // 校验 （处于待报到、已流出、已流入的人无法再次流出）
        if (tabPbFlowOutMapper.checkTabPbFlowOutExistsByUserId(userId)) {
            throw new BusinessDataCheckFailException("该党员已经处于流出状态，无法继续流出");
        }
        //填充流出表 流动状态（如果是流出市外流动状态为已经流出否则为待报到）
        flowOutMemberDto.setUserId(userId);
        if (flowOutMemberDto.getFlowToOrgnizeId() == null) {
            flowOutMemberDto.setFlowOutState(CommonConstant.FLOWED_OUT);
            sysUser.setFlowStatus(CommonConstant.FLOW);
            //添加流动标识
            userTagService.addUserTag(userId, UserTagType.FLOW);
        } else {
            flowOutMemberDto.setFlowOutState(CommonConstant.PENDING_REPORT);
        }
        TabPbFlowOut tabPbFlowOutInsert = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(flowOutMemberDto, TabPbFlowOut.class, false);
        tabPbFlowOutInsert.setFlowToOrgnizeName(flowOutMemberDto.getFlowToOrgName());//设置流入党组织名称
        tabPbFlowOutMapper.insertSelective(tabPbFlowOutInsert);
        //插入流入表 流动状态（如果是手动登记则为已流入）
        TabPbFlowIn tabPbFlowIn = new TabPbFlowIn()
                .setFlowOutId(tabPbFlowOutInsert.getFlowOutId())
                .setFlowInState(CommonConstant.PENDING_REPORT) //设置状态为待报到
                .setUserId(userId)  //设置流入人
                .setOldPlace(flowOutMemberDto.getFlowOutPlace())  //设置原地
                .setFlowInType(flowOutMemberDto.getFlowOutType()) //设置流出类型
                .setFlowInRange(flowOutMemberDto.getOutIndustry()) //设置流动范围
                .setFlowInReason(flowOutMemberDto.getFlowOutReason()) //设置流动原因
                .setOldOrgnizeCode(flowOutMemberDto.getFlowToOrgnizeCode()) //设置流动证
                .setOldOrgnizeName(flowOutMemberDto.getFlowFromOrgName()) //原党组织名称
                .setOldOrgnizePhone(flowOutMemberDto.getFlowFromOrgPhone()) //原党组织联系电话
                .setOldOrgnizeContactor(flowOutMemberDto.getFlowFromOrgContactor())//原党组织联系人
                .setLostTime(flowOutMemberDto.getLostTime()) //失去联系时间
                .setLinkStatus(flowOutMemberDto.getLinkStatus()); //失联情况
        //流入党组织
        if (flowOutMemberDto.getFlowToOrgnizeId() == null) {
            flowOutMemberDto.setFlowOutState(CommonConstant.FLOWED_OUT); //已经流出
            userTagService.addUserTag(userId, UserTagType.FLOW); //添加流出标识
        } else {
            tabPbFlowIn.setOldOrgnizeId(flowOutMemberDto.getOrgId());
            tabPbFlowIn.setOrgId(flowOutMemberDto.getFlowToOrgnizeId());
        }
        //流出党组织
        if (flowOutMemberDto.getOrgId() != null) {
            tabPbFlowIn.setOldOrgnizeId(flowOutMemberDto.getOrgId());//设置原党组织
        }
        //如果有录入日期则为手动录入
        if (flowOutMemberDto.getFlowInDate() != null) {
            //设置流入日期
            tabPbFlowIn.setFlowInDate(flowOutMemberDto.getFlowInDate());
            //已录入
            tabPbFlowIn.setFlowInState(CommonConstant.FLOWED_IN);
        }
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbFlowIn);
        return tabPbFlowInMapper.insertSelective(tabPbFlowIn);
    }

    /**
     * 修改流出党员dto
     *
     * @param
     * @return
     */
    @Override
    public int updateFlowOutMember(FlowOutMemberDTO flowOutMemberDto) {
        //不让他修改流出组织
        flowOutMemberDto.setOrgId(null);
        flowOutMemberDto.setFlowFromOrgName(null);
        //修改流入表
        TabPbFlowIn tabPbFlowIn = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(flowOutMemberDto, TabPbFlowIn.class, true)
                .setOldPlace(flowOutMemberDto.getFlowOutPlace())
                .setFlowInType(flowOutMemberDto.getFlowOutType())
                .setFlowInRange(flowOutMemberDto.getOutIndustry())
                .setFlowInReason(flowOutMemberDto.getFlowOutReason())
                .setOldOrgnizeCode(flowOutMemberDto.getFlowToOrgnizeCode());
        tabPbFlowInMapper.updateByFlowOutIdKeySelective(tabPbFlowIn);
        //修改流出表
        TabPbFlowOut tabPbFlowOutUpdate = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(flowOutMemberDto, TabPbFlowOut.class, true);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOutUpdate);
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @Override
    public FlowOutMemberVO getFlowOutMember(Long id) {
        return tabPbFlowOutMapper.findFlowOutVoById(id);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {
        TabPbFlowOut tabPbFlowOut = tabPbFlowOutMapper.selectByPrimaryKey(id);
        int flag = 0;
        if (tabPbFlowOut.getFlowOutState().equals(CommonConstant.PENDING_REPORT)) {
            tabPbFlowOut.setDelFlag(CommonConstant.STATUS_DEL);
            tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
            SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(tabPbFlowOut.getUserId());
            //用户结束流动
            sysUser.setFlowStatus(CommonConstant.NORMAL);
            flag = tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
            if (flag > 0) {
                //取消流动标识
                userTagService.delete(sysUser.getUserId(), UserTagType.FLOW);
                //取消用户表流入流出党组织
                tabPbFlowInMapper.cancelSysUserFlowStaus(sysUser.getUserId());
                TabPbFlowIn tabPbFlowIn = new TabPbFlowIn();
                Long flowInId = tabPbFlowInMapper.getFlowOutIdByFlowInId(id);
                tabPbFlowIn.setFlowInId(flowInId);
                tabPbFlowIn.setDelFlag(CommonConstant.STATUS_DEL);
                tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
            }
            return flag;
        }
        throw new BusinessDataCheckFailException("只有待报到的流动党员才可以删除");
    }

}
