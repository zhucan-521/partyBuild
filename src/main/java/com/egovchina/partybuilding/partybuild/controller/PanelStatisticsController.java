package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.util.Symbol;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.entity.PanelStatistics;
import com.egovchina.partybuilding.partybuild.entity.PanelStatisticsDetail;
import com.egovchina.partybuilding.partybuild.service.PanelStatisticsService;
import com.egovchina.partybuilding.partybuild.service.TabSysDeptService;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 面板统计控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "首页-面板统计模块 张帆")
@RestController
@RequestMapping("/statistics")
public class PanelStatisticsController {

    @Autowired
    private TabSysDeptService tabSysDeptService;
    @Autowired
    private PanelStatisticsService panelStatisticsService;

    @ApiOperation(value = "统计-活动相关工作", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle", value = "周期 1 年度；2 季度；3 月度；", required = true, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "周期对应值 周期=年（值：此字段不会生效）；周期=季度（值：1-4）；周期=月（值：1-12）；", required = true, paramType = "query")
    })
    @GetMapping("/activity")
    public List<PanelStatistics> statisticsWorkInfoForActivity(@RequestParam Integer cycle,
                                                               @RequestParam Integer value) {
        Long orgId = UserContextHolder.getOrgId();
        SysDept dbDept = tabSysDeptService.selectAloneByPrimaryKey(orgId);
        assert dbDept != null;

        Map<String, Object> conditions = new HashMap<>();
        conditions.put("orgId", orgId);
        conditions.put("cycle", covertCycle(cycle));
        conditions.put("value", value);
        if (dbDept.ifBranch()) { //支部
            return panelStatisticsService.calculateBranchWorkInfoForActivity(conditions);
        }
        return panelStatisticsService.calculateUnBranchWorkInfoForActivity(conditions);
    }

    @ApiOperation(value = "详细-活动相关工作", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "cycle", value = "周期 1 年度；2 季度；3 月度；", required = true, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "周期对应值 周期=年（值：yyyy）；周期=季度（值：1-4）；周期=月（值：1-12）；", required = true, paramType = "query"),
            @ApiImplicitParam(name = "orgRange", value = "列表范围 1 直属下级；2 所有下级；", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "完成情况 1 已完成；2 未完成；不传查全部；", paramType = "query")
    })
    @GetMapping("/details")
    public PageInfo<PanelStatisticsDetail> detailWorkInfoForActivity(@RequestParam Long taskId, @RequestParam Integer cycle, @RequestParam String value,
                                                                     @RequestParam(defaultValue = "2") String orgRange, String status,
                                                                     Page page) {
        Long orgId = UserContextHolder.getOrgId();
        SysDept dbDept = tabSysDeptService.selectAloneByPrimaryKey(orgId);
        if (dbDept.ifBranch()) { //支部
            return new PageInfo<>();
        }
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("taskId", taskId);
        if (StringUtils.contains(value, Symbol.HLINE)) {
            String[] values = value.split(Symbol.HLINE);
            conditions.put("year", values[0]);
            conditions.put("value", values[1]);
        } else {
            if (StringUtils.isEmpty(value)) {
                value = String.valueOf(LocalDate.now().getYear());
            }
            conditions.put("year", value);
            conditions.put("value", value);
        }
        conditions.put("cycle", covertCycle(cycle));
        conditions.put("orgRange", orgRange);
        conditions.put("orgId", orgId);
        conditions.put("status", status);
        List<PanelStatisticsDetail> list = panelStatisticsService.selectDetailWorkInfoForActivity(conditions, page);
        return new PageInfo<>(list);
    }


    private String covertCycle(Integer cycle) {
        if (cycle == 1) {
            return "59418";
        } else if (cycle == 2) {
            return "59419";
        } else if (cycle == 3) {
            return "59420";
        }
        throw new BusinessDataInvalidException("周期值无效");
    }

    @ApiOperation(value = "活动照片墙", httpMethod = "GET")
    @GetMapping("activityPhotoWall")
    public List<HashMap<String, Object>> activityPhotoWall(@RequestParam(required = false, defaultValue = "10") @ApiParam(value = "数量限制") Integer limit) {
        return panelStatisticsService.selectActivityPhotos(UserContextHolder.getOrgId(), limit);
    }
}
