package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.OrganizationUpgradeAndChangeDto;
import com.egovchina.partybuilding.partybuild.dto.OrganizationUpgradeDto;
import com.egovchina.partybuilding.partybuild.dto.Personnel;
import com.egovchina.partybuilding.partybuild.entity.SysDeptUpgradeTemp;
import com.egovchina.partybuilding.partybuild.service.IOrganizationUpgradeService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 描述:
 * 党组织升级控制器
 *
 * @author wuyunjie
 * Date 2019-01-03 14:29
 */
@Api(tags = "党组织-党组织升级-吴云杰")
@RestController
@RequestMapping("/organizationUpgrade")
public class OrganizationUpgradeController {
    @Autowired
    private IOrganizationUpgradeService iOrganizationUpgradeService;
    
    @ApiOperation(value = "升格修改组织信息",notes = "升格修改组织信息")
    @PostMapping("/updateOrganization")
    public ReturnEntity updateOrganization(@RequestBody @Validated OrganizationUpgradeAndChangeDto organizationUpgradeAndChangeDto){
        int retVal = iOrganizationUpgradeService.insertSelective(organizationUpgradeAndChangeDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "升格新建组织支部", notes = "升格新建组织支部",httpMethod = "POST")
    @PostMapping("/upgradedNewOrganizationBranch")
    public ReturnEntity upgradedNewOrganizationBranch(@RequestBody @Validated SysDeptUpgradeTemp sysDeptUpgradeTemp) {
        int retVal = iOrganizationUpgradeService.updateByPrimaryKeySelective(sysDeptUpgradeTemp);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查看组织升格信息",notes = "查看组织升格信息")
    @GetMapping("/showOrgUpgradeInfo")
    public OrganizationUpgradeAndChangeDto showOrgUpgradeInfo(Long deptId){
        return iOrganizationUpgradeService.selectByDeptId(deptId);
    }

    @ApiOperation(value = "批量修改人员组织id", notes = "批量修改人员组织id")
    @PostMapping("/updateListUser")
    public ReturnEntity updateListUser(@ApiParam(value = "组织升格人员转移dto") @RequestBody @Validated OrganizationUpgradeDto organizationUpgradeDto) {
        int retVal = iOrganizationUpgradeService.batchDeptIdByUserId(organizationUpgradeDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查询组织下的直属党员", notes = "查询组织下的直属党员")
    @GetMapping("/getAllUserByDeptId")
    public PageInfo<Personnel> getAllUserByDeptId(
            @RequestParam @ApiParam(value = "组织id", required = true) Long deptId) {
        return new PageInfo<>(iOrganizationUpgradeService.getAllUserByDeptId(deptId));

    }
}
