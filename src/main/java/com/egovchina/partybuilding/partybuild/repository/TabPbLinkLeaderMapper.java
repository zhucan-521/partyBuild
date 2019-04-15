package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabPbLinkLeaderDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbLinkLeader;

import java.util.List;

public interface TabPbLinkLeaderMapper {
    int deleteByPrimaryKey(Long linkLedaerId);

    int insert(TabPbLinkLeader record);

    int insertSelective(TabPbLinkLeader record);

    TabPbLinkLeader selectByPrimaryKey(Long linkLedaerId);

    int updateByPrimaryKeySelective(TabPbLinkLeader record);

    int updateByPrimaryKeyWithBLOBs(TabPbLinkLeader record);

    int updateByPrimaryKey(TabPbLinkLeader record);

    List<TabPbLinkLeader> selectByUserIdAndDeptId(TabPbLinkLeader record);

    List<TabPbLinkLeaderDto> selectTabPbLinkLeaderDtoByDeptId(Long detpId);
}