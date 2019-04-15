package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbWorkPlan;

import java.util.List;
import java.util.Map;

/**
 * 工作计划 service接口
 *
 * @Author Zhang Fan
 **/
public interface TabPbWorkPlanService {

    int deleteByPrimaryKey(Long planId);

    int insert(TabPbWorkPlan record);

    int insertSelective(TabPbWorkPlan record);

    TabPbWorkPlan selectByPrimaryKey(Long planId);

    int updateByPrimaryKeySelective(TabPbWorkPlan record);

    int updateByPrimaryKey(TabPbWorkPlan record);

    List<TabPbWorkPlan> selectWithConditions(Map<String, Object> conditions, Page page);

    int insertWithAnnexs(TabPbWorkPlan tabPbWorkPlan);

    boolean existsWorkPlan(Long planYear, Long orgId);

    int logicDeleteById(Long planId);

    int updateWithAnnexs(TabPbWorkPlan tabPbWorkPlan, Long attType);

    int review(TabPbWorkPlan dbTabPbWorkPlan);
}
