package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.HelpRecordDTO;
import com.egovchina.partybuilding.partybuild.dto.HelpRecordTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.HelpRecordQueryBean;
import com.egovchina.partybuilding.partybuild.service.HelpRecordService;
import com.egovchina.partybuilding.partybuild.vo.HelpRecordVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "党组织-驻村帮扶记录-v1-朱灿")
@RequestMapping("/v1/help-team-records")
@RestController
public class HelpRecordController {

    @Autowired
    private HelpRecordService helpRecordService;

    @ApiOperation(value = "新增驻村帮扶记录", httpMethod = "POST")
    @HasPermission(value = "resident_assistance_work_record_add")
    @PostMapping
    public ReturnEntity addHelpRecord(@Validated @RequestBody @ApiParam("驻村帮扶记录") HelpRecordDTO helpRecordDTO) {
        return ReturnUtil.buildReturn(helpRecordService.addHelpRecord(helpRecordDTO));
    }

    @ApiOperation(value = "更新驻村帮扶记录", httpMethod = "PUT")
    @HasPermission(value = "resident_assistance_work_record_edit")
    @PutMapping
    public ReturnEntity updateHelpTeamDTOByTeamId(@Validated @RequestBody @ApiParam("驻村帮扶记录") HelpRecordDTO helpRecordDTO) {
        return ReturnUtil.buildReturn(helpRecordService.editHelpRecord(helpRecordDTO));
    }

    @ApiOperation(value = "删除帮扶记录", httpMethod = "DELETE")
    @ApiImplicitParam(name = "recordId", value = "帮扶记录主键", paramType = "path", required = true)
    @HasPermission(value = "resident_assistance_work_record_del")
    @DeleteMapping("/{recordId}")
    public ReturnEntity deleteHelpTeam(@PathVariable Long recordId) {
        return ReturnUtil.buildReturn(helpRecordService.removeHelpRecord(recordId));
    }

    @ApiOperation(value = "帮扶记录列表", httpMethod = "GET")
    @HasPermission(value = "resident_assistance_work")
    @GetMapping
    public PageInfo<HelpRecordVO> selectActiveHelpTeam(HelpRecordQueryBean helpRecordQueryBean, Page page) {
        PageInfo<HelpRecordVO> pageInfo = new PageInfo<>(helpRecordService.selectiveHelpRecords(helpRecordQueryBean, page));
        return pageInfo;
    }

    @ApiOperation(value = "帮扶记录详情", httpMethod = "GET")
    @ApiImplicitParam(name = "recordId", value = "帮扶记录ID", paramType = "path", required = true)
    @HasPermission(value = "resident_assistance_work_look")
    @GetMapping("/{recordId}")
    public HelpRecordVO getHelpTeamVOByTeamId(@PathVariable Long recordId) {
        return helpRecordService.getHelpRecordVOByRecordId(recordId);
    }

    @ApiOperation(value = "选择队伍接口", httpMethod = "GET")
    @ApiImplicitParam(name = "orgId", value = "组织Id", paramType = "path", required = true)
    @HasPermission(value = "resident_assistance_work_record_add")
    @GetMapping("/select_team/{orgId}")
    public List<HelpRecordTeamDTO> selectHelpRecordTeamDTO(@PathVariable Long orgId) {
        return helpRecordService.selectHelpRecordTeamDTO(orgId);
    }

}
