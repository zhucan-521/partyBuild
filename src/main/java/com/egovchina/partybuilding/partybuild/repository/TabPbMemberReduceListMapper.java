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
     * 查询以前成为历史党员的,用于算党龄
     *
     * @param record
     * @return
     */
    List<MemberReducesVO> selectInvalidPartyHistoryList(HistoricalPartyMemberQueryBean record);

    /**
     * 查看单个历史党员
     *
     * @param userId
     * @return
     */
    HistoryPartyVO selectHistoryPartyVOByUserId(Long userId);
}