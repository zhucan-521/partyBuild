package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TabPbLinkLeaderDto;
import com.yizheng.partybuilding.entity.TabPbLinkLeader;

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