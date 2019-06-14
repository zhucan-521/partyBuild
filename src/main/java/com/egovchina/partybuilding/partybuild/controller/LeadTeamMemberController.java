package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.CommunityPartTimeMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.LeadTeamMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.PartyMemberSecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.service.LeadTeamMemberService;
import com.egovchina.partybuilding.partybuild.vo.CommunityPartTimeMemberVO;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberListVO;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.PartySecretarysVO;
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
 * 班子成员
 *
 * @Author guanyingxin
 **/
@Api(tags = "党组织-领导班子成员模块v1-官颖鑫")
@RestController
@RequestMapping("/v1")
public class LeadTeamMemberController {

    @Autowired
    private LeadTeamMemberService leadTeamMemberService;

    @ApiOperation(value = "班子成员列表", notes = "班子成员列表", httpMethod = "GET")
    @ApiImplicitParam(value = "班子id", name = "leadTeamId", dataType = "long", paramType = "path", required = true)
    @HasPermission({"party_leadershipTeam_members", "party_leadershipTeam"})
    @GetMapping("/lead-teams/{leadTeamId}/members")
    public PageInfo<LeadTeamMemberListVO> getLeadTeamMemberList(@PathVariable Long leadTeamId, Page page) {
        List<LeadTeamMemberListVO> list = leadTeamMemberService.selectLeadTeamMemberVOListByLeadTeamId(leadTeamId, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "添加班子成员", notes = "添加班子成员", httpMethod = "POST")
    @HasPermission("party_leadershipTeam_add")
    @PostMapping("/lead-team-members")
    public ReturnEntity insertLeadTeamMember(@ApiParam("班子成员信息") @RequestBody @Validated LeadTeamMemberDTO leadTeamMemberDTO) {
        return ReturnUtil.buildReturn(leadTeamMemberService.insertLeadTeamMember(leadTeamMemberDTO));
    }

    @ApiOperation(value = "修改班子成员", notes = "修改班子成员", httpMethod = "PUT")
    @HasPermission("party_leadershipTeam_edit")
    @PutMapping("/lead-team-members")
    public ReturnEntity updateLeadTeamMember(@ApiParam(value = "班子成员信息") @RequestBody @Validated LeadTeamMemberDTO leadTeamMemberDTO) {
        return ReturnUtil.buildReturn(leadTeamMemberService.updateLeadTeamMember(leadTeamMemberDTO));
    }

    @ApiOperation(value = "删除班子成员", notes = "删除班子成员", httpMethod = "DELETE")
    @ApiImplicitParam(value = "班子人员id", name = "memberId", dataType = "long", paramType = "path", required = true)
    @HasPermission("party_leadershipTeam_del")
    @DeleteMapping("/lead-team-members/{memberId}")
    public ReturnEntity deleteLeadTeamMember(@PathVariable Long memberId) {
        return ReturnUtil.buildReturn(leadTeamMemberService.logicDeleteLeadTeamMemberById(memberId));
    }

    @ApiOperation(value = "班子成员详情", notes = "查看班子成员详情", httpMethod = "GET")
    @ApiImplicitParam(value = "班子人员id", name = "memberId", dataType = "long", paramType = "path", required = true)
    @HasPermission("party_leadershipTeam_examine")
    @GetMapping("/lead-team-members/{memberId}")
    public LeadTeamMemberVO getLeadTeamMember(@PathVariable Long memberId) {
        return leadTeamMemberService.selectLeadTeamMemberVOById(memberId);
    }

    @ApiOperation(value = "班子成员列表", notes = "班子成员列表", httpMethod = "GET")
    @HasPermission({"party_leadershipTeam_members", "party_leadershipTeam"})
    @GetMapping("/lead-team-members")
    public PageInfo<LeadTeamMemberVO> getLeadTeamMemberList(LeadTeamMemberQueryBean queryBean, Page page) {
        List<LeadTeamMemberVO> list = leadTeamMemberService.selectLeadTeamMemberVOListByCondition(queryBean, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "社区兼职委员列表", notes = "社区兼职委员列表", httpMethod = "GET")
    @HasPermission("party_partTimePartyMember")
    @GetMapping("/community-part-time-members")
    public PageInfo<CommunityPartTimeMemberVO> getCommunityPartTimeMemberList(CommunityPartTimeMemberQueryBean queryBean, Page page) {
        List<CommunityPartTimeMemberVO> list = leadTeamMemberService.selectCommunityPartTimeMemberVOListByCondition(queryBean, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "书记列表", notes = "书记列表", httpMethod = "GET")
    @HasPermission("party_leadershipTeam")
    @GetMapping("/party-secretaries")
    public PageInfo<PartySecretarysVO> secretaryList(PartyMemberSecretaryMemberQueryBean partyMemberSecretaryMemberQueryBean, Page page) {
        return new PageInfo<>(leadTeamMemberService.selectSecretaryList(partyMemberSecretaryMemberQueryBean, page));
    }

}
