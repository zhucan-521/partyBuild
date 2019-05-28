package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeDTO;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.StreetCommitteeMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.StreetCommitteeQueryBean;
import com.egovchina.partybuilding.partybuild.service.StreetCommitteeService;
import com.egovchina.partybuilding.partybuild.vo.StreetCommitteeMemberVO;
import com.egovchina.partybuilding.partybuild.vo.StreetCommitteeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "修改街道大工委", notes = "修改街道大工委")
    @PutMapping
    public ReturnEntity updateStreetCommittee(@ApiParam("街道大公委信息") @RequestBody @Validated StreetCommitteeDTO streetCommitteeDTO) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.updateStreetCommittee(streetCommitteeDTO));
    }

    @ApiOperation(value = "条件查询街道大公委列表")
    @GetMapping
    public PageInfo<StreetCommitteeVO> getStreetCommitteeList(StreetCommitteeQueryBean streetCommitteeQueryBean, Page page) {
        return this.streetCommitteeService.getStreetCommitteeList(streetCommitteeQueryBean, page);
    }

    @ApiOperation(value = "删除街道大公委数据", notes = "删除街道大公委将同时删除该街道大公委的成员")
    @ApiImplicitParam(value = "大工委id", name = "grantCommitteeId", paramType = "path", required = true)
    @DeleteMapping("/{grantCommitteeId}")
    public ReturnEntity deleteStreetCommittee(@PathVariable Long grantCommitteeId) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.deleteStreetCommittee(grantCommitteeId));
    }

    @ApiOperation(value = "获取街道大公委数据", notes = "通过街道大公委主键grantCommitteeId查询")
    @ApiImplicitParam(value = "大工委id", name = "grantCommitteeId", paramType = "path", required = true)
    @GetMapping("/{grantCommitteeId}")
    public StreetCommitteeVO getStreetCommitteeById(@PathVariable Long grantCommitteeId) {
        return this.streetCommitteeService.getStreetCommitteeById(grantCommitteeId);
    }

    // ----------- 街道大公委成员相关 -------------
    @ApiOperation(value = "添加街道大公委成员", notes = "grantCommitteeId 和userId 必须存在")
    @PostMapping("/one-members")
    public ReturnEntity addStreetCommitteeMember(@ApiParam("成员信息")
                                                 @RequestBody @Validated StreetCommitteeMemberDTO streetCommitteeMemberDTO) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.addStreetCommitteeMember(streetCommitteeMemberDTO));
    }

    @ApiOperation(value = "获取大公委成员数据", notes = "grantCommitteeMemberId为街道大公委成员明细表的主键")
    @ApiImplicitParam(value = "大工委人员id", name = "grantCommitteeMemberId", paramType = "path", required = true)
    @GetMapping("/members/{grantCommitteeMemberId}")
    public StreetCommitteeMemberVO getStreetCommitteeMemberById(
            @PathVariable Long grantCommitteeMemberId) {
        return this.streetCommitteeService.getStreetCommitteeMemberById(grantCommitteeMemberId);
    }

    @ApiOperation(value = "通过成员id删除成员", notes = "grantCommitteeMemberId为街道大公委成员明细表的主键")
    @ApiImplicitParam(value = "大工委人员id", name = "grantCommitteeMemberId", paramType = "path", required = true)
    @DeleteMapping("/members/{grantCommitteeMemberId}")
    public ReturnEntity deleteStreetCommitteeMemberById(
            @PathVariable Long grantCommitteeMemberId) {
        return ReturnUtil.buildReturn(this.streetCommitteeService.deleteStreetCommitteeMemberById(grantCommitteeMemberId));
    }

    @ApiOperation(value = "条件查询成员列表", notes = "grantCommitteeId为街道大公委成员明细表的主键")
    @GetMapping("/members")
    public PageInfo<StreetCommitteeMemberVO> getStreetCommitteeMemberList(
            @Validated  StreetCommitteeMemberQueryBean streetCommitteeMemberQueryBean, Page page) {
        return this.streetCommitteeService.getStreetCommitteeMemberList(streetCommitteeMemberQueryBean, page);
    }

    @ApiOperation(value = "判断组织是否可以添加工委成员", notes = "判断组织是否可以添加工委成员")
    @ApiImplicitParam(value = "组织id", name = "orgId", dataType = "Long", paramType = "path", required = true)
    @GetMapping("/check/{orgId}")
    public Boolean checkStreetCommitteeWhetherAddMembers(@PathVariable Long orgId) {
        return this.streetCommitteeService.checkStreetCommitteeWhetherAddMembers(orgId);
    }
}
