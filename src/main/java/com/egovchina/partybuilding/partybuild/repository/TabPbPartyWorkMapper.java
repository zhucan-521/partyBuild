package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyWork;
import com.egovchina.partybuilding.partybuild.vo.HistoryInformationGraphVO;
import com.egovchina.partybuilding.partybuild.vo.HistoryInformationVO;
import com.egovchina.partybuilding.partybuild.vo.PartyWorkVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tabPbPartyWorkMapper")
public interface TabPbPartyWorkMapper {
    int deleteByPrimaryKey(Long workId);

    int insert(TabPbPartyWork record);

    int insertSelective(TabPbPartyWork record);

    TabPbPartyWork selectByPrimaryKey(Long workId);

    int updateByPrimaryKeySelective(TabPbPartyWork record);

    int updateByPrimaryKey(TabPbPartyWork record);

    /**
     * desc: 根据党员id查询该党员的学历信息
     *
     * @param userId 党员ID
     * @return regions
     * @author FanYanGen
     * @date 2019/4/11 14:45
     **/
    List<PartyWorkVO> findAllByUserId(Long userId);

    /**
     * desc: 批量添加
     *
     * @param list TabPbPartyWork集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/11 14:31
     **/
    int batchInsert(List<TabPbPartyWork> list);

    /**
     * desc: 批量修改
     *
     * @param list TabPbPartyEducation集合
     * @return int
     * @author FanYanGen
     * @date 2019/4/11 14:40
     **/
    int batchUpdate(List<TabPbPartyWork> list);

    /**
     * 党员历史信息图
     *
     * @param orgnizeLife           组织生活
     * @param communityActivity     社区活动
     * @param partyMemberComment    党员评议
     * @param userId                用户id
     * @return
     */
    List<HistoryInformationGraphVO> selectHistoryInformationGraphByBasicAndOrgnizeLifeWithCommunityActivity(@Param("orgnizeLife") Boolean orgnizeLife, @Param("communityActivity") Boolean communityActivity, @Param("partyMemberComment") Boolean partyMemberComment, @Param("userId") Long userId);

    /**
     * @param partyMemberPeriod 党籍时间段
     * @param activityPeriod    活动时间段
     * @param communityPeriod   社区时间段
     * @param userId            用户id
     * @return
     */
    List<HistoryInformationVO> selectWithConditions(@Param("partyMemberPeriod") String partyMemberPeriod, @Param("activityPeriod") String activityPeriod, @Param("communityPeriod") String communityPeriod, @Param("userId") Long userId);

}