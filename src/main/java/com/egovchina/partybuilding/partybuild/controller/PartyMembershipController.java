package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.service.PartyMembershipService;
import com.egovchina.partybuilding.partybuild.vo.MembershipVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GuanYingxin
 * Date: 2019/04/22
 */
@Api(tags = "党员-党籍模块v1-官颖鑫")
@RestController
@RequestMapping("/v1/memberships")
public class PartyMembershipController {

    @Autowired
    private PartyMembershipService partyMembershipService;

    @ApiOperation(value = "根据用户id获取党籍列表", notes = "根据用户id获取党籍列表", httpMethod = "GET")
    @ApiImplicitParam(value = "用户id", name = "userId", dataType = "long", paramType = "path", required = true)
    @GetMapping("/{userId}")
    public PageInfo<MembershipVO> getMembershipListByCondition(@PathVariable Long userId, @ApiParam("分页对象") Page page) {
        return new PageInfo<>(partyMembershipService.getMembershipVOListByCondition(userId, page));
    }
}
