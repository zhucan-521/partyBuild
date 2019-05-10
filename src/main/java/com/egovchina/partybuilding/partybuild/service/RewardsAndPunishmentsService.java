package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.PunishmentDTO;
import com.egovchina.partybuilding.partybuild.dto.RewardsDTO;
import com.egovchina.partybuilding.partybuild.vo.PunishmentVO;
import com.egovchina.partybuilding.partybuild.vo.RewardsVO;

import java.util.List;

/**
 * @author: huang
 * Date: 2018/12/3
 */
public interface RewardsAndPunishmentsService {
    int insertPunishment(PunishmentDTO punishment);

    int deletePunishmentById(Long id);

    int updatePunishmentById(PunishmentDTO punishment);

    int insertRewards(RewardsDTO rewards);

    int deleteRewardsById(Long id);

    int updateRewardsById(RewardsDTO rewards);

    PunishmentVO selectPunishment(Long id);

    RewardsVO selectRewards(Long id);

    List<PunishmentVO> selectPunishmentVOListAndFilesById(Long orgId, Long userId, String userName);

    List<RewardsVO> getRewardsListAndFiles(Long orgId, Long userId, String userName);

}
