package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.LeadTeamQueryBean;
import com.egovchina.partybuilding.partybuild.service.LeadTeamService;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author guanyingxin
 */
@Api(tags = "党组织-领导班子v1-官颖鑫")
@RestController
@RequestMapping("/v1/lead-teams")
public class LeadTeamController {

    @Autowired
    private LeadTeamService leadTeamService;

    @ApiOperation(value = "领导班子列表", notes = "领导班子列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<LeadTeamVO> getLeadTeamList(@Validated LeadTeamQueryBean queryBean, Page page) {
        List<LeadTeamVO> list = leadTeamService.selectLeadTeamVOByCondition(queryBean, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "新增领导班子", notes = "新增领导班子", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertLeadTeam(@ApiParam(value = "班子信息") @RequestBody @Validated LeadTeamDTO leadTeamDTO) {
        return ReturnUtil.buildReturn(leadTeamService.insertLeadTeam(leadTeamDTO));
    }

    @ApiOperation(value = "修改领导班子", notes = "修改领导班子", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateLeadTeam(@ApiParam(value = "班子信息") @RequestBody @Validated LeadTeamDTO leadTeamDTO) {
        return ReturnUtil.buildReturn(leadTeamService.updateLeadTeam(leadTeamDTO));
    }

    @ApiOperation(value = "领导班子详情", notes = "查看单个领导班子详情", httpMethod = "GET")
    @ApiImplicitParam(value = "班子id", name = "orgId", dataType = "long", paramType = "path", required = true)
    @GetMapping("/{leadTeamId}")
    public LeadTeamVO getLeadTeam(@PathVariable Long leadTeamId) {
        return leadTeamService.selectLeadTeamVOById(leadTeamId);
    }

    @ApiOperation(value = "删除单个领导班子", notes = "删除单个领导班子-蒋安", httpMethod = "DELETE")
    @ApiImplicitParam(value = "领导班子id", name = "orgId", dataType = "long", paramType = "path", required = true)
    @DeleteMapping("/{leadTeamId}")
    public ReturnEntity deleteLeadTeam(@PathVariable Long leadTeamId) {
        return ReturnUtil.buildReturn(leadTeamService.logicDeleteLeadTeamById(leadTeamId));
    }
}
