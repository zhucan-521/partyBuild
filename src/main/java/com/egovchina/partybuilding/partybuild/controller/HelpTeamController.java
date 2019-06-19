package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.HelpTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.HelpTeamQueryBean;
import com.egovchina.partybuilding.partybuild.service.HelpTeamService;
import com.egovchina.partybuilding.partybuild.vo.HelpTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.HelpTeamVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "党组织-驻村帮扶-v1-朱灿")
@RequestMapping("/v1/help-teams")
@RestController
public class HelpTeamController {

    @Autowired
    private HelpTeamService helpTeamService;

    @ApiOperation(value = "新增驻村帮扶队伍", httpMethod = "POST")
    @HasPermission(value = "resident_assistance_work_add")
    @PostMapping
    public ReturnEntity addHelpTeam(@Validated @RequestBody @ApiParam("驻村帮扶队伍") HelpTeamDTO helpTeamDTO) {
        return ReturnUtil.buildReturn(helpTeamService.addHelpTeam(helpTeamDTO));
    }

    @ApiOperation(value = "更新驻村帮扶队伍", notes = "务必带上teamId", httpMethod = "PUT")
    @HasPermission(value = "resident_assistance_work_edit")
    @PutMapping
    public ReturnEntity updateHelpTeamDTOByTeamId(@Validated @RequestBody @ApiParam("驻村帮扶队伍") HelpTeamDTO helpTeamDTO) {
        return ReturnUtil.buildReturn(helpTeamService.updateHelpTeamDTOByTeamId(helpTeamDTO));
    }

    @ApiOperation(value = "删除帮扶队伍", notes = "删除帮扶队伍", httpMethod = "DELETE")
    @ApiImplicitParam(name = "teamId", value = "驻村帮扶队伍主键", paramType = "path", required = true)
    @HasPermission(value = "resident_assistance_work_del")
    @DeleteMapping("/{teamId}")
    public ReturnEntity deleteHelpTeam(@PathVariable Long teamId) {
        return ReturnUtil.buildReturn(helpTeamService.deleteHelpTeam(teamId));
    }

    @ApiOperation(value = "驻村帮扶队伍列表", httpMethod = "GET")
    @HasPermission("resident_assistance_work")
    @GetMapping
    public PageInfo<HelpTeamVO> selectActiveHelpTeam(HelpTeamQueryBean helpTeamQueryBean, Page page) {
        PageInfo<HelpTeamVO> pageInfo = new PageInfo<>(helpTeamService.selectActiveHelpTeam(helpTeamQueryBean, page));
        return pageInfo;
    }

    @ApiOperation(value = "获取驻村帮扶队伍详情", httpMethod = "GET")
    @ApiImplicitParam(name = "teamId", value = "驻村帮扶队伍ID", paramType = "path", required = true)
    @HasPermission(value = "resident_assistance_work_look")
    @GetMapping("/{teamId}")
    public HelpTeamVO getHelpTeamVOByTeamId(@PathVariable Long teamId) {
        return helpTeamService.getHelpTeamVOByTeamId(teamId);
    }

    @ApiOperation(value = "驻村帮扶选人接口", httpMethod = "GET")
    @ApiImplicitParam(name = "orgId", value = "组织Id", paramType = "path", required = true)
    @HasPermission(value = "resident_assistance_work_add")
    @GetMapping("/select_member/{orgId}")
    public List<HelpTeamMemberVO> selectHelpTeamMemberVO(@PathVariable Long orgId) {
        return helpTeamService.selectHelpTeamMemberVO(orgId);
    }

}
