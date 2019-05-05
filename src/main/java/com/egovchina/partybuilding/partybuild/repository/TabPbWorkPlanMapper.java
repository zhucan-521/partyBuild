package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbWorkPlan;
import com.egovchina.partybuilding.partybuild.entity.WorkPlanQueryBean;
import com.egovchina.partybuilding.partybuild.vo.WorkPlanVO;
import com.egovchina.partybuilding.partybuild.vo.WorkSummaryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabPbWorkPlanMapper {

    int insertSelective(TabPbWorkPlan record);

    TabPbWorkPlan selectByPrimaryKey(Long planId);

    int updateByPrimaryKeySelective(TabPbWorkPlan tabPbWorkPlan);

    /**
     * 根据条件查询工作计划vo列表
     *
     * @param queryBean 条件
     * @return
     */
    List<WorkPlanVO> selectWorkPlanVOByCondition(WorkPlanQueryBean queryBean);

    /**
     * 根据条件查询工作总结vo列表
     *
     * @param conditions 条件
     * @return
     */
    List<WorkSummaryVO> selectWorkSummaryVOByCondition(Map<String, Object> conditions);

    int logicDeleteById(Long planId);

    TabPbWorkPlan selectByPlanYearAndOrgId(@Param("planYear") Long planYear, @Param("orgId") Long orgId);

    /**
     * 根据id查询工作计划VO
     *
     * @param planId id
     * @return
     */
    WorkPlanVO selectWorkPlanVOById(Long planId);

    /**
     * 根据id查询工作总结vo
     *
     * @param planId id
     * @return
     */
    WorkSummaryVO selectWorkSummaryVOById(Long planId);
}