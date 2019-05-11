package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.RewardsAndPunishmentsQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbRewards;
import com.egovchina.partybuilding.partybuild.vo.RewardsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TabPbRewardsMapper {
    int deleteByPrimaryKey(Long rewardsId);

    int insert(TabPbRewards record);

    int insertSelective(TabPbRewards record);

    TabPbRewards selectByPrimaryKey(Long rewardsId);

    int updateByPrimaryKeySelective(TabPbRewards record);

    int updateByPrimaryKeyWithBLOBs(TabPbRewards record);

    int updateByPrimaryKey(TabPbRewards record);

    List<TabPbRewards> selectList(TabPbRewards rewards);

    RewardsVO selectByPrimaryKeyAndFiles(Long rewardsId);

    List<RewardsVO> selectListAndFile(RewardsAndPunishmentsQueryBean rewardsAndPunishmentsQueryBean);

    /**
     * 批量添加奖励
     *
     * @param rewardsList
     * @return
     */
    int batchInsertTabPbRewardList(List<TabPbRewards> rewardsList);
}