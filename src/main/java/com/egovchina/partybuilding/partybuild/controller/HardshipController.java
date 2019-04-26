package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyDTO;
import com.egovchina.partybuilding.partybuild.entity.HardshipQueryBean;
import com.egovchina.partybuilding.partybuild.service.HardshipService;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 困难党员模块-v1
 * Created by FanYanGen on 2019/4/20 11:49
 */
@Api(tags = "党员-困难党员模块v1-范焱根")
@RequestMapping("/v1/difficulties")
@RestController
public class HardshipController {

    @Autowired
    private HardshipService hardshipService;

    @ApiOperation(value = "新增困难党员", notes = "新增困难党员", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insert(@ApiParam(value = "困难党员信息") @RequestBody @Validated HardshipPartyDTO hardshipPartyDTO) {
        return ReturnUtil.buildReturn(hardshipService.insertHardshipParty(hardshipPartyDTO));
    }

    @ApiOperation(value = "更新困难党员", notes = "更新困难党员", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity update(@ApiParam(value = "困难党员信息") @RequestBody @Validated HardshipPartyDTO hardshipPartyDTO) {
        return ReturnUtil.buildReturn(hardshipService.updateHardshipParty(hardshipPartyDTO));
    }

    @ApiOperation(value = "删除困难党员", notes = "删除困难党员", httpMethod = "DELETE")
    @ApiImplicitParam(name = "hardshipId", value = "困难党员ID", paramType = "path", required = true)
    @DeleteMapping("/{hardshipId}")
    public ReturnEntity delete(@PathVariable Long hardshipId) {
        return ReturnUtil.buildReturn(hardshipService.deleteByHardshipId(hardshipId));
    }

    @ApiOperation(value = "根据困难ID查询党员困难详情", notes = "根据困难ID查询党员困难详情", httpMethod = "GET")
    @ApiImplicitParam(name = "hardshipId", value = "困难党员ID", paramType = "path", required = true)
    @GetMapping("/{hardshipId}")
    public HardshipPartyVO getDifficultyPartyDetailsByHardshipId(@PathVariable Long hardshipId) {
        return hardshipService.findHardshipPartyVOByHardshipId(hardshipId);
    }

    @ApiOperation(value = "根据用户ID查询党员困难详情", notes = "根据用户ID查询党员困难详情", httpMethod = "GET")
    @ApiParam(value = "userId", name = "用户ID", required = true)
    @GetMapping("/party-members/{userId}")
    public HardshipPartyVO getDifficultyPartyDetailsByUserId(@PathVariable Long userId) {
        return hardshipService.findHardshipPartyVOByUserId(userId);
    }

    @ApiOperation(value = "困难党员列表", notes = "困难党员列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<HardshipPartyVO> getDifficultyPartyList(@Validated HardshipQueryBean hardshipQueryBean, Page page) {
        return hardshipService.findHardshipPartyVOWithConditions(hardshipQueryBean, page);
    }

}
