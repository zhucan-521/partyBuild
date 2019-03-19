package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.partybuilding.dto.GrantCommitteeAndMembersDto;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbGrantCommitteMember;
import com.yizheng.partybuilding.entity.TabPbGrantCommittee;
import com.yizheng.partybuilding.service.inf.ITabPbGrantCommitteeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/04
 */
@Api(tags = "党组织-街道大公委")
@RestController
@RequestMapping("/grantCommittee")
public class TabPbGrantCommitteeController {

    @Autowired
    private ITabPbGrantCommitteeService service;

    @ApiOperation(value = "添加街道大公委数据", notes = "街道大公委对象, 如果orgId存在则更新, 否则新增街道大公委数据")
    @PostMapping()
    public ReturnEntity save(@RequestBody TabPbGrantCommittee committee) {
        int retVal = this.service.committeeSave(committee);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "添加街道大公委数据", notes = "1. 街道大公委对象, 如果orgId存在则更新, 否则新增街道大公委数据 2. 添加街道大公委成员数据")
    @PostMapping("list")
    public ReturnEntity save(@RequestBody GrantCommitteeAndMembersDto grantAndMemberList) {
        int retVal = this.service.committeeSave(grantAndMemberList, grantAndMemberList.getMembers());
        return ReturnUtil.buildReturn(retVal);
    }

    @DeleteMapping("{grantCommitteeId}")
    @ApiOperation(value = "删除街道大公委数据", notes = "删除街道大公委将同时删除该街道大公委的成员")
    public ReturnEntity delete(@PathVariable Long grantCommitteeId) {
        int retVal = this.service.committeeDelete(grantCommitteeId);
        return ReturnUtil.buildReturn(retVal);
    }

    @GetMapping("{grantCommitteeId}")
    @ApiOperation(value = "获取街道大公委数据", notes = "通过街道大公委主键grantCommitteeId查询")
    public TabPbGrantCommittee find(@PathVariable Long grantCommitteeId) {
        return this.service.committeeFindById(grantCommitteeId);
    }

    @GetMapping()
    @ApiOperation(value = "获取街道大公委list数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "组织id", name = "orgId",dataType = "Long"),
            @ApiImplicitParam(value = "范围", name = "orgRange",dataType = "Long"),
            @ApiImplicitParam(value = "成员名称", name = "name",dataType = "String"),
            @ApiImplicitParam(value = "职位", name = "positiveName",dataType = "String"),
    })
    public PageInfo<TabPbGrantCommittee> find(Long orgId,Long orgRange,String name, String positiveName,@ApiParam() Page page) {
        return this.service.committeeFind(orgId,orgRange, name, positiveName, page);
    }

    // ----------- 街道大公委成员相关 -------------

    @ApiOperation(value = "添加街道大公委成员数据", notes = "grantCommitteeId 和userId 必须存在")
    @PostMapping("member")
    public ReturnEntity save(@RequestBody TabPbGrantCommitteMember member) {
        int retVal = this.service.memberSave(member);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "添加街道大公委成员数据", notes = "grantCommitteeId 和userId 必须存在")
    @PostMapping("member/list")
    public ReturnEntity save(@RequestBody List<TabPbGrantCommitteMember> list) {
        int retVal = this.service.memberSaveList(list);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "通过grantCommitteeMemberId 获取大公委成员数据", notes = "grantCommitteeMemberId为街道大公委成员明细表的主键")
    @GetMapping("member/{grantCommitteeMemberId}")
    public TabPbGrantCommitteMember memberFindByid(@PathVariable Long grantCommitteeMemberId) {
        return this.service.memberFindById(grantCommitteeMemberId);
    }

    @ApiOperation(value = "通过grantCommitteeMemberId 删除成员", notes = "grantCommitteeMemberId为街道大公委成员明细表的主键")
    @DeleteMapping("member/{grantCommitteeMemberId}")
    public ReturnEntity memberDelete(@PathVariable Long grantCommitteeMemberId) {
        int retVal = this.service.memberDelete(grantCommitteeMemberId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "通过grantCommitteeId查询成员", notes = "grantCommitteeId为街道大公委成员明细表的主键")
    @GetMapping("member/list")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "大公委主键", name = "grantCommitteeId",dataType = "Long"),
            @ApiImplicitParam(value = "人员名称", name = "personName",dataType = "String"),
            @ApiImplicitParam(value = "职务名称", name = "positiveName",dataType = "String"),
    })
    public PageInfo<TabPbGrantCommitteMember> findByCommitteeId(Long grantCommitteeId,String personName, String positiveName, Page page) {
        return this.service.memberFind(grantCommitteeId,personName, positiveName, page);
    }

}
