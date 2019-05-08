package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupMemberIdsDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyGroupQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyGroupService;
import com.egovchina.partybuilding.partybuild.vo.PartyGroupVO;
import com.egovchina.partybuilding.partybuild.vo.PartyMemberBaseVO;
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
 * desc: 党小组模块-v1
 * Created by FanYanGen on 2019/4/28 09:41
 */
@Api(tags = "党组织-党小组管理-v1-范焱根")
@RequestMapping("/v1/party-groups")
@RestController
public class PartyGroupController {

    @Autowired
    private PartyGroupService partyGroupService;

    @ApiOperation(value = "新增党小组", notes = "新增党小组", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertPartyGroup(@RequestBody @Validated @ApiParam("党组信息") PartyGroupDTO partyGroupDTO) {
        return ReturnUtil.buildReturn(partyGroupService.insertPartyGroup(partyGroupDTO));
    }

    @ApiOperation(value = "新增成员到党小组", notes = "新增成员到党小组", httpMethod = "POST")
    @PostMapping("/members")
    public ReturnEntity insertMemberToPartyGroup(@RequestBody @Validated @ApiParam("党组成员信息") PartyGroupMemberDTO partyGroupMemberDTO) {
        return ReturnUtil.buildReturn(partyGroupService.insertMemberToPartyGroup(partyGroupMemberDTO));
    }

    @ApiOperation(value = "更新党小组", notes = "更新党小组", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updatePartyGroup(@RequestBody @Validated @ApiParam("党组信息") PartyGroupDTO partyGroupDTO) {
        return ReturnUtil.buildReturn(partyGroupService.updatePartyGroup(partyGroupDTO));
    }

    @ApiOperation(value = "删除党小组", notes = "删除党小组", httpMethod = "DELETE")
    @ApiImplicitParam(name = "groupId", value = "党组ID", paramType = "path", required = true)
    @DeleteMapping("/{groupId}")
    public ReturnEntity deletePartyGroup(@PathVariable Long groupId) {
        return ReturnUtil.buildReturn(partyGroupService.deletePartyGroup(groupId));
    }

    @ApiOperation(value = "移除党小组成员", notes = "移除党小组成员", httpMethod = "DELETE")
    @DeleteMapping("/members")
    public ReturnEntity removePartyGroupMembers(@RequestBody @Validated @ApiParam("党组成员ID集合") List<PartyGroupMemberIdsDTO> memberIds) {
        return ReturnUtil.buildReturn(partyGroupService.removePartyGroupMembers(memberIds));
    }

    @ApiOperation(value = "撤销党小组", notes = "撤销党小组", httpMethod = "DELETE")
    @ApiImplicitParam(name = "groupId", value = "党组ID", paramType = "path", required = true)
    @DeleteMapping("/annuls/{groupId}")
    public ReturnEntity revokePartyGroup(@PathVariable Long groupId) {
        return ReturnUtil.buildReturn(partyGroupService.revokePartyGroup(groupId));
    }

    @ApiOperation(value = "恢复党小组", notes = "恢复党小组", httpMethod = "POST")
    @ApiImplicitParam(name = "groupId", value = "党组ID", paramType = "path", required = true)
    @PostMapping("/resumes/{groupId}")
    public ReturnEntity recoveryRevokePartyGroup(@PathVariable Long groupId) {
        return ReturnUtil.buildReturn(partyGroupService.recoveryPartyGroup(groupId));
    }

    @ApiOperation(value = "筛选指定组织中未在任何党小组存在的党员（调用新增成员到党组前调用该接口）", notes = "筛选指定组织中未在任何党小组存在的党员", httpMethod = "GET")
    @ApiImplicitParam(name = "deptId", value = "组织ID", paramType = "path", required = true)
    @GetMapping("/{deptId}/candidate")
    public PageInfo<PartyMemberBaseVO> screenPartyGroupMembers(@PathVariable Long deptId, Page page) {
        return partyGroupService.screenPartyGroupMembers(deptId, page);
    }

    @ApiOperation(value = "获取党小组详情", notes = "获取党小组详情", httpMethod = "GET")
    @ApiImplicitParam(name = "groupId", value = "党组ID", paramType = "path", required = true)
    @GetMapping("/{groupId}")
    public PartyGroupVO getPartyGroupDetails(@PathVariable Long groupId) {
        return partyGroupService.getPartyGroupDetails(groupId);
    }

    @ApiOperation(value = "获取党小组列表", notes = "获取党小组列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<PartyGroupVO> getPartyGroupList(@Validated PartyGroupQueryBean partyGroupQueryBean, Page page) {
        return partyGroupService.getPartyGroupList(partyGroupQueryBean, page);
    }

}
