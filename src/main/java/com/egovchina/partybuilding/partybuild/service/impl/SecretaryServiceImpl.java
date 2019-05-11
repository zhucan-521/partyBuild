package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.repository.*;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.SecretaryInfoVO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SecretarysVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @author zhucan
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SecretaryServiceImpl implements SecretaryService {

    @Autowired
    private TabPbDeptSecretaryMapper tabPbDeptSecretaryMapper;

    @Autowired
    private TabPbFamilyMapper tabPbFamilyMapper;

    @Autowired
    private TabPbPositivesMapper tabPbPositivesMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbPunishmentMapper tabPbPunishmentMapper;

    @Autowired
    private TabPbRewardsMapper tabPbRewardsMapper;

    /**
     * 根据用户id获取书记基本信息
     *
     * @param userId
     * @return
     */
    @Override
    public SecretaryInfoVO getSecretaryInfoVOByUserId(Long userId) {
        SecretaryMemberVO secretaryMemberVO = tabPbDeptSecretaryMapper.selectSecretaryVOByUserId(userId);
        SecretaryInfoVO secretaryInfoVO = new SecretaryInfoVO();
        BeanUtil.copyPropertiesIgnoreNull(secretaryMemberVO, secretaryInfoVO);
        return secretaryInfoVO;
    }

    /**
     * 添加书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int insertSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        Long userId = secretaryMemberDTO.getUserId();
        if (userId == null) {
            SysUser sysUser = generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, SysUser.class, false);
            sysUser.setUsername(secretaryMemberDTO.getRealname());
            tabSysUserMapper.insertSelective(sysUser);
            userId = sysUser.getUserId();
            secretaryMemberDTO.setUserId(userId);
        }
        TabPbDeptSecretary tabPbDeptSecretaryinsert = generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, TabPbDeptSecretary.class, false);
        int flag = tabPbDeptSecretaryMapper.insertSelective(tabPbDeptSecretaryinsert);
        if (flag > 0) {
            List<FamilyMemberDTO> familyList = secretaryMemberDTO.getFamilys();
            List<PositivesDTO> positivesList = secretaryMemberDTO.getPositivesVOs();
            List<RewardsDTO> rewardsDTOs = secretaryMemberDTO.getRewardsDTOs();
            List<PunishmentDTO> punishmentDTO = secretaryMemberDTO.getPunishmentDTOs();
            final Long finalUserId = userId;
            //添加书记奖励和处分和职务和家庭成员
            this.addRewardAndPunishmentAndFamilyAndPosition(rewardsDTOs, punishmentDTO, finalUserId, familyList, positivesList);
        }
        return flag;
    }

    /**
     * 修改书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int updateSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        TabPbDeptSecretary tabPbDeptSecretaryinsert = new TabPbDeptSecretary();
        BeanUtil.copyPropertiesIgnoreNull(secretaryMemberDTO, tabPbDeptSecretaryinsert);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbDeptSecretaryinsert);
        int flag = tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretaryinsert);
        List<FamilyMemberDTO> familyMemberDTOS = secretaryMemberDTO.getFamilys();
        List<PositivesDTO> positivesDTOS = secretaryMemberDTO.getPositivesVOs();
        //修改或者添加家庭成员和职务
        this.updateOrAddFamilyAndPositives(positivesDTOS, familyMemberDTOS, secretaryMemberDTO);
        return flag;
    }

    /**
     * 根据书记id获取书记详情
     *
     * @param secretaryId
     * @return
     */
    @Override
    public SecretaryMemberVO selectSecretaryBySecretaryId(Long secretaryId) {
        return tabPbDeptSecretaryMapper.selectSecretaryVOBySecretaryId(secretaryId);
    }

    /**
     * 列表查询书记
     *
     * @param secretaryMemberQueryBean
     * @return
     */
    @Override
    public List<SecretarysVO> selectSecretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbDeptSecretaryMapper.selectSecretaryVOList(secretaryMemberQueryBean);
    }

    /**
     * 删除书记
     *
     * @param secretaryId
     * @return
     */
    @Override
    public int deleteSecretary(Long secretaryId) {
        TabPbDeptSecretary tabPbDeptSecretary = new TabPbDeptSecretary();
        tabPbDeptSecretary.setSecretaryId(secretaryId);
        tabPbDeptSecretary.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbDeptSecretary);
        return tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
    }

    /**
     * 添加或者修改书记家庭和职务
     *
     * @param positivesDTOS
     * @param familyMemberDTOS
     * @param secretaryMemberDTO
     */
    private void updateOrAddFamilyAndPositives(List<PositivesDTO> positivesDTOS, List<FamilyMemberDTO> familyMemberDTOS, SecretaryMemberDTO secretaryMemberDTO) {
        if (CollectionUtil.isNotEmpty(familyMemberDTOS)) {
            for (FamilyMemberDTO familyDTO : familyMemberDTOS) {
                if (familyDTO.getRelationId() != null) {
                    TabPbFamily tabPbFamily = new TabPbFamily();
                    BeanUtil.copyPropertiesIgnoreNull(familyDTO, tabPbFamily);
                    tabPbFamilyMapper.updateByPrimaryKeySelective(tabPbFamily);
                } else {
                    TabPbFamily tabPbFamily = new TabPbFamily();
                    BeanUtil.copyPropertiesIgnoreNull(familyDTO, tabPbFamily);
                    tabPbFamily.setUserId(secretaryMemberDTO.getUserId());
                    tabPbFamilyMapper.insertSelective(tabPbFamily);
                }
            }
        }
        if (CollectionUtil.isNotEmpty(secretaryMemberDTO.getPositivesVOs())) {
            for (PositivesDTO positivesDTO : positivesDTOS) {
                if (positivesDTO.getPositiveId() != null) {
                    TabPbPositives tabPbPositives = new TabPbPositives();
                    BeanUtil.copyPropertiesIgnoreNull(positivesDTO, tabPbPositives);
                    tabPbPositivesMapper.updateByPrimaryKeySelective(tabPbPositives);
                } else {
                    TabPbPositives tabPbPositives = new TabPbPositives();
                    BeanUtil.copyPropertiesIgnoreNull(positivesDTO, tabPbPositives);
                    tabPbPositives.setUserId(secretaryMemberDTO.getUserId());
                    tabPbPositivesMapper.insertSelective(tabPbPositives);
                }
            }
        }
    }

    /**
     * 添加书记奖励和惩罚和职务和家庭
     *
     * @param rewardsDTOs
     * @param punishmentDTOs
     * @param userId
     * @param familieDTOs
     * @param PositivesDTOs
     */
    private void addRewardAndPunishmentAndFamilyAndPosition(List<RewardsDTO> rewardsDTOs, List<PunishmentDTO> punishmentDTOs, Long userId, List<FamilyMemberDTO> familieDTOs, List<PositivesDTO> PositivesDTOs) {
        //添加书记奖励
        if (CollectionUtil.isNotEmpty(rewardsDTOs)) {
            List<TabPbRewards> tabPbRewards = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(rewardsDTOs, TabPbRewards.class, reward -> reward.setUserId(userId), false);
            tabPbRewardsMapper.batchInsertTabPbRewardList(tabPbRewards);
        }
        //添加书记惩罚
        if (CollectionUtil.isNotEmpty(punishmentDTOs)) {
            List<TabPbPunishment> tabPbPunishments = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(punishmentDTOs, TabPbPunishment.class, tabPbPunishment -> tabPbPunishment.setUserId(userId), false);
            tabPbPunishmentMapper.batchInsertTabPbPunishment(tabPbPunishments);
        }
        //添加书记家庭
        if (CollectionUtil.isNotEmpty(familieDTOs)) {
            List<TabPbFamily> familys = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(familieDTOs, TabPbFamily.class, family -> family.setUserId(userId), false);
            tabPbFamilyMapper.batchInsertFamilyList(familys);
        }
        //添加书记职务
        if (CollectionUtil.isNotEmpty(PositivesDTOs)) {
            List<TabPbPositives> positives = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(PositivesDTOs, TabPbPositives.class, positive -> positive.setUserId(userId), false);
            tabPbPositivesMapper.batchInsertPositivesList(positives);
        }
    }

}
