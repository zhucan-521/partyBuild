package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.partybuild.dto.SysUserDto;
import com.egovchina.partybuilding.partybuild.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 党员信息
 * <p>
 * Created by FanYanGen on 2019/4/11 11:41
 */
@Api(tags = "党员-党员信息模块v2-范焱根")
@RestController
@RequestMapping("/party/v2")
public class PartyInfoController {

    @Autowired
    private SysUserService userService;

    @ApiOperation(value = "党员信息补录", notes = "党员信息补录", httpMethod = "POST")
    @PostMapping("/save")
    public int save(@RequestBody SysUserDto sysUser) {
        return userService.saveSysUserInfo(sysUser);
    }

    @ApiOperation(value = "党员信息更新", notes = "党员信息更新", httpMethod = "PUT")
    @PutMapping("/update")
    public int updateSysUserInfo(@RequestBody SysUserDto sysUser) {
        return userService.updateSysUserInfo(sysUser);
    }

}
