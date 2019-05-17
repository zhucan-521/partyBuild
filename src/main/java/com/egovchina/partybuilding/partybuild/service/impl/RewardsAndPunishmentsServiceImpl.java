package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.PunishmentDTO;
import com.egovchina.partybuilding.partybuild.dto.RewardsDTO;
import com.egovchina.partybuilding.partybuild.entity.RewardsAndPunishmentsQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPunishment;
import com.egovchina.partybuilding.partybuild.entity.TabPbRewards;
import com.egovchina.partybuilding.partybuild.repository.TabPbPunishmentMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbRewardsMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.RewardsAndPunishmentsService;
import com.egovchina.partybuilding.partybuild.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.vo.PunishmentVO;
import com.egovchina.partybuilding.partybuild.vo.RewardsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RewardsAndPunishmentsServiceImpl implements RewardsAndPunishmentsService {

    @Autowired
    TabPbRewardsMapper tabPbRewardsMapper;

    @Autowired
    TabPbPunishmentMapper tabPbPunishmentMapper;

    @Autowired
    ITabPbAttachmentService tabPbAttachmentService;

    @Autowired
    ExtendedInfoServiceImpl extendedInfoServiceImpl;

    //开除党籍
    private final String DISMISSALOFPARTYMEMBERSHIP = "28";
    //开除党籍的出党类型
    private final Long DISMISSALOFPARTYMEMBERSHIPTYPE = 30019L;
    //出党
    private final Long OUTOFTHEPARTY = 59590L;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertPunishment(PunishmentDTO punishmentDTO) {
        TabPbPunishment tabPbPunishment = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(punishmentDTO, TabPbPunishment.class, false);
        int insertRow = tabPbPunishmentMapper.insertSelective(tabPbPunishment);
        if (insertRow > 0) {
            insertRow += tabPbAttachmentService.intelligentOperation(punishmentDTO.getAttachments(), tabPbPunishment.getPunishmentId(), AttachmentType.PUNISHMENT);
        }
        //判断处分是不是开除党籍,若是新增历史党员
        if (DISMISSALOFPARTYMEMBERSHIP.equals(punishmentDTO.getPunishName())) {
            DeletePartyMemberDTO deletePartyMemberDTO = new DeletePartyMemberDTO();
            deletePartyMemberDTO.setUserId(punishmentDTO.getUserId());
            deletePartyMemberDTO.setOutType(OUTOFTHEPARTY);
            deletePartyMemberDTO.setReduceTime(new Date());
            deletePartyMemberDTO.setQuitType(DISMISSALOFPARTYMEMBERSHIPTYPE);
            insertRow += extendedInfoServiceImpl.invalidByUserId(deletePartyMemberDTO);
        }
        return insertRow;
    }

    @Override
    public int deletePunishmentById(Long id) {
        TabPbPunishment pbPunishment = tabPbPunishmentMapper.selectByPrimaryKey(id);
        if (pbPunishment == null) {
            throw new BusinessDataNotFoundException("查不到该处分!");
        }
        pbPunishment.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(pbPunishment);
        return tabPbPunishmentMapper.updateByPrimaryKeySelective(pbPunishment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePunishmentById(PunishmentDTO punishmentDTO) {
        TabPbPunishment tabPbPunishment = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(punishmentDTO, TabPbPunishment.class, true);
        PaddingBaseFieldUtil.paddingBaseFiled(punishmentDTO.getAttachments());
        tabPbAttachmentService.intelligentOperation(punishmentDTO.getAttachments(), tabPbPunishment.getPunishmentId(), AttachmentType.PUNISHMENT);
        return tabPbPunishmentMapper.updateByPrimaryKeySelective(tabPbPunishment);
    }

    @Override
    public PunishmentVO selectPunishment(Long id) {
        return tabPbPunishmentMapper.selectByPrimaryKeyAndFiles(id);
    }

    @Override
    public int insertRewards(RewardsDTO rewards) {
        TabPbRewards tabPbRewards = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(rewards, TabPbRewards.class, false);
        int insertRow = tabPbRewardsMapper.insertSelective(tabPbRewards);
        if (insertRow > 0) {
            tabPbAttachmentService.intelligentOperation(rewards.getAttachments(), tabPbRewards.getRewardsId(), AttachmentType.REWARDS);
        }
        return insertRow;
    }

    @Override
    public int deleteRewardsById(Long id) {
        TabPbRewards rewards = tabPbRewardsMapper.selectByPrimaryKey(id);
        if (rewards == null) {
            throw new BusinessDataNotFoundException("查不到该奖励");
        }
        rewards.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(rewards);
        return tabPbRewardsMapper.updateByPrimaryKeySelective(rewards);
    }

    @PaddingBaseField(recursive = true)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRewardsById(RewardsDTO rewards) {
        TabPbRewards tabPbRewards = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(rewards, TabPbRewards.class, false);
        tabPbAttachmentService.intelligentOperation(rewards.getAttachments(), tabPbRewards.getRewardsId(), AttachmentType.REWARDS);
        return tabPbRewardsMapper.updateByPrimaryKeySelective(tabPbRewards);
    }


    @Override
    public RewardsVO selectRewards(Long id) {
        return tabPbRewardsMapper.selectByPrimaryKeyAndFiles(id);
    }


    @Override
    public List<PunishmentVO> selectPunishmentVOListAndFilesById(RewardsAndPunishmentsQueryBean rewardsAndPunishmentsQueryBean) {
        checkCondition(rewardsAndPunishmentsQueryBean);
        return tabPbPunishmentMapper.selectListAndFileVO(rewardsAndPunishmentsQueryBean);
    }

    @Override
    public List<RewardsVO> getRewardsListAndFiles(RewardsAndPunishmentsQueryBean rewardsAndPunishmentsQueryBean) {
        checkCondition(rewardsAndPunishmentsQueryBean);
        return tabPbRewardsMapper.selectListAndFile(rewardsAndPunishmentsQueryBean);
    }

    public void checkCondition(RewardsAndPunishmentsQueryBean rewardsAndPunishmentsQueryBean) {
        if (rewardsAndPunishmentsQueryBean.getOrgId() == null && rewardsAndPunishmentsQueryBean.getUserId() == null && rewardsAndPunishmentsQueryBean.getRealName() == null) {
            throw new BusinessDataNotFoundException("用户id,机构Id,用户名不能同时为空");
        }
    }
}
