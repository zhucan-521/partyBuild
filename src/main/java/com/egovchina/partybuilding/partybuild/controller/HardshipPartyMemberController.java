package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.HardshipPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.HardshipQueryBean;
import com.egovchina.partybuilding.partybuild.service.HardshipPartyMemberService;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
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
 * desc: 困难党员模块-v1
 * Created by FanYanGen on 2019/4/20 11:49
 */
@Api(tags = "党员-困难党员模块v1-范焱根")
@RequestMapping("/v1/difficulties")
@RestController
public class HardshipPartyMemberController {

    @Autowired
    private HardshipPartyMemberService hardshipPartyMemberService;

    @ApiOperation(value = "新增困难党员", notes = "新增困难党员", httpMethod = "POST")
    @HasPermission("party_difficult_add")
    @PostMapping
    public ReturnEntity insertHardshipPartyMember(@RequestBody @Validated @ApiParam(value = "困难党员信息") HardshipPartyMemberDTO hardshipPartyMemberDTO) {
        return ReturnUtil.buildReturn(hardshipPartyMemberService.insertHardshipPartyMember(hardshipPartyMemberDTO));
    }

    @ApiOperation(value = "更新困难党员", notes = "更新困难党员", httpMethod = "PUT")
    @HasPermission("party_difficult_edit")
    @PutMapping
    public ReturnEntity updateHardshipParty(@RequestBody @Validated @ApiParam(value = "困难党员信息") HardshipPartyMemberDTO hardshipPartyMemberDTO) {
        return ReturnUtil.buildReturn(hardshipPartyMemberService.updateHardshipPartyMember(hardshipPartyMemberDTO));
    }

    @ApiOperation(value = "删除困难党员", notes = "删除困难党员", httpMethod = "DELETE")
    @ApiImplicitParam(name = "hardshipId", value = "困难党员ID", paramType = "path", required = true)
    @HasPermission("party_difficult_delete")
    @DeleteMapping("/{hardshipId}")
    public ReturnEntity deleteByHardshipId(@PathVariable Long hardshipId) {
        return ReturnUtil.buildReturn(hardshipPartyMemberService.deleteByHardshipId(hardshipId));
    }

    @ApiOperation(value = "根据困难ID查询党员困难详情", notes = "根据困难ID查询党员困难详情", httpMethod = "GET")
    @ApiImplicitParam(name = "hardshipId", value = "困难记录ID", paramType = "path", required = true)
    @HasPermission("party_difficult_detail")
    @GetMapping("/{hardshipId}")
    public HardshipPartyVO getDifficultyPartyDetailsByHardshipId(@PathVariable Long hardshipId) {
        return hardshipPartyMemberService.findHardshipPartyVOByHardshipId(hardshipId);
    }

    @ApiOperation(value = "根据用户ID查询党员困难详情", notes = "根据用户ID查询党员困难详情", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "path", required = true)
    @HasPermission("party_difficult_detail")
    @GetMapping("/party-members/{userId}")
    public List<HardshipPartyVO> getDifficultyPartyDetailsByUserId(@PathVariable Long userId) {
        return hardshipPartyMemberService.findHardshipPartyVOByUserId(userId);
    }

    @ApiOperation(value = "困难党员列表", notes = "困难党员列表", httpMethod = "GET")
    @HasPermission("party_difficult")
    @GetMapping
    public PageInfo<HardshipPartyVO> getDifficultyPartyList(@Validated HardshipQueryBean hardshipQueryBean, Page page) {
        return hardshipPartyMemberService.findHardshipPartyVOWithConditions(hardshipQueryBean, page);
    }

}
