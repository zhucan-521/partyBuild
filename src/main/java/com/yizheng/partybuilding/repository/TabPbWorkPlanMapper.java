package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbWorkPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TabPbWorkPlanMapper {
    int deleteByPrimaryKey(Long planId);

    int insert(TabPbWorkPlan record);

    int insertSelective(TabPbWorkPlan record);

    TabPbWorkPlan selectByPrimaryKey(Long planId);

    int updateByPrimaryKeySelective(TabPbWorkPlan record);

    int updateByPrimaryKeyWithBLOBs(TabPbWorkPlan record);

    int updateByPrimaryKey(TabPbWorkPlan record);

    List<TabPbWorkPlan> selectWithConditions(Map<String, Object> conditions);

    int logicDeleteById(Long planId);

    TabPbWorkPlan selectByPlanYearAndOrgId(@Param("planYear") Long planYear, @Param("orgId") Long orgId);
}