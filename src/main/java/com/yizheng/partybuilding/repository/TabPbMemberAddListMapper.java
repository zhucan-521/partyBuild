package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbMemberAddList;

import java.util.List;

public interface TabPbMemberAddListMapper {
    int deleteByPrimaryKey(Long memberAddId);

    int insert(TabPbMemberAddList record);

    int insertSelective(TabPbMemberAddList record);

    TabPbMemberAddList selectByPrimaryKey(Long memberAddId);

    int updateByPrimaryKeySelective(TabPbMemberAddList record);

    int updateByPrimaryKeyWithBLOBs(TabPbMemberAddList record);

    int updateByPrimaryKey(TabPbMemberAddList record);

    TabPbMemberAddList selectByUserId(Long userId);

    List<TabPbMemberAddList> selectListByUserId(Long userId);
}