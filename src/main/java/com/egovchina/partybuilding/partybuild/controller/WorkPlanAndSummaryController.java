package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.WorkPlanDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkPlanReviewDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkSumamryReviewDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkSummaryDTO;
import com.egovchina.partybuilding.partybuild.entity.WorkPlanQueryBean;
import com.egovchina.partybuilding.partybuild.service.WorkPlanService;
import com.egovchina.partybuilding.partybuild.vo.WorkPlanVO;
import com.egovchina.partybuilding.partybuild.vo.WorkSummaryVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 工作计划控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "党组织-工作计划、总结模块v1-官颖鑫")
@RestController
@RequestMapping("/v1")
public class WorkPlanAndSummaryController {

    @Autowired
    private WorkPlanService workPlanService;

    @ApiOperation(value = "工作计划列表", notes = "工作计划列表", httpMethod = "GET")
    @GetMapping("/work-plans")
    public PageInfo<WorkPlanVO> getWorkPlanList(@ApiParam("工作计划列表查询参数") WorkPlanQueryBean queryBean, Page page) {
        List<WorkPlanVO> list = workPlanService.selectWorkPlanVOByCondition(queryBean, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "新增工作计划", notes = "新增工作计划", httpMethod = "POST")
    @PostMapping("/work-plans")
    public ReturnEntity insertWorkPlan(@ApiParam(value = "计划信息") @RequestBody @Validated WorkPlanDTO workPlanDTO) {
        return ReturnUtil.buildReturn(workPlanService.insertWorkPlan(workPlanDTO));
    }

    @ApiOperation(value = "工作计划修改", notes = "工作计划修改", httpMethod = "PUT")
    @PutMapping("/work-plans")
    public ReturnEntity updateWorkPlan(@ApiParam("计划信息") @RequestBody @Validated WorkPlanDTO workPlanDTO) {
        return ReturnUtil.buildReturn(workPlanService.updateWorkPlan(workPlanDTO));
    }

    @ApiOperation(value = "工作计划详细", notes = "工作计划详细", httpMethod = "GET")
    @ApiImplicitParam(value = "计划id", name = "planId", dataType = "long", paramType = "path", required = true)
    @GetMapping("/work-plans/{planId}")
    public WorkPlanVO getWorkPlan(@PathVariable Long planId) {
        return workPlanService.selectWorkPlanVOById(planId);
    }

    @ApiOperation(value = "工作计划删除", notes = "工作计划删除", httpMethod = "DELETE")
    @ApiImplicitParam(value = "计划id", name = "planId", dataType = "long", paramType = "path", required = true)
    @DeleteMapping("/work-plans/{planId}")
    public ReturnEntity deleteWorkPlan(@PathVariable Long planId) {
        return ReturnUtil.buildReturn(workPlanService.logicDeleteById(planId));
    }

    @ApiOperation(value = "工作计划审核", notes = "工作计划审核", httpMethod = "POST")
    @PostMapping("/work-plans/reviews")
    public ReturnEntity reviewWorkPlan(@ApiParam("审核信息") @RequestBody @Validated WorkPlanReviewDTO reviewDTO) {
        return ReturnUtil.buildReturn(workPlanService.reviewWorkPlan(reviewDTO));
    }

    @ApiOperation(value = "工作总结列表", notes = "工作总结列表", httpMethod = "GET")
    @GetMapping("/work-summaries")
    public PageInfo<WorkSummaryVO> getWorkSummaryList(OrgRange orgRange, Page page) {
        Map<String, Object> conditions = orgRange.toMap();
        List<WorkSummaryVO> list = workPlanService.selectWorkSummaryVOByCondition(conditions, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "工作总结编辑", notes = "工作总结编辑", httpMethod = "PUT")
    @PutMapping("/work-summaries")
    public ReturnEntity updateWorkSummary(@ApiParam("总结信息") @RequestBody @Validated WorkSummaryDTO workSummaryDTO) {
        return ReturnUtil.buildReturn(workPlanService.updateWorkSummary(workSummaryDTO));
    }

    @ApiOperation(value = "工作总结详细", notes = "工作总结详细", httpMethod = "GET")
    @ApiImplicitParam(value = "计划id", name = "planId", dataType = "long", paramType = "path", required = true)
    @GetMapping("/work-summaries/{planId}")
    public WorkSummaryVO getWorkSummary(@PathVariable Long planId) {
        return workPlanService.selectWorkSummaryVOById(planId);
    }

    @ApiOperation(value = "工作总结审核", notes = "工作总结审核", httpMethod = "POST")
    @PostMapping("/work-summaries/reviews")
    public ReturnEntity reviewWorkSummary(@ApiParam("审核信息") @RequestBody @Validated WorkSumamryReviewDTO reviewDTO) {
        return ReturnUtil.buildReturn(workPlanService.reviewWorkSummary(reviewDTO));
    }
}
