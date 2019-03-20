package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.OrgRange;
import com.yizheng.commons.domain.Page;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.dto.TabPbLeadTeamMemberDto;
import com.yizheng.partybuilding.service.inf.TabPbLeadTeamMemberService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 社区兼职委员控制器
 *
 * @author wuyunjie
 * Date 2018-12-04 18:30
 */
@Api(tags = "党组织-社区兼职委员模块")
@RestController
@RequestMapping("/partTimeMember/")
public class PartTimeMemberController {

    @Autowired
    private TabPbLeadTeamMemberService tabPbLeadTeamMemberService;

    @ApiOperation(value = "查询社区兼职委员列表", notes = "查询社区兼职委员列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "组织范围  1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(name = "personName", value = "人员姓名", paramType = "query"),
            @ApiImplicitParam(name = "positiveId", value = "党内职务id", paramType = "query")
    })
    @GetMapping("/getLeadTeam")
    public PageInfo<TabPbLeadTeamMemberDto> getLeadTeam(String personName, Long positiveId,
                                                        Page page, OrgRange orgRange) {
        Map<String, Object> conditions = orgRange.toMap();
        conditions.put("personName", personName);
        conditions.put("positiveId", positiveId);
        List<TabPbLeadTeamMemberDto> leadTeamMemberDtos = tabPbLeadTeamMemberService.selectLeadTeamMemberByUser(conditions, page);
        return new PageInfo<>(leadTeamMemberDtos);
    }

    @ApiOperation(value = "删除社区兼职委员", notes = "删除社区兼职委员", httpMethod = "DELETE")
    @DeleteMapping("/delete/{memberId}")
    public int deleteMember(@ApiParam(value = "班子成员Id", required = true) @PathVariable Long memberId) {
        return tabPbLeadTeamMemberService.deleteMemberByMemberId(memberId);
    }
}
