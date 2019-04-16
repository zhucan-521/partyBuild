package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.partybuild.dto.RegistryDto;
import com.egovchina.partybuilding.partybuild.service.TabSysUserService;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: huang
 * Date: 2018/12/12
 */
@Api(tags = "党员-党籍模块-官颖鑫")
@RestController
@RequestMapping("/registry")
public class RegistryController {

    @Autowired
    TabSysUserService sysUserService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取党籍列表")
    @GetMapping("/getList")
    @ApiImplicitParam(name = "userId", value = "用户id",paramType = "query",required = true,dataType = "long")
    public List<RegistryDto> getRegistryList(Long userId){
        return sysUserService.getRegistryList(userId);
    }

    /**
     * getById
     */
    @ApiOperation(value = "获取用户党籍信息")
    @GetMapping("/getById")
    @ApiImplicitParam(name = "userId", value = "用户id",paramType = "query",required = true,dataType = "long")
    public SysUser getById(Long userId){
        return sysUserService.getRegistryId(userId);
    }

    /**
     * updata
     */
    @ApiOperation(value = "更新用户党籍信息")
    @PutMapping("/updata")
    public boolean updata(@ApiParam(value = "党籍信息")@Valid @RequestBody SysUser sysUser){
        return sysUserService.updataUser(sysUser);
    }
}
