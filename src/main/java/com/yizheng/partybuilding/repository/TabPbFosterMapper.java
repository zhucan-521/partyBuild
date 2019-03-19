package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbFoster;

public interface TabPbFosterMapper {
    int deleteByPrimaryKey(Long fosterId);

    int insert(TabPbFoster record);

    int insertSelective(TabPbFoster record);

    TabPbFoster selectByPrimaryKey(Long fosterId);

    int updateByPrimaryKeySelective(TabPbFoster record);

    int updateByPrimaryKeyWithBLOBs(TabPbFoster record);

    int updateByPrimaryKey(TabPbFoster record);
}