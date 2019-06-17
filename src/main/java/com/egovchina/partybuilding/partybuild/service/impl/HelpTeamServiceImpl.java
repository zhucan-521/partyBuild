package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.HelpTeamDTO;
import com.egovchina.partybuilding.partybuild.dto.HelpTeamMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.MsgUpInfoDTO;
import com.egovchina.partybuilding.partybuild.entity.HelpTeamQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbHelpTeam;
import com.egovchina.partybuilding.partybuild.entity.TabPbHelpTeamMember;
import com.egovchina.partybuilding.partybuild.repository.TabPbHelpTeamMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbHelpTeamMemberMapper;
import com.egovchina.partybuilding.partybuild.service.HelpTeamService;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.vo.HelpTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.HelpTeamVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class HelpTeamServiceImpl implements HelpTeamService {

    @Autowired
    TabPbHelpTeamMapper tabPbHelpTeamMapper;

    @Autowired
    TabPbHelpTeamMemberMapper tabpbHelpTeamMemberMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public int addHelpTeam(HelpTeamDTO helpTeamDTO) {
        TabPbHelpTeam tabPbHelpTeam = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(helpTeamDTO, TabPbHelpTeam.class, false);
        int flag = tabPbHelpTeamMapper.insertSelective(tabPbHelpTeam);
        Long teamId = tabPbHelpTeam.getTeamId();
        if (flag > 0) {
            List<HelpTeamMemberDTO> helpTeamMemberDTOS = helpTeamDTO.getHelpTeamMemberDTOS();
            helpTeamMemberDTOS.stream().forEach(item -> {
                item.setTeamId(teamId);
                PaddingBaseFieldUtil.paddingBaseFiled(item);
            });
            tabpbHelpTeamMemberMapper.batchInsert(helpTeamMemberDTOS);
            this.modifyAttachment(helpTeamDTO.getAttachments(), teamId);
        }
        return flag;
    }

    @Override
    public List<HelpTeamMemberVO> selectHelpTeamMemberVO(Long orgId) {
        return tabPbHelpTeamMapper.selectHelpTeamMemberVO(orgId);
    }

    @Override
    public int deleteHelpTeam(Long teamId) {
        TabPbHelpTeam tabPbHelpTeam = new TabPbHelpTeam().setDelFlag(CommonConstant.STATUS_DEL).setTeamId(teamId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbHelpTeam);
        return tabPbHelpTeamMapper.updateByPrimaryKeySelective(tabPbHelpTeam);
    }

    @Override
    public List<HelpTeamVO> selectActiveHelpTeam(HelpTeamQueryBean helpTeamQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbHelpTeamMapper.selectiveHelpTeamVOs(helpTeamQueryBean);
    }

    @Override
    public HelpTeamVO getHelpTeamVOByTeamId(Long teamId) {
        return tabPbHelpTeamMapper.getHelpTeamVOByTeamId(teamId);
    }

    @Override
    public int updateHelpTeamDTOByTeamId(HelpTeamDTO helpTeamDTO) {
        TabPbHelpTeam tabPbHelpTeam = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(helpTeamDTO, TabPbHelpTeam.class, true);
        int flag = tabPbHelpTeamMapper.updateByPrimaryKeySelective(tabPbHelpTeam);
        if (flag > 0) {
            //前端传过来的队伍成员
            List<HelpTeamMemberDTO> newHelpTeamMemberVOs = helpTeamDTO.getHelpTeamMemberDTOS();
            List<Long> newMemberIds = new ArrayList<>();
            newHelpTeamMemberVOs.forEach(item -> {
                item.setTeamId(helpTeamDTO.getTeamId());
                if (item.getMemberId() == null) {
                    TabPbHelpTeamMember tabPbHelpTeamMember = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(item, TabPbHelpTeamMember.class, false);
                    tabpbHelpTeamMemberMapper.insertSelective(tabPbHelpTeamMember);
                    newMemberIds.add(tabPbHelpTeamMember.getMemberId());
                } else {
                    newMemberIds.add(item.getMemberId());
                }
            });
            //原来的队伍成员
            List<HelpTeamMemberVO> oldHelpTeamMemberVOs = tabpbHelpTeamMemberMapper.selectHelpTeamMemberVOByTeamId(helpTeamDTO.getTeamId());
            List<Long> oldMemberIds = new ArrayList<>();
            oldHelpTeamMemberVOs.forEach(oldItem -> oldMemberIds.add(oldItem.getMemberId()));
            oldMemberIds.removeAll(newMemberIds);
            oldMemberIds.forEach(item -> {
                TabPbHelpTeamMember tabPbHelpTeamMember = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(item, TabPbHelpTeamMember.class, true);
                tabPbHelpTeamMember.setDelFlag(CommonConstant.STATUS_DEL).setMemberId(item);
                tabpbHelpTeamMemberMapper.updateByPrimaryKeySelective(tabPbHelpTeamMember);
            });
            this.modifyAttachment(helpTeamDTO.getAttachments(), helpTeamDTO.getTeamId());
        }
        return flag;
    }

    /**
     * 附件维护
     *
     * @param attachments
     * @param hostId
     */
    private void modifyAttachment(List<TabPbAttachment> attachments, Long hostId) {
        iTabPbAttachmentService.intelligentOperation(attachments, hostId, AttachmentType.HELP_TEAM);
    }

}