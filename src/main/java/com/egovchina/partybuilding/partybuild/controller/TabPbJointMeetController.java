package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.TabPbJointMeetDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import com.egovchina.partybuilding.partybuild.service.ITabPbJointMeetService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.egovchina.partybuilding.common.util.UserContextHolder.currentUser;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2019/01/02
 */

@Api(tags = "党组织-联席单位管理 杨颖翔")
@RestController
@RequestMapping("/jointMeet")
public class TabPbJointMeetController {

    @Autowired
    private ITabPbJointMeetService jointMeetService;

    @ApiOperation(value = "添加联席单位", notes = "联系成员单位必须有")
    @PostMapping()
    public void save(@RequestBody TabPbJointMeetDto meet) {
        this.jointMeetService.save(meet);
    }

    @ApiOperation(value = "添加联席成员单位", notes = "jointMeetId 为必填")
    @PostMapping("/org")
    public ReturnEntity save(@RequestBody List<TabPbJointMeetOrg> list) {
        return this.jointMeetService.save(list);
    }

    @ApiOperation(value = "修改联席单位")
    @PutMapping()
    public ReturnEntity update(@RequestBody TabPbJointMeet meet) {
        int retVal = jointMeetService.update(meet);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改联席成员单位")
    @PutMapping("/org")
    public ReturnEntity update(@RequestBody TabPbJointMeetOrg org) {
        int update = this.jointMeetService.update(org);
        return ReturnUtil.buildReturn(update);
    }

    @ApiOperation(value = "删除联席单位")
    @DeleteMapping()
    public void delete(Long jointMeetId) {
        this.jointMeetService.delete(new TabPbJointMeet().setJointMeetId(jointMeetId));
    }

    @ApiOperation(value = "删除联席成员单位")
    @DeleteMapping("/org")
    public void deleteOrg(Long memberOrgId) {
        this.jointMeetService.delete(new TabPbJointMeetOrg().setMemberOrgId(memberOrgId));
    }

    @ApiOperation(value = "查询联席单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织主键"),
            @ApiImplicitParam(name = "orgRange", value = "范围"),
    })
    @GetMapping()
    public PageInfo<TabPbJointMeet> select(Long orgId, Long orgRange, @ApiParam Page page) {
        var meet = new TabPbJointMeet()
                .setOrgRange(orgRange)
                .setOrgId(orgId);
        return this.jointMeetService.select(meet, page);
    }

    @ApiOperation(value = "分页查询联席单位成员")
    @GetMapping("/org")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jointMeetId", value = "联席主键"),
            @ApiImplicitParam(name = "orgId", value = "联席组织主键", required = true),
            @ApiImplicitParam(name = "unitName", value = "成员组织名称")
    })
    public PageInfo<TabPbJointMeetOrg> select(Long jointMeetId, Long orgId, String unitName, @ApiParam Page page) {
        var org = new TabPbJointMeetOrg()
                .setJointMeetId(jointMeetId)
                .setOrgId(orgId)
                .setUnitName(unitName);
        if (orgId == null || orgId < 0) {
            org.setOrgId(currentUser().getDeptId().longValue());
        }
        return this.jointMeetService.select(org, page);
    }
}
