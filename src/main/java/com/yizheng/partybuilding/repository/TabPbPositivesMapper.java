package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbPositives;

import java.util.List;

public interface TabPbPositivesMapper {
    int deleteByPrimaryKey(Integer positiveId);

    int insert(TabPbPositives record);

    int insertSelective(TabPbPositives record);

    TabPbPositives selectByPrimaryKey(Integer positiveId);

    int updateByPrimaryKeySelective(TabPbPositives record);

    int updateByPrimaryKeyWithBLOBs(TabPbPositives record);

    int updateByPrimaryKey(TabPbPositives record);

    List<TabPbPositives> selectList(TabPbPositives tabPbPositives);

    List<TabPbPositives> verifyDuplicateDuties(TabPbPositives tabPbPositives);
}