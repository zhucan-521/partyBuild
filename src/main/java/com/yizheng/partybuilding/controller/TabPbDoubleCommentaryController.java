package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.OrgRange;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbDoubleCommentary;

import com.yizheng.partybuilding.service.inf.TabPbDoubleCommentaryService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 双述双评控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "党组织-双述双评模块")
@RestController
@RequestMapping("/doubleCommentary")
public class TabPbDoubleCommentaryController {

    @Autowired
    private TabPbDoubleCommentaryService tabPbDoubleCommentaryService;

    @ApiOperation(value = "双述双评列表", notes = "双述双评列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planYear", value = "所属年度 yyyy", paramType = "query"),
            @ApiImplicitParam(name = "reportStartDate", value = "上报日期-开始 yyyy-MM-dd", paramType = "query"),
            @ApiImplicitParam(name = "reportEndDate", value = "上报日期-结束 yyyy-MM-dd", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbDoubleCommentary> list(String planYear, String reportStartDate, String reportEndDate,
                                                Page page, OrgRange orgRange) {
        Map<String, Object> conditions = orgRange.toMap();
        conditions.put("planYear", planYear);
        conditions.put("reportStartDate", reportStartDate);
        conditions.put("reportEndDate", reportEndDate);
        conditions.put("delFlag", "0");
        List<TabPbDoubleCommentary> list = tabPbDoubleCommentaryService.selectWithConditions(conditions, page);
        PageInfo<TabPbDoubleCommentary> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "新增双述双评", notes = "新增双述双评", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "双评双述实体") @RequestBody TabPbDoubleCommentary tabPbDoubleCommentary) {
        Long planYear = tabPbDoubleCommentary.getPlanYear();
        Long orgId = tabPbDoubleCommentary.getOrgId();
        if (tabPbDoubleCommentaryService.existsDoubleCommentary(planYear, orgId)) {
            throw new BusinessDataCheckFailException(String.format("该组织%s年度双述双评总结已存在", planYear));
        }
        int retVal = tabPbDoubleCommentaryService.insertWithAnnexs(tabPbDoubleCommentary);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "双述双评修改", notes = "双述双评修改", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam("双评双述实体") @RequestBody TabPbDoubleCommentary tabPbDoubleCommentary) {
        int retVal = tabPbDoubleCommentaryService.updateWithAnnexs(tabPbDoubleCommentary);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "双述双评详细", notes = "双述双评详细", httpMethod = "GET")
    @GetMapping("/details")
    public TabPbDoubleCommentary details(@RequestParam("commentaryId") @ApiParam(name = "commentaryId", value = "数据ID", required = true) Long commentaryId) {
        return tabPbDoubleCommentaryService.selectByPrimaryKey(commentaryId);
    }

    @ApiOperation(value = "双述双评删除", notes = "双述双评删除", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam("commentaryId") @ApiParam(name = "commentaryId", value = "数据ID", required = true) Long commentaryId) {
        //逻辑删除
        int retVal = tabPbDoubleCommentaryService.logicDeleteById(commentaryId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "双述双评审核", notes = "双述双评审核", httpMethod = "POST")
    @PostMapping("/review")
    public ReturnEntity review(@RequestParam @ApiParam(value = "数据ID", required = true) Long commentaryId,
                                      @RequestParam @ApiParam(value = "审核结果 dict SHJG", required = true) Long checkResult,
                                      @RequestParam(required = false) @ApiParam(value = "审核说明") String checkDesc) {
        TabPbDoubleCommentary dbDoubleCommentary = tabPbDoubleCommentaryService.selectByPrimaryKey(commentaryId);
        if (dbDoubleCommentary == null) {
            throw new BusinessDataNotFoundException("双述双评数据不存在");
        }
        TabPbDoubleCommentary update = new TabPbDoubleCommentary();
        update.setCommentaryId(dbDoubleCommentary.getCommentaryId());
        update.setCheckOrg(UserContextHolder.getOrgId());
        update.setCheckUser(UserContextHolder.getUserIdLong());
        update.setCheckResult(checkResult);
        update.setCheckDesc(checkDesc);
        update.setCheckDate(new Date());
        int retVal = tabPbDoubleCommentaryService.review(update);
        return ReturnUtil.buildReturn(retVal);
    }
}
