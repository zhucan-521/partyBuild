package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.service.PartyMembershipService;
import com.egovchina.partybuilding.partybuild.vo.MembershipVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "获取党籍列表", notes = "获取党籍列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<MembershipVO> getMembershipListByCondition(@ApiParam("分页对象") Page page) {
        return new PageInfo<>(partyMembershipService.getMembershipVOListByCondition(page));
    }
}
