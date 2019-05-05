package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.MsgUpInfoDTO;
import com.egovchina.partybuilding.partybuild.entity.MsgUpInfoQueryBean;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgUpInfo;
import com.egovchina.partybuilding.partybuild.repository.TabPbMsgUpInfoMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.MsgUpInfoSerivce;
import com.egovchina.partybuilding.partybuild.vo.MsgUpInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MsgUpInfoSerivceImpl implements MsgUpInfoSerivce {

    @Autowired
    TabPbMsgUpInfoMapper tabPbMsgUpInfoMapper;

    @Autowired
    ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Autowired
    TabSysDeptMapper tabSysDeptMapper;

    /**
     * 信息实体添加
     * @param
     * @return
     */
    @Override
    public int insertMsgUpInfo(MsgUpInfoDTO msgUpInfoDTO) {
        TabPbMsgUpInfo tabPbMsgUpInfo=new TabPbMsgUpInfo();
        BeanUtil.copyPropertiesIgnoreNull(msgUpInfoDTO,tabPbMsgUpInfo);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbMsgUpInfo);
        int retVal = tabPbMsgUpInfoMapper.insertSelective(tabPbMsgUpInfo);
        Long id=tabPbMsgUpInfo.getId();
        if (retVal > 0) {
            msgUpInfoDTO.setId(id);
            retVal += modifyAttachment(msgUpInfoDTO);
        }
        return retVal;
    }

    /**
     * 维护附件
     *
     * @param tabPbMsgUpInfo
     * @return
     */
    private int modifyAttachment(MsgUpInfoDTO tabPbMsgUpInfo) {
        List<TabPbAttachment> attachments = tabPbMsgUpInfo.getAttachments();
        if (CollectionUtil.isNotEmpty(attachments)) {
            return iTabPbAttachmentService.intelligentOperation(attachments,
                    tabPbMsgUpInfo.getId(), AttachmentType.msgUpInfoType);
        }
        return 0;
    }


    /**
     * 返回登录人的姓名，组织名称，上级组织名称，上级组织专干人姓名,党组织名称，党组织id
     *
     * @return TabPbMsgUpInfoDto
     */
    @Override
    public TabPbMsgUpInfo retrnUpMember(Long realDeptId) {
        if(null == realDeptId ){
            realDeptId= UserContextHolder.getOrgId();
        }
        TabPbMsgUpInfo tabPbMsgUpInfo = new TabPbMsgUpInfo();
        //党组织
        tabPbMsgUpInfo.setRealDeptId(realDeptId);
        SysDept realSysDept = tabSysDeptMapper.selectAloneByPrimaryKey(realDeptId);
        tabPbMsgUpInfo.setRealDeptName( realSysDept.getName());
        //上报时间
        tabPbMsgUpInfo.setUpTime(new Date());
        //上报人姓名
        tabPbMsgUpInfo.setUpUserId(UserContextHolder.getUserIdLong());
        tabPbMsgUpInfo.setUpUsername(UserContextHolder.getUserName());
        //上报人组织名称
        Long upDeptId=UserContextHolder.getOrgId();
        SysDept upDept = tabSysDeptMapper.selectAloneByPrimaryKey(upDeptId);
        tabPbMsgUpInfo.setUpDeptId(upDeptId);
        tabPbMsgUpInfo.setUpDeptName(upDept.getName());
        //接受组织名称
        if(null == realSysDept.getParentId()){
           return tabPbMsgUpInfo;
        }
        SysDept recDept = tabSysDeptMapper.selectAloneByPrimaryKey(realSysDept.getParentId().longValue());
        if (null == recDept) {
            return tabPbMsgUpInfo;
        }
        tabPbMsgUpInfo.setRecevieDeptId(recDept.getDeptId().longValue());
        tabPbMsgUpInfo.setRecevieDeptName(recDept.getName());
        //接受人姓名
        SysUser recSysUser = tabPbMsgUpInfoMapper.selectBydeptId(recDept.getDeptId().longValue());
        if (null == recSysUser) {
            return tabPbMsgUpInfo;
        }
        tabPbMsgUpInfo.setRecevieUserId(recSysUser.getUserId().longValue());
        tabPbMsgUpInfo.setRecevieUsername(recSysUser.getUsername());
        return tabPbMsgUpInfo;
    }


    /**
     * 条件查询信息报送列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageInfo<MsgUpInfoVO> selectMsgUpInfoList(MsgUpInfoQueryBean dto, Page page) {
        PageHelper.startPage(page);
        List<MsgUpInfoVO> list = tabPbMsgUpInfoMapper.selectVoActive(dto);
        return new PageInfo<>(list);
    }

    /**
     * 条件查询接受信息列表
     *
     * @param dto
     * @param page
     * @return
     */
    @Override
    public PageInfo<MsgUpInfoVO> selectReceiveMsgUpInfoList(MsgUpInfoQueryBean dto, Page page) {
        PageHelper.startPage(page);
        List<MsgUpInfoVO> list = tabPbMsgUpInfoMapper.selectActiveVoRec(dto);
        return new PageInfo<>(list);
    }


    /**
     * 修改
     *
     * @param tabPbMsgUpInfoDto
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int editMsgUpInfo(MsgUpInfoDTO tabPbMsgUpInfoDto) {
        TabPbMsgUpInfo tabPbMsgUpInfoUpdate=new TabPbMsgUpInfo();
        BeanUtil.copyPropertiesIgnoreNull(tabPbMsgUpInfoDto,tabPbMsgUpInfoUpdate);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbMsgUpInfoUpdate);
        int retVal = tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfoUpdate);
        if (retVal > 0) {
            retVal += modifyAttachment(tabPbMsgUpInfoDto);
        }
        return retVal;
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @PaddingBaseField
    public int deleteMsgUpInfo(Long id) {
        TabPbMsgUpInfo tabPbMsgUpInfo = new TabPbMsgUpInfo();
        tabPbMsgUpInfo.setId(id);
        tabPbMsgUpInfo.setDelFlag("1");
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbMsgUpInfo);
        return tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfo);
    }

    /**
     * 单个查询
     * @param id
     * @return
     */
    @Override
    public MsgUpInfoVO getMsgUpInfoById(Long id) {
          MsgUpInfoVO msgUpInfoVO=new MsgUpInfoVO();
          BeanUtil.copyPropertiesIgnoreNull(tabPbMsgUpInfoMapper.selectWithAboutById(id),msgUpInfoVO);
          return msgUpInfoVO;
    }


    /**
     * 审核
     * @param
     * @return
     */
    @Override
    public int auditMsgUpInfo(MsgUpInfoDTO msgUpInfoDto) {
        //审核人组织
        msgUpInfoDto.setAuditDeptId(UserContextHolder.getOrgId());
        SysDept AuditSysDept=tabSysDeptMapper.selectAloneByPrimaryKey(UserContextHolder.getOrgId());
        msgUpInfoDto.setAuditDeptName(AuditSysDept.getName());
        //审核人姓名
        msgUpInfoDto.setAuditUserId(UserContextHolder.getUserIdLong());
        msgUpInfoDto.setAuditUsername(UserContextHolder.getUserName());
        //审核时间
        msgUpInfoDto.setAuditTime(new Date());
        TabPbMsgUpInfo tabPbMsgUpInfo=new TabPbMsgUpInfo();
        BeanUtil.copyPropertiesIgnoreNull(msgUpInfoDto,tabPbMsgUpInfo);
        return tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfo);
    }


}
