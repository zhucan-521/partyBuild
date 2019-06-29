package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.HistoricalPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbMemberReduceList;
import com.egovchina.partybuilding.partybuild.vo.HistoryPartyVO;
import com.egovchina.partybuilding.partybuild.vo.MemberReducesVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbMemberReduceListMapper {
    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insertSelective(TabPbMemberReduceList record);

    /**
     * 通过主键查询历史党员
     *
     * @param memberReduceId
     * @return
     */
    TabPbMemberReduceList selectByPrimaryKey(Long memberReduceId);

    /**
     * 条件修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(TabPbMemberReduceList record);

    /**
     * 通过出国id查询减少党员id
     */
    Long selectMemberIdByAbroadId(Long selectMemberIdByAbroadId);

    /**
     * 用于维护修改时出党方式为空不修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelectiveCondition(TabPbMemberReduceList record);

    /**
     * 通过用户id查询最近一条历史党员
     *
     * @param userId
     * @return
     */
    TabPbMemberReduceList selectByUserId(Long userId);

    /**
     * 查询历史党员列表
     *
     * @param record
     * @return
     */
    List<HistoryPartyVO> selectPartyHistoryList(HistoricalPartyMemberQueryBean record);

    /**
     * 查看单个历史党员
     *
     * @param userId
     * @return
     */
    HistoryPartyVO selectHistoryPartyVOByUserId(Long userId);

    /**
     * 通过id查询字典值
     *
     * @param type
     * @return
     */
    String selectDictName(Long type);
}