package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.JointMeetDTO;
import com.egovchina.partybuilding.partybuild.dto.JointMeetOrgDTO;
import com.egovchina.partybuilding.partybuild.entity.JointMeetOrgQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import com.egovchina.partybuilding.partybuild.service.JointMeetService;
import com.egovchina.partybuilding.partybuild.vo.JointMeetOrgVO;
import com.egovchina.partybuilding.partybuild.vo.JointMeetVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuyunjie
 * @version 1.0
 * @date 2019/01/02
 */

@Api(tags = "党组织-联席会管理-v1 吴云杰")
@RestController
@RequestMapping("/v1/joint-meets")
public class JointMeetController {

    @Autowired
    private JointMeetService jointMeetService;

    @ApiOperation(value = "添加联席会及成员信息", notes = "联系成员单位必须有")
    @PostMapping
    public ReturnEntity addJointMeet(@ApiParam("联席会及成员集合信息") @RequestBody @Validated JointMeetDTO jointMeetDto) {
        return ReturnUtil.success(this.jointMeetService.addJointMeet(jointMeetDto));
    }

    @ApiOperation(value = "添加联席会成员", notes = "jointMeetId 为必填")
    @PostMapping("/members")
    public ReturnEntity addJointMeetOrgList(@ApiParam("联席会成员集合信息") @RequestBody @Validated List<JointMeetOrgDTO> jointMeetOrgs) {
        return this.jointMeetService.addJointMeetOrgList(jointMeetOrgs);
    }

    @ApiOperation(value = "修改联席会")
    @PutMapping
    public ReturnEntity updateJointMeet(@ApiParam("联席会及成员集合信息") @RequestBody @Validated JointMeetDTO jointMeetDto) {
        return ReturnUtil.buildReturn(jointMeetService.updateJointMeet(jointMeetDto));
    }

    @ApiOperation(value = "修改联席会成员")
    @PutMapping("/members")
    public ReturnEntity updateJointMeetOrg(@ApiParam("联席会成员信息") @RequestBody @Validated JointMeetOrgDTO jointMeetOrgDTO) {
        return ReturnUtil.buildReturn(this.jointMeetService.updateJointMeetOrg(jointMeetOrgDTO));
    }

    @ApiOperation(value = "删除联席会")
    @ApiImplicitParam(name = "jointMeetId", value = "联席会id", required = true, paramType = "path")
    @DeleteMapping("/{jointMeetId}")
    public void deleteJointMeet(@PathVariable Long jointMeetId) {
        this.jointMeetService.deleteJointMeet(new TabPbJointMeet().setJointMeetId(jointMeetId));
    }

    @ApiOperation(value = "删除联席会成员")
    @ApiImplicitParam(name = "memberOrgId", value = "联席会成员Id", required = true, paramType = "path")
    @DeleteMapping("/members/{memberOrgId}")
    public void deleteJointMeetOrg(@PathVariable Long memberOrgId) {
        this.jointMeetService.deleteJointMeetOrg(new TabPbJointMeetOrg().setMemberOrgId(memberOrgId));
    }

    @ApiOperation(value = "查询联席会列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织主键"),
            @ApiImplicitParam(name = "orgRange", value = "范围"),
    })
    @GetMapping
    public PageInfo<JointMeetVO> getJointMeetList(Long orgId, Long orgRange, @ApiParam Page page) {
        var meet = new TabPbJointMeet()
                .setOrgRange(orgRange)
                .setOrgId(orgId);
        return this.jointMeetService.getJointMeetList(meet, page);
    }

    @ApiOperation(value = "分页查询联席会成员")
    @GetMapping("/members")
    public PageInfo<JointMeetOrgVO> getJointMeetOrgList(JointMeetOrgQueryBean jointMeetOrgQueryBean, Page page) {
        Long orgId = jointMeetOrgQueryBean.getOrgId();
        if (orgId == null || orgId == 0) {
            jointMeetOrgQueryBean.setOrgId(UserContextHolder.getOrgId());
        }
        return this.jointMeetService.getJointMeetOrgList(jointMeetOrgQueryBean, page);
    }
}
