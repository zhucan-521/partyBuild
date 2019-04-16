package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.entity.TabPbPunishment;
import com.egovchina.partybuilding.partybuild.entity.TabPbRewards;

import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * Date: 2018/12/3
 */
public interface TabPbJiangChengService {
    int insertPunishment(TabPbPunishment punishment);

    int deletePunishmentById(Long id);

    int updatePunishmentById(TabPbPunishment punishment);

    TabPbPunishment selectPunishmentById(Long id);

    List<TabPbPunishment> selectPunishmentList(Map<String, Object> params);

    int insertRewards(TabPbRewards rewards);

    int deleteRewardsById(Long id);

    int updateRewardsById(TabPbRewards rewards);

    TabPbRewards selectRewardsById(Long id);

    List<TabPbRewards> selectRewardsList(Map<String, Object> params);
}
