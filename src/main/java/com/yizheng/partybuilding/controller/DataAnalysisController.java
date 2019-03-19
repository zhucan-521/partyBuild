package com.yizheng.partybuilding.controller;

import com.yizheng.partybuilding.dto.BaseDataAnalysisDto;
import com.yizheng.partybuilding.dto.PartyMemberNumDataAnalysisDto;
import com.yizheng.partybuilding.service.inf.DataAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据分析控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "大数据分析模块")
@RestController
@RequestMapping("/dataAnalysis")
public class DataAnalysisController {

    @Autowired
    private DataAnalysisService dataAnalysisService;

    @ApiOperation(value = "统计单位占比-根据类型", httpMethod = "GET")
    @GetMapping("/countUnitRatioByType")
    public List<BaseDataAnalysisDto<Long>> countUnitRatioByType(@RequestParam @ApiParam(value = "组织ID") Long orgId) {
        return dataAnalysisService.countUnitRatioByType(orgId);
    }

    @ApiOperation(value = "统计领导班子人员数-根据年龄", httpMethod = "GET")
    @GetMapping("/countLeadTeamMemberNumByAge")
    public List<BaseDataAnalysisDto<Long>> countLeadTeamMemberNumByAge(@RequestParam @ApiParam(value = "组织ID") Long orgId) {
        return dataAnalysisService.countLeadTeamMemberNumByAge(orgId);
    }

    @ApiOperation(value = "统计组织数-根据类型", httpMethod = "GET")
    @GetMapping("/countOrgNumByType")
    public List<BaseDataAnalysisDto<Long>> countOrgNumByType(@RequestParam @ApiParam(value = "组织ID") Long orgId) {
        return dataAnalysisService.countOrgNumByType(orgId);
    }

    @ApiOperation(value = "统计党员数-根据类型", httpMethod = "GET")
    @GetMapping("/countPartyMemberNumByType")
    public List<BaseDataAnalysisDto<Long>> countPartyNumByType(@RequestParam @ApiParam(value = "组织ID") Long orgId) {
        return dataAnalysisService.countPartyMemberNumByType(orgId);
    }

    @ApiOperation(value = "统计特殊党员分类数量", httpMethod = "GET")
    @GetMapping("/countSpecialPartyMemberClassificationNum")
    public List<BaseDataAnalysisDto<Long>> countSpecialPartyMemberClassificationNum(@RequestParam @ApiParam(value = "组织ID") Long orgId) {
        return dataAnalysisService.countSpecialPartyMemberClassificationNum(orgId);
    }

    @ApiOperation(value = "统计当年党员数量及相较往年发展趋势-根据地区及月份", httpMethod = "GET")
    @GetMapping("/countPartyMemberNumByAreaAndMonth")
    public List<PartyMemberNumDataAnalysisDto> countPartyMemberNumByAreaCodeAndMonth(
            @RequestParam(defaultValue = "0430001") @ApiParam(value = "areaCode") String areaCode) {
        return dataAnalysisService.countPartyMemberNumByAreaCodeAndMonth(areaCode);
    }

}
