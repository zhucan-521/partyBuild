package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.MsgUpInfoDTO;
import com.egovchina.partybuilding.partybuild.entity.MsgUpInfoQueryBean;
import com.egovchina.partybuilding.common.entity.SysDept;
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

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

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
     *
     * @param
     * @return
     */
    @Override
    public int insertMsgUpInfo(MsgUpInfoDTO msgUpInfoDTO) {
        TabPbMsgUpInfo tabPbMsgUpInfo = generateTargetCopyPropertiesAndPaddingBaseField(msgUpInfoDTO, TabPbMsgUpInfo.class, false);
        int retVal = tabPbMsgUpInfoMapper.insertSelective(tabPbMsgUpInfo);
        Long id = tabPbMsgUpInfo.getId();
        if (retVal > 0) {
            msgUpInfoDTO.setId(id);
            retVal += modifyAttachment(msgUpInfoDTO);
        }
        return retVal;
    }

    /**
     * 维护附件
     *
     * @param msgUpInfoDTO
     * @return
     */
    private int modifyAttachment(MsgUpInfoDTO msgUpInfoDTO) {
        List<TabPbAttachment> attachments = msgUpInfoDTO.getAttachments();
        if (CollectionUtil.isNotEmpty(attachments)) {
            return iTabPbAttachmentService.intelligentOperation(attachments,
                    msgUpInfoDTO.getId(), AttachmentType.msgUpInfoType);
        }
        return 0;
    }

    /**
     * 返回登录人的姓名，组织名称，上级组织名称，上级组织专干人姓名,党组织名称，党组织id
     *
     * @return TabPbMsgUpInfoDto
     */
    @Override
    public MsgUpInfoVO returnUpMember(Long realDeptId) {
        //如果没有党组织则是上报组织为自已组织（没有替别的组织上报）
        if (realDeptId == null) {
            realDeptId = UserContextHolder.getOrgId();
        }
        TabPbMsgUpInfo tabPbMsgUpInfo = new TabPbMsgUpInfo();
        //党组织（真实上报组织）
        tabPbMsgUpInfo.setRealDeptId(realDeptId);
        SysDept realSysDept = tabSysDeptMapper.selectAloneByPrimaryKey(realDeptId);
        tabPbMsgUpInfo.setRealDeptName(realSysDept.getName());
        //上报时间
        tabPbMsgUpInfo.setUpTime(new Date());
        //上报人姓名
        tabPbMsgUpInfo.setUpUserId(UserContextHolder.getUserId());
        tabPbMsgUpInfo.setUpUsername(UserContextHolder.getUserName());
        //上报人组织名称
        Long upDeptId = UserContextHolder.getOrgId();
        SysDept upDept = tabSysDeptMapper.selectAloneByPrimaryKey(upDeptId);
        tabPbMsgUpInfo.setUpDeptId(upDeptId);
        tabPbMsgUpInfo.setUpDeptName(upDept.getName());
        //获取接收组织和接收组织的专干人员
        MsgUpInfoVO msgUpInfoVO = tabPbMsgUpInfoMapper.getReceiveDeptAndWorker(realDeptId);
        BeanUtil.copyPropertiesIgnoreNull(tabPbMsgUpInfo, msgUpInfoVO);
        return msgUpInfoVO;
    }

    /**
     * 条件查询信息报送列表
     *
     * @param msgUpInfoQueryBean
     * @return
     */
    @Override
    public PageInfo<MsgUpInfoVO> selectMsgUpInfoList(MsgUpInfoQueryBean msgUpInfoQueryBean, Page page) {
        Long rangeDeptId = msgUpInfoQueryBean.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            msgUpInfoQueryBean.setRangeDeptId(UserContextHolder.getOrgId());
        }
        PageHelper.startPage(page);
        List<MsgUpInfoVO> list = tabPbMsgUpInfoMapper.selectVoActive(msgUpInfoQueryBean);
        return new PageInfo<>(list);
    }

    /**
     * 条件查询接受信息列表
     *
     * @param msgUpInfoQueryBean
     * @param page
     * @return
     */
    @Override
    public PageInfo<MsgUpInfoVO> selectReceiveMsgUpInfoList(MsgUpInfoQueryBean msgUpInfoQueryBean, Page page) {
        PageHelper.startPage(page);
        List<MsgUpInfoVO> list = tabPbMsgUpInfoMapper.selectActiveVoRec(msgUpInfoQueryBean);
        return new PageInfo<>(list);
    }

    /**
     * 修改
     *
     * @param msgUpInfoDTO
     * @return
     */
    @Override
    public int editMsgUpInfo(MsgUpInfoDTO msgUpInfoDTO) {
        TabPbMsgUpInfo tabPbMsgUpInfoUpdate = generateTargetCopyPropertiesAndPaddingBaseField(msgUpInfoDTO, TabPbMsgUpInfo.class, true);
        int flag = tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfoUpdate);
        if (flag > 0) {
            flag += modifyAttachment(msgUpInfoDTO);
        }
        return flag;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteMsgUpInfo(Long id) {
        TabPbMsgUpInfo tabPbMsgUpInfo = new TabPbMsgUpInfo();
        tabPbMsgUpInfo.setId(id);
        tabPbMsgUpInfo.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbMsgUpInfo);
        return tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfo);
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @Override
    public MsgUpInfoVO getMsgUpInfoById(Long id) {
        MsgUpInfoVO msgUpInfoVO = new MsgUpInfoVO();
        BeanUtil.copyPropertiesIgnoreNull(tabPbMsgUpInfoMapper.selectWithAboutById(id), msgUpInfoVO);
        return msgUpInfoVO;
    }

    /**
     * 审核
     *
     * @param
     * @return
     */
    @Override
    public int auditMsgUpInfo(MsgUpInfoDTO msgUpInfoDTO) {
        //审核人组织
        msgUpInfoDTO.setAuditDeptId(UserContextHolder.getOrgId());
        SysDept auditSysDept = tabSysDeptMapper.selectAloneByPrimaryKey(UserContextHolder.getOrgId());
        msgUpInfoDTO.setAuditDeptName(auditSysDept.getName());
        //审核人姓名
        msgUpInfoDTO.setAuditUserId(UserContextHolder.getUserId());
        msgUpInfoDTO.setAuditUsername(UserContextHolder.getUserName());
        //审核时间
        msgUpInfoDTO.setAuditTime(new Date());
        TabPbMsgUpInfo tabPbMsgUpInfo = new TabPbMsgUpInfo();
        BeanUtil.copyPropertiesIgnoreNull(msgUpInfoDTO, tabPbMsgUpInfo);
        return tabPbMsgUpInfoMapper.updateByPrimaryKeySelective(tabPbMsgUpInfo);
    }

}
