package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.partybuild.dto.SysUserDto;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.PartyInformationService;
import com.egovchina.partybuilding.partybuild.system.service.SysUserService;
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
    private PartyInformationService partyInformationService;

    @ApiOperation(value = "补录党员信息", notes = "补录党员信息", httpMethod = "POST")
    @PostMapping("/save")
    public int save(@RequestBody SysUserDto sysUser) {
        return partyInformationService.saveSysUserInfo(sysUser);
    }

    @ApiOperation(value = "更新党员信息", notes = "更新党员信息", httpMethod = "PUT")
    @PutMapping("/update")
    public int updateSysUserInfo(@RequestBody SysUserDto sysUser) {
        return partyInformationService.updateSysUserInfo(sysUser);
    }

}
