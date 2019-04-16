package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbPunishment;
import com.egovchina.partybuilding.partybuild.entity.TabPbRewards;
import com.egovchina.partybuilding.partybuild.service.TabPbJiangChengService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * Date: 2018/12/3
 */
@Api(tags = "党员-奖惩模块")
@RestController
@RequestMapping("/jiangcheng")
public class TabPbJiangChengController {

    @Autowired
    TabPbJiangChengService tabPbJiangChengService;

    /**
     * 添加处分
     *
     * @param punishment 处分信息
     * @return success、false
     */
    @ApiOperation(value = "添加处分", notes = "处分新增", httpMethod = "POST")
    @PostMapping("/addPunishment")
    public ReturnEntity addPunishment(@ApiParam(value = "处分信息")@Valid @RequestBody TabPbPunishment punishment) {
        int retVal = tabPbJiangChengService.insertPunishment(punishment);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 删除处分
     *
     * @param id   ID
     * @return R
     */
    @DeleteMapping("/deletePunishment/{id}")
    @ApiOperation(value = "删除处分", notes = "删除处分")
    public ReturnEntity deletePunishment(@ApiParam(value = "处分id",required=true)@PathVariable Long id) {
        int retVal = tabPbJiangChengService.deletePunishmentById(id);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 修改处分
     *
     * @param punishment 处分信息
     * @return success/false
     */
    @ApiOperation(value = "修改处分", notes = "修改处分", httpMethod = "PUT")
    @PutMapping("editPunishment")
    public ReturnEntity editPunishment(@ApiParam(value = "处分信息")@Valid @RequestBody TabPbPunishment punishment) {
        int retVal = tabPbJiangChengService.updatePunishmentById(punishment);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 通过ID查询处分信息
     *
     * @param id ID
     * @return 处分信息
     */
    @ApiOperation(value = "通过ID查询处分信息", notes = "通过ID查询处分信息", httpMethod = "GET")
    @GetMapping("/punishment/{id}")
    public TabPbPunishment punishment(@ApiParam(value = "处分ID",required=true)@PathVariable Long id) {
        return tabPbJiangChengService.selectPunishmentById(id);
    }

    @ApiOperation(value = "查询处分信息列表", notes = "通过ID查询处分信息", httpMethod = "GET")
    @GetMapping("/selectPunishmentList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "punishOrgId", value = "处分机构Id",paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query",required=true)
    })
    public List<TabPbPunishment> selectPunishmentList(@ApiIgnore @RequestParam Map<String, Object> params){
        return tabPbJiangChengService.selectPunishmentList(params);
    }

    /**
     * 添加奖励
     *
     * @param rewards 奖励信息
     * @return success、false
     */
    @ApiOperation(value = "添加奖励", notes = "奖励新增", httpMethod = "POST")
    @PostMapping("/addRewards")
    public ReturnEntity addRewards(@ApiParam(value = "奖励信息")@Valid @RequestBody TabPbRewards rewards) {
        int retVal = tabPbJiangChengService.insertRewards(rewards);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 删除奖励
     *
     * @param id   ID
     * @return R
     */
    @DeleteMapping("/deleteRewards/{id}")
    @ApiOperation(value = "删除奖励", notes = "删除奖励")
    public ReturnEntity deleteRewards(@ApiParam(value = "奖励id",required=true)@PathVariable Long id) {
        int retVal = tabPbJiangChengService.deleteRewardsById(id);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 修改奖励
     *
     * @param rewards 奖励信息
     * @return success/false
     */
    @ApiOperation(value = "修改奖励", notes = "修改奖励", httpMethod = "PUT")
    @PutMapping("/editRewards")
    public ReturnEntity editRewards(@ApiParam(value = "奖励信息")@Valid @RequestBody TabPbRewards rewards) {
        int retVal = tabPbJiangChengService.updateRewardsById(rewards);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 通过ID查询奖励信息
     *
     * @param id ID
     * @return 奖励信息
     */
    @ApiOperation(value = "通过ID查询奖励信息", notes = "通过ID查询奖励信息", httpMethod = "GET")
    @GetMapping("/rewards/{id}")
    public TabPbRewards rewards(@ApiParam(value = "奖励ID",required=true)@PathVariable Long id) {
        return tabPbJiangChengService.selectRewardsById(id);
    }

    @ApiOperation(value = "查询奖励信息列表", notes = "查询奖励信息", httpMethod = "GET")
    @GetMapping("/selectRewardsList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rewardsOrgnizeId", value = "奖励机构Id", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query",required=true)
    })
    public List<TabPbRewards> selectRewardsList(@ApiIgnore @RequestParam Map<String, Object> params){
        return tabPbJiangChengService.selectRewardsList(params);
    }
}
