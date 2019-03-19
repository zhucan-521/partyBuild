package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbPlanContent;

public interface TabPbPlanContentMapper {
    int deleteByPrimaryKey(Long planContentId);

    int insert(TabPbPlanContent record);

    int insertSelective(TabPbPlanContent record);

    TabPbPlanContent selectByPrimaryKey(Long planContentId);

    int updateByPrimaryKeySelective(TabPbPlanContent record);

    int updateByPrimaryKey(TabPbPlanContent record);
}