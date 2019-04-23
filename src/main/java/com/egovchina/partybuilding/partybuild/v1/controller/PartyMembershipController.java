package com.egovchina.partybuilding.partybuild.v1.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.RegistryDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMembership;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.v1.service.SysUserService;
import com.egovchina.partybuilding.partybuild.v1.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.v1.dto.RegistryDTO;
import com.egovchina.partybuilding.partybuild.v1.entity.MembershipQueryBean;
import com.egovchina.partybuilding.partybuild.v1.service.PartyMembershipService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: GuanYingxin
 * Date: 2019/04/22
 */
@Api(tags = "党员-党籍模块v1-官颖鑫")
@RestController
@RequestMapping("/v1/memberships")
public class PartyMembershipController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PartyMembershipService partyMembershipService;

    /**
     * 根据用户id获取用户党籍实体类信息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id获取用户党籍实体类信息", notes = "根据userId获取用户党籍实体类信息", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "long")
    @GetMapping("/{userId}")
    public TabPbPartyMembership getTabPbPartyMembershipByUserId(@PathVariable(value = "userId") Long userId) {
        return partyMembershipService.getTabPbPartyMembershipByUserId(userId);
    }

    /**
     * 更新用户党籍信息
     *
     * @param membershipDTO
     * @return
     */
    @ApiOperation(value = "更新用户党籍信息", notes = "更新用户党籍信息", httpMethod = "PUT")
    @PutMapping
    public boolean updateMembership(@ApiParam(value = "党籍信息", required = true) @Valid @RequestBody MembershipDTO membershipDTO) {
        return sysUserService.updateMembership(membershipDTO);
    }

    /**
     * 根据人员类别和党籍处理获取党籍实体类列表
     *
     * @param membershipQueryBean
     * @param page
     * @return
     */
    @ApiOperation(value = "根据人员类别和党籍处理获取党籍实体类列表", notes = "根据identityType和type获取党籍实体类列表", httpMethod = "GET")
    @GetMapping("/like")
    public PageInfo<TabPbPartyMembership> getTabPbMembershipByIdentityTypeAndTypeList(@ApiParam(value = "党籍信息", required = true) @Valid MembershipQueryBean membershipQueryBean, @ApiParam("分页对象") Page page) {
        return new PageInfo<>(partyMembershipService.getTabPbMembershipByIdentityTypeAndTypeList(membershipQueryBean, page));
    }

    /**
     * 获取党籍实体类列表
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "获取党籍实体类列表", notes = "获取党籍实体类列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<TabPbPartyMembership> getTabPbMembershipList(@ApiParam("分页对象") Page page) {
        return new PageInfo<>(partyMembershipService.getTabPbMembershipList(page));
    }

    /**
     * 获取党籍列表
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取党籍列表", notes = "获取党籍列表", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", required = true, dataType = "long")
    @GetMapping("/multiple")
    public List<RegistryDTO> getRegistryList(Long userId) {
        return sysUserService.getRegistryV1List(userId);
    }

    /**
     * 获取用户党籍信息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户党籍信息", notes = "获取用户党籍信息", httpMethod = "GET")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", required = true, dataType = "long")
    @GetMapping("/single")
    public SysUser getRegistryByUserId(Long userId) {
        return sysUserService.getRegistryByUserId(userId);
    }
}
