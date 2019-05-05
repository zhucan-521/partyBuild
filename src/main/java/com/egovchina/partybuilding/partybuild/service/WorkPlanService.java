package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.WorkPlanDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkPlanReviewDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkSumamryReviewDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkSummaryDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbWorkPlan;
import com.egovchina.partybuilding.partybuild.entity.WorkPlanQueryBean;
import com.egovchina.partybuilding.partybuild.vo.WorkPlanVO;
import com.egovchina.partybuilding.partybuild.vo.WorkSummaryVO;

import java.util.List;
import java.util.Map;

/**
 * 工作计划 service接口
 *
 * @Author Zhang Fan
 **/
public interface WorkPlanService {

    List<WorkPlanVO> selectWorkPlanVOByCondition(WorkPlanQueryBean queryBean, Page page);

    /**
     * 新增工作计划
     *
     * @param workPlanDTO 工作计划实体
     * @return
     */
    int insertWorkPlan(WorkPlanDTO workPlanDTO);

    /**
     * 修改工作计划
     *
     * @param workPlanDTO 工作计划DTO
     * @return
     */
    int updateWorkPlan(WorkPlanDTO workPlanDTO);

    /**
     * 根据id查询工作计划VO
     *
     * @param planId id
     * @return
     */
    WorkPlanVO selectWorkPlanVOById(Long planId);

    /**
     * 审核工作计划
     *
     * @param reviewDTO 审核信息
     * @return
     */
    int reviewWorkPlan(WorkPlanReviewDTO reviewDTO);

    boolean existsWorkPlan(Long planYear, Long orgId);

    int logicDeleteById(Long planId);

    /**
     * 根据条件查询工作总结列表
     *
     * @param conditions 查询条件
     * @param page       分页对象
     * @return
     */
    List<WorkSummaryVO> selectWorkSummaryVOByCondition(Map<String, Object> conditions, Page page);

    /**
     * 修改工作总结
     *
     * @param workSummaryDTO 工作总结
     * @return
     */
    int updateWorkSummary(WorkSummaryDTO workSummaryDTO);

    /**
     * 根据id查询工作总结vo
     *
     * @param planId id
     * @return
     */
    WorkSummaryVO selectWorkSummaryVOById(Long planId);

    /**
     * 审核工作总结
     *
     * @param reviewDTO 总结实体
     * @return
     */
    int reviewWorkSummary(WorkSumamryReviewDTO reviewDTO);
}
