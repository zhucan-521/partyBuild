package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.util.AttachmentType;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbWorkPlan;

import com.yizheng.partybuilding.service.inf.TabPbWorkPlanService;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作计划控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "党组织-工作计划、工作总结模块-张帆")
@RestController
@RequestMapping("/workPlan")
public class TabPbWorkPlanController {

    @Autowired
    private TabPbWorkPlanService tabPbWorkPlanService;

    @ApiOperation(value = "工作计划列表", notes = "工作计划列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(name = "planYear", value = "年度 yyyy", paramType = "query"),
            @ApiImplicitParam(name = "reportStartDate", value = "上报日期-开始 yyyy-MM-dd", paramType = "query"),
            @ApiImplicitParam(name = "reportEndDate", value = "上报日期-结束 yyyy-MM-dd", paramType = "query"),
            @ApiImplicitParam(name = "checkResult", value = "计划审核结果 dict SHJG", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbWorkPlan> list(String orgRange, Long rangeDeptId, String planYear,
                                        String reportStartDate, String reportEndDate, Long checkResult,
                                        Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("orgRange", orgRange);
        if (rangeDeptId == null || rangeDeptId == 0L) {
            rangeDeptId = UserContextHolder.getOrgId();
        }
        conditions.put("rangeDeptId", rangeDeptId);
        conditions.put("planYear", planYear);
        conditions.put("reportStartDate", reportStartDate);
        conditions.put("reportEndDate", reportEndDate);
        conditions.put("checkResult", checkResult);
        conditions.put("attachmentType", AttachmentType.WORK_PLAN);
        conditions.put("delFlag", "0");
        List<TabPbWorkPlan> list = tabPbWorkPlanService.selectWithConditions(conditions, page);
        PageInfo<TabPbWorkPlan> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "新增工作计划", notes = "新增工作计划", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "计划实体") @RequestBody TabPbWorkPlan tabPbWorkPlan) {
        planDataVerification(tabPbWorkPlan);
        Long planYear = tabPbWorkPlan.getPlanYear();
        Long orgId = UserContextHolder.getOrgId();
        if (tabPbWorkPlanService.existsWorkPlan(planYear, orgId)) {
            throw new BusinessDataCheckFailException(String.format("该组织%s年度工作计划已存在", planYear));
        }
        tabPbWorkPlan.setOrgId(orgId);
        tabPbWorkPlan.setReportDate(new Date());
        int retVal = tabPbWorkPlanService.insertWithAnnexs(tabPbWorkPlan);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "工作计划修改", notes = "工作计划修改", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam("计划实体") @RequestBody TabPbWorkPlan tabPbWorkPlan) {
        planDataVerification(tabPbWorkPlan);
        int retVal = tabPbWorkPlanService.updateWithAnnexs(tabPbWorkPlan, AttachmentType.WORK_PLAN);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "工作计划审核", notes = "工作计划审核", httpMethod = "POST")
    @PostMapping("/review")
    public ReturnEntity review(@RequestParam @ApiParam(value = "计划ID", required = true) Long planId,
                               @RequestParam @ApiParam(value = "审核结果 dict SHJG", required = true) Long checkResult,
                               @RequestParam(required = false) @ApiParam(value = "审核说明") String planCheck) {
        TabPbWorkPlan dbTabPbWorkPlan = tabPbWorkPlanService.selectByPrimaryKey(planId);
        if (dbTabPbWorkPlan == null) {
            throw new BusinessDataNotFoundException("计划数据异常，已被删除或不存在");
        }
        TabPbWorkPlan tabPbWorkPlan = new TabPbWorkPlan();
        tabPbWorkPlan.setPlanId(dbTabPbWorkPlan.getPlanId());
        tabPbWorkPlan.setCheckOrg(UserContextHolder.getOrgId());
        tabPbWorkPlan.setCheckUser(UserContextHolder.getUserIdLong());
        tabPbWorkPlan.setCheckResult(checkResult);
        tabPbWorkPlan.setPlanCheck(planCheck);
        int retVal = tabPbWorkPlanService.review(tabPbWorkPlan);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "工作计划详细", notes = "工作计划详细", httpMethod = "GET")
    @GetMapping("/details")
    public TabPbWorkPlan details(@RequestParam("planId") @ApiParam(name = "planId", value = "计划ID", required = true) Long planId) {
        return tabPbWorkPlanService.selectByPrimaryKey(planId);
    }

    @ApiOperation(value = "工作计划删除", notes = "工作计划删除", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam("planId") @ApiParam(name = "planId", value = "计划ID", required = true) Long planId) {
        //逻辑删除
        int retVal = tabPbWorkPlanService.logicDeleteById(planId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "工作总结编辑", notes = "工作总结编辑", httpMethod = "POST")
    @PostMapping("/summary/update")
    public ReturnEntity summaryUpdate(@ApiParam("计划实体") @RequestBody TabPbWorkPlan tabPbWorkPlan) {
        summaryDataVerification(tabPbWorkPlan);
        int retVal = tabPbWorkPlanService.updateWithAnnexs(tabPbWorkPlan, AttachmentType.WORK_SUMMARY);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "工作总结审核", notes = "工作总结审核", httpMethod = "POST")
    @PostMapping("/summary/review")
    public ReturnEntity summaryReview(@RequestParam @ApiParam(value = "总结ID", required = true) Long planId,
                                      @RequestParam @ApiParam(value = "审核结果 dict SHJG", required = true) Long summaryCheckResult,
                                      @RequestParam(required = false) @ApiParam(value = "审核说明") String summaryCheck) {
        TabPbWorkPlan dbTabPbWorkPlan = tabPbWorkPlanService.selectByPrimaryKey(planId);
        if (dbTabPbWorkPlan == null) {
            throw new BusinessDataNotFoundException("总结数据不存在");
        }
        TabPbWorkPlan tabPbWorkPlan = new TabPbWorkPlan();
        tabPbWorkPlan.setPlanId(dbTabPbWorkPlan.getPlanId());
        tabPbWorkPlan.setSummaryCheckOrg(UserContextHolder.getOrgId());
        tabPbWorkPlan.setSummaryCheckUser(UserContextHolder.getUserIdLong());
        tabPbWorkPlan.setSummaryCheckResult(summaryCheckResult);
        tabPbWorkPlan.setSummaryCheck(summaryCheck);
        int retVal = tabPbWorkPlanService.review(tabPbWorkPlan);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "工作总结列表", notes = "工作总结列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query")
    })
    @GetMapping("/summary/list")
    public PageInfo<TabPbWorkPlan> summaryList(String orgRange, Long rangeDeptId, Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("orgRange", orgRange);
        if (rangeDeptId == null || rangeDeptId == 0) {
            rangeDeptId = UserContextHolder.getOrgId();
        }
        conditions.put("rangeDeptId", rangeDeptId);
        conditions.put("attachmentType", "4");
        conditions.put("delFlag", "0");
        List<TabPbWorkPlan> list = tabPbWorkPlanService.selectWithConditions(conditions, page);
        PageInfo<TabPbWorkPlan> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 数据校验
     *
     * @param tabPbWorkPlan
     */
    private void summaryDataVerification(TabPbWorkPlan tabPbWorkPlan) {
        if (StringUtils.isEmpty(tabPbWorkPlan.getPlanSummary())) {
            throw new BusinessDataIncompleteException("总结内容不能为空");
        }
    }

    private void planDataVerification(TabPbWorkPlan tabPbWorkPlan) {
        if (StringUtils.isEmpty(tabPbWorkPlan.getPlanContent())) {
            throw new BusinessDataIncompleteException("计划内容不能为空");
        }
    }
}
