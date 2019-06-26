package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.FlowInMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.FlowInMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowInMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowOutMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.FlowInService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.util.UserTagType;
import com.egovchina.partybuilding.partybuild.vo.FlowInMemberVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * 流入党员实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowInServiceImpl implements FlowInService {

    @Autowired
    private TabPbFlowInMapper tabPbFlowInMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbFlowOutMapper tabPbFlowOutMapper;

    @Autowired
    private UserTagService userTagService;

    /**
     * 流入党员列表查询
     *
     * @param
     * @return
     */
    @Override
    public PageInfo<FlowInMemberVO> getFlowInMemberList(FlowInMemberQueryBean flowInMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        List<FlowInMemberVO> tabPbFlowInDtos = tabPbFlowInMapper.selectListByFlowInVo(flowInMemberQueryBean);
        PageInfo<FlowInMemberVO> pageInfo = new PageInfo(tabPbFlowInDtos);
        return pageInfo;
    }

    /**
     * 根据流入id删除流入党员信息
     *
     * @param flowInId
     * @return
     */
    @Override
    public int deleteFlowInMember(Long flowInId) {
        TabPbFlowIn tabPbFlowIn = tabPbFlowInMapper.selectByPrimaryKey(flowInId);
        int flag = 0;
        if (tabPbFlowIn.getFlowInState().equals(CommonConstant.PENDING_REPORT)) {
            tabPbFlowIn.setDelFlag(CommonConstant.STATUS_DEL);
            tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
            SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(tabPbFlowIn.getUserId());
            //用户结束流动
            sysUser.setFlowStatus(CommonConstant.END_FLOW);
            flag = tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
            if (flag > 0) {
                //取消流动标识
                userTagService.delete(sysUser.getUserId(), UserTagType.FLOW);
                //取消用户表流入流出党组织
                tabPbFlowInMapper.cancelSysUserFlowStaus(sysUser.getUserId());
                TabPbFlowOut tabPbFlowOut = new TabPbFlowOut();
                tabPbFlowOut.setDelFlag(CommonConstant.STATUS_DEL);
                tabPbFlowOut.setFlowOutId(tabPbFlowIn.getFlowOutId());
                tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbFlowOut);
            }
            return flag;
        }
        throw new BusinessDataCheckFailException("只有待报到的流动党员才可以删除");
    }

    /**
     * 录入
     *
     * @param
     * @return
     */
    @Override
    @PaddingBaseField
    public int acceptFlowInMember(FlowInMemberDTO flowInMemberDto) {
        //流入表
        TabPbFlowIn tabPbFlowIn = tabPbFlowInMapper.selectByPrimaryKey(flowInMemberDto.getFlowInId());
        if (tabPbFlowIn.getFlowInState().equals(CommonConstant.FLOWED_IN)) {
            throw new BusinessDataCheckFailException("该党员已经录入");
        }
        BeanUtils.copyProperties(flowInMemberDto, tabPbFlowIn);
        //设置状态已流入
        tabPbFlowIn.setFlowInState(CommonConstant.FLOWED_IN);
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
        TabPbFlowOut tabPbFlowOut = new TabPbFlowOut();
        tabPbFlowOut.setFlowOutId(tabPbFlowIn.getFlowOutId());
        //设置状态已流出
        tabPbFlowOut.setFlowOutState(CommonConstant.FLOWED_OUT);
        //修改流入流出党组织联系人和电话  user表
        Long userId = tabSysUserMapper.SelectUserIdByIDcard(flowInMemberDto.getIdCardNo());
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        //设置状态流动
        sysUser.setFlowStatus(CommonConstant.FLOW);
        BeanUtils.copyProperties(flowInMemberDto, sysUser);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        //添加流动标识
        userTagService.addUserTag(sysUser.getUserId(), UserTagType.FLOW);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

    /**
     * 修改
     *
     * @param
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int updateFlowInDto(FlowInMemberDTO flowInMemberDto) {
        //不让修改流入党组织
        flowInMemberDto.setOrgId(null);
        flowInMemberDto.setFlowToOrgName(null);
        Long userId = tabSysUserMapper.SelectUserIdByIDcard(flowInMemberDto.getIdCardNo());
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        BeanUtil.copyPropertiesIgnoreNull(flowInMemberDto, sysUser);
        TabPbFlowIn tabPbFlowIn = tabPbFlowInMapper.selectByPrimaryKey(flowInMemberDto.getFlowInId());
        TabPbFlowOut tabPbFlowOutDto = generateTargetCopyPropertiesAndPaddingBaseField(flowInMemberDto, TabPbFlowOut.class, true);
        tabPbFlowOutDto.setFlowOutId(tabPbFlowIn.getFlowOutId());
        tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOutDto);
        //修改流入流出党组织联系人和电话
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        TabPbFlowIn tabPbFlowInUpdate = generateTargetCopyPropertiesAndPaddingBaseField(flowInMemberDto, TabPbFlowIn.class, true);
        return tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowInUpdate);
    }

    /**
     * 結束流動
     *
     * @param
     * @return
     */
    @Override
    public int returnFlowInMember(FlowInMemberDTO flowInMemberDto) {
        SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(flowInMemberDto.getUserId());
        BeanUtils.copyProperties(flowInMemberDto, sysUser);
        //结束流动
        sysUser.setFlowStatus(CommonConstant.END_FLOW);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        //取消流动标识
        userTagService.delete(sysUser.getUserId(), UserTagType.FLOW);
        //取消用户表流入流出党组织
        tabPbFlowInMapper.cancelSysUserFlowStaus(sysUser.getUserId());
        //返回
        flowInMemberDto.setFlowInState(CommonConstant.RETURNED);
        TabPbFlowIn tabPbFlowIn = generateTargetCopyPropertiesAndPaddingBaseField(flowInMemberDto, TabPbFlowIn.class, true);
        tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
        TabPbFlowOut tabPbFlowOut = tabPbFlowOutMapper.selectByPrimaryKey(flowInMemberDto.getFlowOutId());
        //返回
        tabPbFlowOut.setFlowOutState(CommonConstant.RETURNED);
        //设置返回日期
        tabPbFlowOut.setReturnDate(flowInMemberDto.getReturnDate());
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbFlowOut);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
    }

    /**
     * 根据流入id获取流入党员DTO信息
     *
     * @param flowInId
     * @return
     */
    @Override
    public FlowInMemberVO getFlowInMeberVoById(Long flowInId) {
        return tabPbFlowInMapper.selectFlowInVoByFlowId(flowInId);
    }

}
