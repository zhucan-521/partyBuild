package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeDTO;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.StreetCommitteeQueryBean;
import com.egovchina.partybuilding.partybuild.service.StreetCommitteeService;
import com.egovchina.partybuilding.partybuild.vo.StreetCommitteeMemberVO;
import com.egovchina.partybuilding.partybuild.vo.StreetCommitteeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuyunjie
 * @version 1.0
 * @date 2018/12/04
 */
@Api(tags = "党组织-街道大公委-v1 吴云杰")
@RestController
@RequestMapping("/v1/street-committees")
public class StreetCommitteeController {

    @Autowired
    private StreetCommitteeService streetCommitteeService;

    @ApiOperation(value = "添加街道大公委", notes = "街道大公委对象, 如果orgId存在则更新, 否则新增街道大公委数据")
    @PostMapping
    public ReturnEntity addStreetCommittee(@ApiParam("街道大公委和成员集合信息") @RequestBody @Validated StreetCommitteeDTO streetCommitteeDTO) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.saveStreetCommittee(streetCommitteeDTO));
    }

    @ApiOperation(value = "获取街道大公委列表")
    @GetMapping
    public PageInfo<StreetCommitteeVO> getStreetCommitteeList(StreetCommitteeQueryBean streetCommitteeQueryBean, Page page) {
        Long rangeDeptId = streetCommitteeQueryBean.getOrgId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            streetCommitteeQueryBean.setOrgId(UserContextHolder.getOrgId());
        }
        return this.streetCommitteeService.getStreetCommitteeList(streetCommitteeQueryBean, page);
    }

    @ApiOperation(value = "添加街道大公委,可批量添加成员", notes = "1. 街道大公委对象, 如果orgId存在则更新, 否则新增街道大公委数据 2. 添加街道大公委成员数据")
    @PostMapping("/add-members")
    public ReturnEntity addStreetCommitteeAndMember(@ApiParam("街道大公委和成员集合信息") @RequestBody @Validated StreetCommitteeDTO streetCommitteeDTO) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.addStreetCommittee(
                streetCommitteeDTO, streetCommitteeDTO.getStreetCommitteMemberList()));
    }

    @ApiOperation(value = "删除街道大公委数据", notes = "删除街道大公委将同时删除该街道大公委的成员")
    @ApiImplicitParam(value = "大工委id",name = "grantCommitteeId",paramType = "path" , required = true)
    @DeleteMapping("/{grantCommitteeId}")
    public ReturnEntity deleteStreetCommittee(@PathVariable Long grantCommitteeId) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.deleteStreetCommittee(grantCommitteeId));
    }

    @ApiOperation(value = "获取街道大公委数据", notes = "通过街道大公委主键grantCommitteeId查询")
    @ApiImplicitParam(value = "大工委id",name = "grantCommitteeId", paramType = "path" ,required = true)
    @GetMapping("/{grantCommitteeId}")
    public StreetCommitteeVO getStreetCommitteeById(@PathVariable Long grantCommitteeId) {
        return this.streetCommitteeService.getStreetCommitteeById(grantCommitteeId);
    }

    // ----------- 街道大公委成员相关 -------------
    @ApiOperation(value = "添加街道大公委成员", notes = "grantCommitteeId 和userId 必须存在")
    @PostMapping("/one-member")
    public ReturnEntity addStreetCommitteeMember(@ApiParam("成员信息")
            @RequestBody @Validated StreetCommitteeMemberDTO streetCommitteeMemberDTO) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.addStreetCommitteeMember(streetCommitteeMemberDTO));
    }

    @ApiOperation(value = "添加街道大公委成员数据", notes = "grantCommitteeId 和userId 必须存在")
    @PostMapping("/members")
    public ReturnEntity addStreetCommitteeMemberList(@ApiParam("成员集合信息")
            @RequestBody @Validated List<StreetCommitteeMemberDTO> streetCommitteeMemberDTOList) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.addStreetCommitteeMembers(streetCommitteeMemberDTOList));
    }

    @ApiOperation(value = "获取大公委成员数据", notes = "grantCommitteeMemberId为街道大公委成员明细表的主键")
    @ApiImplicitParam(value = "大工委人员id",name = "grantCommitteeMemberId" ,paramType = "path" , required = true)
    @GetMapping("/members/{grantCommitteeMemberId}")
    public StreetCommitteeMemberVO getStreetCommitteeMemberById(
            @PathVariable Long grantCommitteeMemberId) {
        return this.streetCommitteeService.getStreetCommitteeMemberById(grantCommitteeMemberId);
    }

    @ApiOperation(value = "通过grantCommitteeMemberId 删除成员", notes = "grantCommitteeMemberId为街道大公委成员明细表的主键")
    @ApiImplicitParam(value = "大工委人员id" ,name = "grantCommitteeMemberId" ,paramType = "path", required = true)
    @DeleteMapping("/members/{grantCommitteeMemberId}")
    public ReturnEntity deleteStreetCommitteeMemberById(
            @PathVariable Long grantCommitteeMemberId) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.deleteStreetCommitteeMemberById(grantCommitteeMemberId));
    }

    @ApiOperation(value = "通过grantCommitteeId查询成员", notes = "grantCommitteeId为街道大公委成员明细表的主键")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "大公委主键", name = "grantCommitteeId",dataType = "Long"),
            @ApiImplicitParam(value = "人员名称", name = "personName",dataType = "String"),
            @ApiImplicitParam(value = "职务名称", name = "positiveName",dataType = "String"),
    })
    @GetMapping("/members")
    public PageInfo<StreetCommitteeMemberVO> getStreetCommitteeMemberList(
            Long grantCommitteeId,String personName, String positiveName, Page page) {
        return this.streetCommitteeService.getStreetCommitteeMemberList(grantCommitteeId,personName, positiveName, page);
    }

}
