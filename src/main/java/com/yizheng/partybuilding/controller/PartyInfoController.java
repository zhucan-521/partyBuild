package com.yizheng.partybuilding.controller;

import com.alibaba.fastjson.JSON;
import com.yizheng.partybuilding.dto.SysUserDto;
import com.yizheng.partybuilding.entity.TabPbPartyEducation;
import com.yizheng.partybuilding.entity.TabPbPartyJobTitle;
import com.yizheng.partybuilding.entity.TabPbPartyWork;
import com.yizheng.partybuilding.service.inf.*;
import com.yizheng.partybuilding.system.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 党员信息
 * <p>
 * Created by FanYanGen on 2019/4/11 11:41
 */
@Api(tags = "党员-党员信息模块v2-范焱根")
@RestController
@RequestMapping("/partyv2")
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
        System.out.println(JSON.toJSONString(sysUser));
        return userService.updateSysUserInfo(sysUser);
    }

}
