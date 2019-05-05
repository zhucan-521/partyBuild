package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.FlowOutMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.FlowOutMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowInMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbFlowOutMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.FlowOutVoService;
import com.egovchina.partybuilding.partybuild.vo.FlowOutMemberVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.var;
import org.springframework.beans.BeanUtils;
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


    /**
     * 流出党员列表条件查询
     * @param dto
     * @return
     */
    @Override
    public PageInfo<FlowOutMemberVO> getFlowOutVoList(FlowOutMemberQueryBean dto , Page page) {
        PageHelper.startPage(page);
        List<FlowOutMemberVO> list=tabPbFlowOutMapper.selectActiveFlowOutVo(dto);
        return (PageInfo<FlowOutMemberVO>) new PageInfo(list);
    }


    /**
     * 登记流出党员信息(市外流动党员录入)
     * @param flowOutMemberDto
     * @return
     */
    @Override
    @PaddingBaseField
    public int addFlowOutMemberDTO(FlowOutMemberDTO flowOutMemberDto) {
        //修改用户表信息
        SysUser sysUser=tabSysUserMapper.selectUserByIdCardNo(flowOutMemberDto.getIdCardNo());
        if(null==sysUser){
            //外市流动党员登记
            SysUser newSysUser=new SysUser()
                    .setRealname(flowOutMemberDto.getUsername())
                    .setGender(flowOutMemberDto.getGender())
                    .setIdCardNo(flowOutMemberDto.getIdCardNo())
                    .setFlowStatus(41207L);
            tabSysUserMapper.insertSelective(newSysUser);
            sysUser=tabSysUserMapper.selectUserByIdCardNo(flowOutMemberDto.getIdCardNo());
        }
        Long userId=sysUser.getUserId();
        if(null !=tabPbFlowOutMapper.checkTabPbFlowOutExixtByUserId(userId)){
            throw new BusinessDataCheckFailException("该党员已经处于流出状态，无法继续流出！");
        }
        BeanUtils.copyProperties(flowOutMemberDto,sysUser);
        flowOutMemberDto.setUserId(userId);
        //流入党组织 没有流入组织id则流出到市外
        if(flowOutMemberDto.getFlowOutPlace()!=null){
            sysUser.setFlowToOrgId(flowOutMemberDto.getFlowOutPlace());
            //插入流出表 设置状态为待报道
            flowOutMemberDto.setFlowOutState(59413L);
         }else{
            //插入流出表 设置状态为已经流出
            flowOutMemberDto.setFlowOutState(59414L);
            sysUser.setFlowStatus(41207L);
        }
        //流出党组织
        if(flowOutMemberDto.getOrgId()!=null){
            sysUser.setFlowFromOrgId(flowOutMemberDto.getOrgId()) ;
        }
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        TabPbFlowOut tabPbFlowOutInsert=new TabPbFlowOut();
        BeanUtil.copyPropertiesIgnoreNull(flowOutMemberDto,tabPbFlowOutInsert);
        tabPbFlowOutMapper.insertSelective(tabPbFlowOutInsert);
        //插入流入表
        TabPbFlowIn tabPbFlowIn=new TabPbFlowIn()
                   .setFlowOutId(flowOutMemberDto.getFlowOutId())
                   .setFlowInState(59413L) //设置状态为待报道
                   .setUserId(userId)  //设置流入人
                   .setOldPlace(flowOutMemberDto.getFlowToUnitName())  //设置原地
                   .setFlowInType(flowOutMemberDto.getFlowOutType()) //设置流出类型
                   .setFlowInRange(flowOutMemberDto.getOutIndustry()) //设置流动范围
                   .setFlowInReason(flowOutMemberDto.getFlowOutReason().toString()) //设置流动原因
                   .setOldOrgnizeCode(flowOutMemberDto.getFlowToOrgnizeCode());  //设置流动证
       //流入党组织
        if(flowOutMemberDto.getFlowOutPlace()!=null){
            tabPbFlowIn.setOrgId(flowOutMemberDto.getFlowOutPlace()); //设置流入组织
        }else{
            flowOutMemberDto.setFlowOutState(59414L); //已经流出
        }
        //流出党组织
        if(flowOutMemberDto.getOrgId()!=null){
            tabPbFlowIn.setOldOrgnizeId(flowOutMemberDto.getOrgId()) ;//设置原党组织
        }
        if(flowOutMemberDto.getFlowInDate()!=null){  //如果有录入日期则为手动录入
            tabPbFlowIn.setFlowInDate(flowOutMemberDto.getFlowInDate());//设置流入日期
            tabPbFlowIn.setFlowInState(59415L);//已录入
        }
        return  tabPbFlowInMapper.insertSelective(tabPbFlowIn);
    }

    /**
     * 修改流出党员dto
     * @param
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int updateFlowOutMember(FlowOutMemberDTO flowOutMemberDto) {
        //不让他修改流出组织
        flowOutMemberDto.setOrgId(null);
        flowOutMemberDto.setFlowFromOrgName(null);
        //修改用户表
        SysUser sysUser=tabSysUserMapper.selectUserByIdCardNo(flowOutMemberDto.getIdCardNo());
        BeanUtil.copyPropertiesIgnoreNull(flowOutMemberDto,sysUser);
        //流入党组织
        if(flowOutMemberDto.getFlowOutPlace()!=null){
            sysUser.setFlowToOrgId(flowOutMemberDto.getFlowOutPlace());
            sysUser.setFlowToOrgName(tabPbFlowOutMapper.selectDeptNameByDeptId(flowOutMemberDto.getFlowOutPlace()));
        }
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        //修改流入表
        var tabPbFlowIn=new TabPbFlowIn()
                    .setFlowOutId(flowOutMemberDto.getFlowOutId())
                    .setUserId(sysUser.getUserId())
                    .setOldOrgnizeId(flowOutMemberDto.getOrgId())
                    .setOrgId(flowOutMemberDto.getFlowOutPlace())
                    .setOldPlace(flowOutMemberDto.getFlowToUnitName())
                    .setFlowInType(flowOutMemberDto.getFlowOutType())
                    .setFlowInRange(flowOutMemberDto.getOutIndustry())
                    .setFlowInReason(flowOutMemberDto.getFlowOutReason().toString())
                    .setOldOrgnizeCode(flowOutMemberDto.getFlowToOrgnizeCode())
                    .setLostTime(flowOutMemberDto.getLostTime());
        tabPbFlowInMapper.updateByFlowOutIdKeySelective(tabPbFlowIn);
        //修改流出表
        TabPbFlowOut tabPbFlowOutUpdate=new TabPbFlowOut();
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbFlowOutUpdate);
        BeanUtil.copyPropertiesIgnoreNull(flowOutMemberDto,tabPbFlowOutUpdate);
        return tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOutUpdate);
    }


    /**
     *单个查询
     * @param id
     * @return
     */
    @Override
    public FlowOutMemberVO getFlowOutMember(Long id) {
        return tabPbFlowOutMapper.findFlowOutVoById(id);
    }


    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    @PaddingBaseField
    public int delete(Long id) {
        TabPbFlowOut tabPbFlowOut=tabPbFlowOutMapper.selectByPrimaryKey(id);
        int flag=0;
        if(tabPbFlowOut.getFlowOutState()==59413){
            tabPbFlowOut.setDelFlag("1");
            tabPbFlowOutMapper.updateByPrimaryKeySelective(tabPbFlowOut);
            SysUser sysUser=tabSysUserMapper.selectByPrimaryKey(tabPbFlowOut.getUserId());
            //用户结束流动
            sysUser.setFlowStatus(41209L);
            flag=tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
            if(flag>0){
                TabPbFlowIn tabPbFlowIn=new TabPbFlowIn();
                tabPbFlowIn.setDelFlag("1");
                tabPbFlowInMapper.updateByPrimaryKeySelective(tabPbFlowIn);
            }
        }else{
            throw new BusinessDataCheckFailException("只有带报道的流动党员才可以删除！");
        }
        return flag;
    }

}
