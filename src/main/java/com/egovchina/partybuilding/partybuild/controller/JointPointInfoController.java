package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.LinkLeaderDTO;
import com.egovchina.partybuilding.partybuild.service.JointPointInfoService;
import com.egovchina.partybuilding.partybuild.vo.LinkLeaderVO;
import com.egovchina.partybuilding.partybuild.vo.UserDeptPositionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @GetMapping("/positives/{userId}")
    public UserDeptPositionVO getJointPointInfoByUserId(@PathVariable Long userId) {
        return jointPointInfoService.selectJointByUserId(userId);
    }

    @ApiOperation(value = "查看联点领导列表详情", notes = "查看联点领导列表详情")
    @ApiImplicitParam(value = "组织ID", name = "deptId", paramType = "path", required = true)
    @GetMapping("/{deptId}")
    public List<LinkLeaderVO> getJointPointInfoByDeptIdList(@PathVariable Long deptId) {
        return jointPointInfoService.selectUserDeptByDeptId(deptId);
    }

    @ApiOperation(value = "删除联点领导", notes = "删除联点领导")
    @ApiImplicitParam(value = "组织联点领导联点主键", name = "linkLedaerId", paramType = "path", required = true)
    @DeleteMapping("/{linkLedaerId}")
    public ReturnEntity deleteJointPointInfo(@PathVariable Long linkLedaerId) {
        return ReturnUtil.buildReturn(jointPointInfoService.delJointPointInfoByLinkLedaerId(linkLedaerId));
    }

    @ApiOperation(value = "保存联点信息", notes = "保存联点信息")
    @PostMapping
    public ReturnEntity addJointPointInfo(@ApiParam("联点领导和联点活动信息") @RequestBody @Validated LinkLeaderDTO linkLeaderDTO) {
        return ReturnUtil.buildReturn(jointPointInfoService.saveJointPointInfo(linkLeaderDTO));
    }
}
