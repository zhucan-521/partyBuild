package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.LeadTeamQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeam;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeamMember;
import com.egovchina.partybuilding.partybuild.repository.TabPbLeadTeamMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbLeadTeamMemberMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.LeadTeamService;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @Author Jiang An
 **/
@Service("leadTeamService")
public class LeadTeamServiceImpl implements LeadTeamService {

    @Autowired
    private TabPbLeadTeamMapper tabPbLeadTeamMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    private TabPbLeadTeamMemberMapper tabPbLeadTeamMemberMapper;

    @Override
    public List<LeadTeamVO> selectLeadTeamVOByCondition(LeadTeamQueryBean queryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbLeadTeamMapper.selectLeadTeamVOByCondition(queryBean);
    }

    @Transactional
    @Override
    public int logicDeleteLeadTeamById(Long leadTeamId) {
        LeadTeamVO leadTeamVO = tabPbLeadTeamMapper.selectLeadTeamVOById(leadTeamId);
        if (leadTeamVO == null) {
            throw new BusinessDataNotFoundException("领导班子信息已删除或有误");
        }
        TabPbLeadTeam delete = new TabPbLeadTeam();
        delete.setLeadTeamId(leadTeamId);
        delete.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(delete);
        int judgment = tabPbLeadTeamMapper.updateByPrimaryKeySelective(delete);
        if (judgment > 0) {
            TabPbLeadTeamMember memberDelete = new TabPbLeadTeamMember();
            memberDelete.setLeadTeamId(leadTeamId);
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(memberDelete);
            judgment += tabPbLeadTeamMemberMapper.logicDeleteByLeadTeamId(memberDelete);
        }
        return judgment;
    }

    @Transactional
    @Override
    public int insertLeadTeam(LeadTeamDTO leadTeamDTO) {
        //判断新增的领导班子在数据库中是否已经存在,如果存在则不能新增
        boolean result = Optional.ofNullable(tabPbLeadTeamMapper.chechLeadTeamIsExist(leadTeamDTO.getSessionYear(), leadTeamDTO.getOrgId())).orElse(false);
        if (result) {
            throw new BusinessDataInvalidException(String.format("该领导班子在%d届中已经存在了", leadTeamDTO.getSessionYear()));
        }
        TabPbLeadTeam tabPbLeadTeam =
                generateTargetCopyPropertiesAndPaddingBaseField(leadTeamDTO, TabPbLeadTeam.class, false);
        int judgment = tabPbLeadTeamMapper.insertSelective(tabPbLeadTeam);
        return setCurrentAndAttachment(leadTeamDTO, tabPbLeadTeam, judgment);
    }

    /**
     * 设置以前的班子信息为往届并且传递附件信息
     *
     * @param leadTeamDTO   新增班子成员dto
     * @param tabPbLeadTeam 新增班子成员实体
     * @param judgment      判断条件
     * @return
     */
    private int setCurrentAndAttachment(LeadTeamDTO leadTeamDTO, TabPbLeadTeam tabPbLeadTeam, int judgment) {
        if (judgment > 0) {
            if (tabPbLeadTeam.getCurrent() == 1) {
                tabPbLeadTeamMapper.setPreviousLeadTeamForThePast(tabPbLeadTeam.getOrgId(), tabPbLeadTeam.getLeadTeamId());
            }
            judgment += iTabPbAttachmentService.intelligentOperation(leadTeamDTO.getAttachments(),
                    tabPbLeadTeam.getLeadTeamId(), AttachmentType.LEADERSHIP_TEAM);
        }
        return judgment;
    }

    @Transactional
    @Override
    public int updateLeadTeam(LeadTeamDTO leadTeamDTO) {
        LeadTeamVO leadTeamVO = tabPbLeadTeamMapper.selectLeadTeamVOById(leadTeamDTO.getLeadTeamId());
        if (leadTeamVO == null) {
            throw new BusinessDataNotFoundException("要修改的领导班子不存在");
        }
        TabPbLeadTeam tabPbLeadTeam =
                generateTargetCopyPropertiesAndPaddingBaseField(leadTeamDTO, TabPbLeadTeam.class, true);
        int judgment = tabPbLeadTeamMapper.updateByPrimaryKeySelective(tabPbLeadTeam);
        return setCurrentAndAttachment(leadTeamDTO, tabPbLeadTeam, judgment);
    }

    @Override
    public LeadTeamVO selectLeadTeamVOById(Long leadTeamId) {
        LeadTeamVO leadTeamVO = tabPbLeadTeamMapper.selectLeadTeamVOById(leadTeamId);
        if (leadTeamVO == null) {
            throw new BusinessDataNotFoundException("您要查找的领导班子信息不存在");
        }
        return tabPbLeadTeamMapper.selectLeadTeamVOById(leadTeamId);
    }
}
