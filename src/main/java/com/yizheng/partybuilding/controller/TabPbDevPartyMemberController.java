package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.partybuilding.dto.*;
import com.yizheng.partybuilding.entity.TabPbDevPartyMember;
import com.yizheng.partybuilding.entity.TabPbDevPartyMemberDate;
import com.yizheng.partybuilding.service.inf.ITabPbDevPartyMemberService;
import com.yizheng.partybuilding.system.entity.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 党员发展步骤接口
 *
 * @author chenshanlu
 * @version 1.0
 * @date 2018/11/28
 */

@RestController
@RequestMapping("/devPartyMember")
@Api(tags = "党员-发展步骤 朱灿")
public class TabPbDevPartyMemberController {

    @Autowired
    private ITabPbDevPartyMemberService memberService;

    @ApiOperation(value = "更新党员发展步骤信息")
    @PutMapping()
    public ReturnEntity update(@RequestBody TabPbDevPartyMemberDto member) {
        int retVal = memberService.update(member);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "通过发展步骤id获取党员发展步骤信息")
    @GetMapping(value = "{dpId}")
    public TabPbDevPartyMember getById(@PathVariable Long dpId) {
        return memberService.getByPrimaryKey(dpId);
    }

    @ApiOperation(value = "通过党员id获取发展步骤信息")
    @GetMapping(value = "getByUserId")
    public TabPbDevPartyMember getByUserId(Long userId) {
        return memberService.getByUserId(userId);
    }

    @ApiOperation(value = "删除党员发展步骤信息")
    @DeleteMapping(value = "{pdId}")
    @ApiImplicitParam(name = "pdId", paramType = "path", example = "1")
    public ReturnEntity delete(@PathVariable Long pdId) {
        int retVal = memberService.delete(pdId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "分页获取党员发展列表信息")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pdId", example = "1", value = "主键id"),
            @ApiImplicitParam(name = "userId", example = "1", value = "党员userId"),
            @ApiImplicitParam(name = "status", example = "1", value = "发展状态")
    })
    public PageInfo<TabPbDevPartyMember> list(Long pdId, Long userId, Long status, Page page) {
        return memberService.list(pdId, userId, status, page);
    }

    @ApiOperation(value = "判断该用户是否能补录, 如果能补录, 则返回userId, 否则返回null")
    @GetMapping("checkIsParty")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addType", example = "1", value = "增加方式: 1: 新发展 2:恢复党籍(归国) 3:恢复党籍(重新取得联系) 4:其他原因增加", required = true),
            @ApiImplicitParam(name = "userName", example = "1", value = "党员userName", required = true),
            @ApiImplicitParam(name = "idCardNo", example = "1", value = "身份证号", required = true),
            @ApiImplicitParam(name = "isLost", example = "1", value = "是否失联0,1")
    })
    public CheckPartyDto check(Long addType, String userName, String idCardNo, Short isLost) {
        return memberService.check(addType, userName, idCardNo, isLost);
    }

    @ApiOperation(value = "入党申请, 同时生成党员发展步骤")
    @PostMapping("partyApply")
    public TabPbDevPartyMember partyApply(@RequestBody SysUser user) {
        return memberService.partyApply(user);
    }

    @ApiOperation(value = "入党申请信息修改")
    @PutMapping("partyApply")
    public ReturnEntity updateUser(@RequestBody SysUser user) {
        int retVal = memberService.updateUserInfo(user);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "入党申请列表")
    @GetMapping("partyApply")
    public PageInfo<DevPartyUserDto> partyApply(PartyApplyConditionsDto conditions, Page page) {
        return memberService.getDevPartyList(conditions, page);
    }

    @ApiOperation(value = "添加发展步骤时间")
    @PostMapping("devDate")
    public ReturnEntity devDate(@RequestBody List<TabPbDevPartyMemberDate> dates) {
        int retVal = memberService.saveDevDate(dates);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "通过hostId获取发展步骤时间")
    @GetMapping("devDate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hostId", required = true)
    })
    public List<TabPbDevPartyMemberDate> devDate(Long hostId) {
        return memberService.getDevDate(hostId);
    }

    @ApiOperation(value = "通过时间主键删除")
    @DeleteMapping("devDate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "timeId", required = true)
    })
    public ReturnEntity delDevDate(Long timeId) {
        int retVal = memberService.deleteDevDate(timeId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "通过时间主键更新")
    @PutMapping("devDate")
    public ReturnEntity putDevDate(@RequestBody TabPbDevPartyMemberDate date) {
        int retVal = memberService.updateDevDate(date);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "上传党员发展指定步骤的附件, 每次上传将覆盖上次的附件")
    @PostMapping("attach")
    public void attach(@RequestBody PartyDevAttachDto attach) {
        this.memberService.attach(attach);
    }

    @ApiOperation(value = "查询党员发展指定步骤, (包含具体的附件信息和发展步骤信息)")
    @GetMapping("attach")
    public PartyDevAttachListDto getAttach(Long dpId, Boolean isExtend) {
        return this.memberService.getAttach(dpId, isExtend);
    }
}

