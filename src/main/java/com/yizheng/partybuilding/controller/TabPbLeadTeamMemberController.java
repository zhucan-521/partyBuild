package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbLeadTeamMemberDto;
import com.yizheng.partybuilding.entity.TabPbLeadTeamMember;
import com.yizheng.partybuilding.service.inf.TabPbLeadTeamMemberService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 班子成员
 *
 * @Author Jiang An
 **/
@Api(tags = "党组织-领导班子成员模块-蒋安")
@RestController
@RequestMapping("/member/")
public class TabPbLeadTeamMemberController {

    @Autowired
    private TabPbLeadTeamMemberService tabPbLeadTeamMemberService;

    @ApiOperation(value = "添加班子成员", notes = "添加班子成员-蒋安", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@RequestBody TabPbLeadTeamMember tabPbLeadTeamMember) {
        int retVal = tabPbLeadTeamMemberService.insertSelective(tabPbLeadTeamMember);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "删除班子成员", notes = "删除班子成员-蒋安", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam @ApiParam(value = "班子人员Id", required = true) Long memberId) {
        int retVal = tabPbLeadTeamMemberService.deleteId(memberId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查看班子成员详情", notes = "查看班子成员详情-蒋安", httpMethod = "GET")
    @GetMapping("/find")
    public TabPbLeadTeamMember findById(@RequestParam @ApiParam(value = "班子成员Id", required = true) Long memberId) {
        return tabPbLeadTeamMemberService.selectById(memberId);
    }

    @ApiOperation(value = "修改班子成员", notes = "修改班子成员-蒋安", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "班子成员实体") @RequestBody TabPbLeadTeamMember tabPbLeadTeamMember) {
        int retVal = tabPbLeadTeamMemberService.updateByPrimaryKeySelective(tabPbLeadTeamMember);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "班子成员列表", notes = "根据班子ID查询班子成员列表-蒋安", httpMethod = "GET")
    @GetMapping("/list")
    public PageInfo<TabPbLeadTeamMember> findByAll(@RequestParam @ApiParam(value = "领导班子Id", required = true) Long leadTeamId,
                                                   Page Page) {
        List<TabPbLeadTeamMember> list = tabPbLeadTeamMemberService.selectTeamMemberListByTeamId(leadTeamId, Page);
        PageInfo<TabPbLeadTeamMember> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "按条件分页全部成员", notes = "按条件全部班子成员-蒋安", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query"),
            @ApiImplicitParam(name = "personName", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "rank", value = "职务级别", paramType = "query"),
            @ApiImplicitParam(name = "positiveId", value = "职务", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织Id", paramType = "query")
    })
    @GetMapping("/findByList")
    public PageInfo<TabPbLeadTeamMemberDto> findByTabPbLeadTeamDto(@ApiIgnore TabPbLeadTeamMemberDto tabPbLeadTeamMemberDto, Page page) {
        Long rangeDeptId = tabPbLeadTeamMemberDto.getOrgId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            rangeDeptId = UserContextHolder.getOrgId();
        }
        tabPbLeadTeamMemberDto.setRangeDeptId(rangeDeptId);
        List<TabPbLeadTeamMemberDto> list = tabPbLeadTeamMemberService.selectByTabPbLeadTeamDto(tabPbLeadTeamMemberDto, page);
        PageInfo<TabPbLeadTeamMemberDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "查询上级组织领导班子成员", notes = "查询上级组织领导班子成员", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personName", value = "姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deptId", value = "组织id", paramType = "query", dataType = "long",required = true)
    })
    @GetMapping("/queryTheLeaderOfTheSuperiorOrganization")
    public PageInfo<TabPbLeadTeamMemberDto> queryTheLeaderOfTheSuperiorOrganization(Long deptId,String personName, Page page) {
        List<TabPbLeadTeamMemberDto> leadTeamMembers = tabPbLeadTeamMemberService.queryTheLeaderOfTheSuperiorOrganization(deptId,personName, page);
        PageInfo<TabPbLeadTeamMemberDto> pbLeadTeamMemberPageInfo = new PageInfo<>(leadTeamMembers);
        return pbLeadTeamMemberPageInfo;
    }
}
