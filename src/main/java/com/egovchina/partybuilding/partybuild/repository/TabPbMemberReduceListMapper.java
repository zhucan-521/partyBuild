package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbMemberReduceList;
import com.egovchina.partybuilding.partybuild.entity.HistoricalPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.vo.HistoryPartyVO;
import com.egovchina.partybuilding.partybuild.vo.MemberReducesVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbMemberReduceListMapper {
    int deleteByPrimaryKey(Long memberReduceId);

    int insert(TabPbMemberReduceList record);

    int insertSelective(TabPbMemberReduceList record);

    TabPbMemberReduceList selectByPrimaryKey(Long memberReduceId);

    int updateByPrimaryKeySelective(TabPbMemberReduceList record);

    int updateByPrimaryKeyWithBLOBs(TabPbMemberReduceList record);

    int updateByPrimaryKey(TabPbMemberReduceList record);

    TabPbMemberReduceList selectByUserId(Long userId);

    List<TabPbMemberReduceList> selectListByUserId(Long userId);

    List<HistoryPartyVO> selectPartyHistoryList(HistoricalPartyMemberQueryBean record);

    List<MemberReducesVO> selectInvalidPartyHistoryList(HistoricalPartyMemberQueryBean record);

    HistoryPartyVO selectHistoryPartyVOByUserId(Long userId);
}