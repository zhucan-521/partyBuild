package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.LinkLeaderDTO;
import com.egovchina.partybuilding.partybuild.entity.LinkLeaderQueryBean;
import com.egovchina.partybuilding.partybuild.service.JointPointInfoService;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.LinkLeaderVO;
import com.egovchina.partybuilding.partybuild.vo.UserDeptPositionVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 描述:
 * 联点信息控制器
 *
 * @author wuyunjie
 * Date 2019-04-19 16:01
 */
@Api(tags = "党组织-联点信息-v1-吴云杰")
@RestController
@RequestMapping("/v1/joint-points")
public class JointPointInfoController {

    @Autowired
    private JointPointInfoService jointPointInfoService;

    @ApiOperation(value = "跟据党员id查看联点领导信息", notes = "跟据党员id查看联点领导信息")
    @ApiImplicitParam(value = "党员id", name = "userId", paramType = "path", required = true)
    @HasPermission("party_lianDianInformation_add")
    @GetMapping("/positives/{userId}")
    public UserDeptPositionVO getJointPointInfoByUserId(@PathVariable Long userId) {
        return jointPointInfoService.selectJointByUserId(userId);
    }

    @ApiOperation(value = "查看联点领导列表详情", notes = "查看联点领导列表详情")
    @HasPermission({"party_lianDianInformation", "party_lianDianInformation_detail"})
    @GetMapping
    public PageInfo<LinkLeaderVO> getJointPointInfoByDeptIdList(@Validated LinkLeaderQueryBean linkLeaderQueryBean, Page page) {
        List<LinkLeaderVO> list = jointPointInfoService.selectUserDeptByDeptId(linkLeaderQueryBean, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "选择通过名字和身份证完全匹配选择联点领导")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "组织id", name = "orgId", paramType = "path", dataType = "Long", required = true),
            @ApiImplicitParam(value = "身份证", name = "idCardNo", paramType = "query", dataType = "String"),
            @ApiImplicitParam(value = "姓名", name = "realName", paramType = "query", dataType = "String")
    })
    @HasPermission("party_lianDianInformation_add")
    @GetMapping("/lead-team-members/{orgId}")
    public PageInfo<LeadTeamMemberVO> getLeadTeamMembersByIdCardNoOrRealName(@PathVariable Long orgId, String idCardNo, String realName, Page page) {
        List<LeadTeamMemberVO> list = jointPointInfoService.getLeadTeamMembersByIdCardNoOrRealName(orgId, idCardNo, realName, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "删除联点领导", notes = "删除联点领导")
    @ApiImplicitParam(value = "组织联点领导联点主键", name = "linkLedaerId", paramType = "path", required = true)
    @HasPermission("party_lianDianInformation_del")
    @DeleteMapping("/{linkLedaerId}")
    public ReturnEntity deleteJointPointInfo(@PathVariable Long linkLedaerId) {
        return ReturnUtil.buildReturn(jointPointInfoService.delJointPointInfoByLinkLedaerId(linkLedaerId));
    }

    @ApiOperation(value = "保存联点信息", notes = "保存联点信息")
    @HasPermission({"party_lianDianInformation_add", "party_lianDianInformation_edit"})
    @PostMapping
    public ReturnEntity addJointPointInfo(@ApiParam("联点领导和联点活动信息") @RequestBody @Validated LinkLeaderDTO linkLeaderDTO) {
        return ReturnUtil.buildReturn(jointPointInfoService.saveJointPointInfo(linkLeaderDTO));
    }
}
