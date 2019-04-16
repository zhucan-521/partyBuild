package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TabPbMsgUpInfoDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgUpInfo;
import com.egovchina.partybuilding.partybuild.repository.TabPbMsgUpInfoMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.TabPbMsgUpInfoSerivce;
import com.egovchina.partybuilding.partybuild.system.entity.SysDept;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TabPbMsgUpInfoSerivceImpl implements TabPbMsgUpInfoSerivce {

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
     *
     * @param tabPbMsgUpInfo
     * @return
     */
    @Override
    public int insert(TabPbMsgUpInfoDto tabPbMsgUpInfo) {
        int retVal = tabPbMsgUpInfoMapper.insertSelective(tabPbMsgUpInfo);
        if (retVal > 0) {
            retVal += modifyAttachment(tabPbMsgUpInfo);
        }
        return retVal;
    }

    /**
     * 维护附件
     *
     * @param tabPbMsgUpInfo
     * @return
     */
    private int modifyAttachment(TabPbMsgUpInfoDto tabPbMsgUpInfo) {
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
    public TabPbMsgUpInfo selectAffter(Long realDeptId) {
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
    public PageInfo<TabPbMsgUpInfoDto> selectActive(TabPbMsgUpInfoDto dto, Page page) {
        PageHelper.startPage(page);
        List<TabPbMsgUpInfoDto> list = tabPbMsgUpInfoMapper.selectActive(dto);
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
    public PageInfo<TabPbMsgUpInfoDto> selectActiveRec(TabPbMsgUpInfoDto dto, Page page) {
        PageHelper.startPage(page);
        List<TabPbMsgUpInfoDto> list = tabPbMsgUpInfoMapper.selectActiveRec(dto);
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
    public int update(TabPbMsgUpInfoDto tabPbMsgUpInfoDto) {
        int retVal = tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfoDto);
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
    public int delete(Long id) {
        TabPbMsgUpInfo tabPbMsgUpInfo = new TabPbMsgUpInfo();
        tabPbMsgUpInfo.setId(id);
        tabPbMsgUpInfo.setDelFlag("1");
        return tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfo);
    }

    /**
     * 单个查询
     * @param id
     * @return
     */
    @Override
    public TabPbMsgUpInfoDto selectActiveOne(Long id) {
        return tabPbMsgUpInfoMapper.selectWithAboutById(id);
    }


    /**
     * 审核
     * @param
     * @return
     */
    @Override
    public int auditResult(TabPbMsgUpInfoDto tabPbMsgUpInfoDto) {
        //审核人组织
        tabPbMsgUpInfoDto.setAuditDeptId(UserContextHolder.getOrgId());
        SysDept AuditSysDept=tabSysDeptMapper.selectAloneByPrimaryKey(UserContextHolder.getOrgId());
        tabPbMsgUpInfoDto.setAuditDeptName(AuditSysDept.getName());
        //审核人姓名
        tabPbMsgUpInfoDto.setAuditUserId(UserContextHolder.getUserIdLong());
        tabPbMsgUpInfoDto.setAuditUsername(UserContextHolder.getUserName());
        //审核时间
        tabPbMsgUpInfoDto.setAuditTime(new Date());
        return tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfoDto);
    }


}
