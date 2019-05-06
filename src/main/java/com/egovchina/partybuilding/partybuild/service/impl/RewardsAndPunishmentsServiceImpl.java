package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.PunishmentDTO;
import com.egovchina.partybuilding.partybuild.dto.RewardsDTO;
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

import java.util.List;

@Service
public class RewardsAndPunishmentsServiceImpl implements RewardsAndPunishmentsService {

    @Autowired
    TabPbRewardsMapper tabPbRewardsMapper;

    @Autowired
    TabPbPunishmentMapper tabPbPunishmentMapper;

    @Autowired
    ITabPbAttachmentService tabPbAttachmentService;


    @Override
    public int insertPunishment(PunishmentDTO punishmentDTO) {
        TabPbPunishment tabPbPunishment = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(punishmentDTO, TabPbPunishment.class, false);
        int insertRow = tabPbPunishmentMapper.insertSelective(tabPbPunishment);
        if (insertRow > 0) {
            insertRow += tabPbAttachmentService.intelligentOperation(punishmentDTO.getAttachments(), tabPbPunishment.getPunishmentId(), AttachmentType.PUNISHMENT);
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


    @PaddingBaseField(recursive = true)
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
    public List<PunishmentVO> selectPunishmentVOListAndFilesById(Long userId) {
        return tabPbPunishmentMapper.selectListAndFileVO(userId);
    }

    @Override
    public List<RewardsVO> getRewardsListAndFiles(Long userId) {
        return tabPbRewardsMapper.selectListAndFile(userId);
    }

}
