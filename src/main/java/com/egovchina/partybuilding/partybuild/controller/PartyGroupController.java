package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.enums.PermissionMatchType;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyGroupQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyGroupService;
import com.egovchina.partybuilding.partybuild.vo.PartyGroupVO;
import com.egovchina.partybuilding.partybuild.vo.PartyMemberBaseVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @HasPermission(value = "party_partyGroup_add", matchType = PermissionMatchType.ANY)
    @PostMapping
    public ReturnEntity insertPartyGroup(@RequestBody @Validated @ApiParam("党小组信息") PartyGroupDTO partyGroupDTO) {
        return ReturnUtil.buildReturn(partyGroupService.insertPartyGroup(partyGroupDTO));
    }

    @ApiOperation(value = "更新党小组", notes = "更新党小组", httpMethod = "PUT")
    @HasPermission(value = "party_partyGroup_edit", matchType = PermissionMatchType.ANY)
    @PutMapping
    public ReturnEntity updatePartyGroup(@RequestBody @Validated @ApiParam("党小组信息") PartyGroupDTO partyGroupDTO) {
        return ReturnUtil.buildReturn(partyGroupService.updatePartyGroup(partyGroupDTO));
    }

    @ApiOperation(value = "删除党小组", notes = "删除党小组", httpMethod = "DELETE")
    @ApiImplicitParam(name = "groupId", value = "党小组ID", paramType = "path", required = true)
    @HasPermission(value = "party_partyGroup_del", matchType = PermissionMatchType.ANY)
    @DeleteMapping("/{groupId}")
    public ReturnEntity deletePartyGroup(@PathVariable Long groupId) {
        return ReturnUtil.buildReturn(partyGroupService.deletePartyGroup(groupId));
    }

    @ApiOperation(value = "撤销党小组", notes = "撤销党小组", httpMethod = "DELETE")
    @ApiImplicitParam(name = "groupId", value = "党小组ID", paramType = "path", required = true)
    @HasPermission(value = "party_partyGroup_repeal", matchType = PermissionMatchType.ANY)
    @DeleteMapping("/annuls/{groupId}")
    public ReturnEntity revokePartyGroup(@PathVariable Long groupId) {
        return ReturnUtil.buildReturn(partyGroupService.revokePartyGroup(groupId));
    }

    @ApiOperation(value = "恢复党小组", notes = "恢复党小组", httpMethod = "POST")
    @ApiImplicitParam(name = "groupId", value = "党小组ID", paramType = "path", required = true)
    @HasPermission(value = "party_partyGroup_recover", matchType = PermissionMatchType.ANY)
    @PostMapping("/resumes/{groupId}")
    public ReturnEntity recoveryRevokePartyGroup(@PathVariable Long groupId) {
        return ReturnUtil.buildReturn(partyGroupService.recoveryPartyGroup(groupId));
    }

    @ApiOperation(value = "筛选指定组织中未在任何党小组存在的党员（更新时需要传入党小组ID）", notes = "筛选指定组织中未在任何党小组存在的党员", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "groupId", value = "党小组ID", paramType = "query")
    })
    @GetMapping("/candidate")
    public PageInfo<PartyMemberBaseVO> screenPartyGroupMembers(Long orgId, Long groupId) {
        return partyGroupService.screenPartyGroupMembers(orgId, groupId);
    }

    @ApiOperation(value = "获取党小组详情", notes = "获取党小组详情", httpMethod = "GET")
    @ApiImplicitParam(name = "groupId", value = "党小组ID", paramType = "path", required = true)
    @HasPermission(value = "party_partyGroup_examine", matchType = PermissionMatchType.ANY)
    @GetMapping("/{groupId}")
    public PartyGroupVO getPartyGroupDetails(@PathVariable Long groupId) {
        return partyGroupService.getPartyGroupDetails(groupId);
    }

    @ApiOperation(value = "获取党小组列表", notes = "获取党小组列表", httpMethod = "GET")
    @HasPermission(value = "party_partyGroup_examine", matchType = PermissionMatchType.ANY)
    @GetMapping
    public PageInfo<PartyGroupVO> getPartyGroupList(@Validated PartyGroupQueryBean partyGroupQueryBean, Page page) {
        return partyGroupService.getPartyGroupList(partyGroupQueryBean, page);
    }

}
