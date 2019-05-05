package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.OrgUpgradeDTO;
import com.egovchina.partybuilding.partybuild.dto.OrgUpgradedPersonnelTransferDTO;
import com.egovchina.partybuilding.partybuild.service.OrgUpgradeService;
import com.egovchina.partybuilding.partybuild.vo.DirectPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.OrgUpgradeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = "党组织-党组织升级-v1-吴云杰")
@RestController
@RequestMapping("/v1/org-upgrades")
public class OrgUpgradeController {
    @Autowired
    private OrgUpgradeService orgUpgradeService;
    
    @ApiOperation(value = "新增组织升格信息",notes = "新增组织升格信息")
    @PostMapping
    public ReturnEntity addOrgUpgrade(@ApiParam("组织升格信息") @RequestBody @Validated OrgUpgradeDTO orgUpgradeDto) {
        return ReturnUtil.buildReturn(orgUpgradeService.addOrgUpgrade(orgUpgradeDto));
    }

    @ApiOperation(value = "升格新建组织支部", notes = "升格新建组织支部")
    @PostMapping("/branch")
    public ReturnEntity addNewOrgBranch(@ApiParam("组织升格信息") @RequestBody @Validated OrgUpgradeDTO orgUpgradeDto) {
        return ReturnUtil.buildReturn(orgUpgradeService.updateByPrimaryKeySelective(orgUpgradeDto));
    }

    @ApiOperation(value = "查看组织升格信息",notes = "查看组织升格信息")
    @ApiImplicitParam(value = "组织id",name = "deptId" ,paramType = "path" , required = true)
    @GetMapping("/{deptId}")
    public OrgUpgradeVO getOrgUpgrade(@PathVariable Long deptId){
        return orgUpgradeService.selectOrgUpgradeByDeptId(deptId);
    }

    @ApiOperation(value = "批量转移人员", notes = "批量转移人员")
    @PostMapping("/personnel-transfer")
    public ReturnEntity updateListUser(@ApiParam("人员集合信息")
            @RequestBody @Validated OrgUpgradedPersonnelTransferDTO orgUpgradedPersonnelTransferDTO) {
        return ReturnUtil.buildReturn(orgUpgradeService.batchDeptIdByUserId(orgUpgradedPersonnelTransferDTO));
    }

    @ApiOperation(value = "查询组织下的直属党员", notes = "查询组织下的直属党员")
    @ApiImplicitParam(value = "组织id",name = "deptId" ,paramType = "path" , required = true)
    @GetMapping("/members/{deptId}")
    public PageInfo<DirectPartyMemberVO> getDirectPartyMemberByDeptId(@PathVariable Long deptId) {
        return new PageInfo<>(orgUpgradeService.getDirectPartyMemberByDeptId(deptId));

    }
}
