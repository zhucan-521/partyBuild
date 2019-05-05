package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.LeadTeamQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeam;
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
        TabPbLeadTeam delete = new TabPbLeadTeam();
        delete.setLeadTeamId(leadTeamId);
        delete.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(delete);
        int judgment = tabPbLeadTeamMapper.updateByPrimaryKeySelective(delete);
        if (judgment > 0) {
            judgment += tabPbLeadTeamMemberMapper.logicDeleteByLeadTeamId(leadTeamId);
        }
        return judgment;
    }

    @Transactional
    @Override
    public int insertLeadTeam(LeadTeamDTO leadTeamDTO) {
        TabPbLeadTeam tabPbLeadTeam =
                generateTargetCopyPropertiesAndPaddingBaseField(leadTeamDTO, TabPbLeadTeam.class, false);
        int judgment = tabPbLeadTeamMapper.insertSelective(tabPbLeadTeam);
        if (judgment > 0) {
            judgment += iTabPbAttachmentService.intelligentOperation(leadTeamDTO.getAttachments(),
                    tabPbLeadTeam.getLeadTeamId(), AttachmentType.LEADERSHIP_TEAM);
        }
        return judgment;
    }

    @Transactional
    @Override
    public int updateLeadTeam(LeadTeamDTO leadTeamDTO) {
        TabPbLeadTeam tabPbLeadTeam =
                generateTargetCopyPropertiesAndPaddingBaseField(leadTeamDTO, TabPbLeadTeam.class, true);
        int judgment = tabPbLeadTeamMapper.updateByPrimaryKeySelective(tabPbLeadTeam);
        if (judgment > 0) {
            judgment += iTabPbAttachmentService.intelligentOperation(leadTeamDTO.getAttachments(),
                    tabPbLeadTeam.getLeadTeamId(), AttachmentType.LEADERSHIP_TEAM);
        }
        return judgment;
    }

    @Override
    public LeadTeamVO selectLeadTeamVOById(Long leadTeamId) {
        return tabPbLeadTeamMapper.selectLeadTeamVOById(leadTeamId);
    }
}
