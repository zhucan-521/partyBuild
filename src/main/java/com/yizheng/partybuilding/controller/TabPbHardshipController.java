package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbHardshipDto;
import com.yizheng.partybuilding.entity.TabPbHardship;
import com.yizheng.partybuilding.service.inf.TabPbHardshipService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 困难党员
 *
 * @author Jiang An
 * Update by FanYanGen in 2019/04/04 16:29:20
 **/
@Api(tags = "党员-困难党员模块-范焱根")
@RestController
@RequestMapping("/difficulty/")
public class TabPbHardshipController {

    @Autowired
    private TabPbHardshipService tabPbHardshipService;

    @ApiOperation(value = "困难党员列表", notes = "困难党员列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织Id ", paramType = "query"),
            @ApiImplicitParam(name = "hardshipType", value = "困难类型 KNLX", paramType = "query")
    })
    @PostMapping("/list")
    public PageInfo<TabPbHardship> list(@ApiIgnore TabPbHardshipDto tabPbHardshipDto, Page page) {
        Map<String, Object> conditions = new HashMap<>(5);
        conditions.put("hardshipType", tabPbHardshipDto.getHardshipType());
        conditions.put("orgRange", tabPbHardshipDto.getOrgRange());
        conditions.put("username", tabPbHardshipDto.getUsername());
        conditions.put("delFlag", 0);
        Long rangeDeptId = tabPbHardshipDto.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            rangeDeptId = UserContextHolder.getOrgId();
        }
        conditions.put("rangeDeptId", rangeDeptId);
        List<TabPbHardship> list = tabPbHardshipService.selectWithConditions(conditions, page);
        PageInfo<TabPbHardship> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "删除困难党员", notes = "删除困难党员", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hardshipId", value = "困难Id", paramType = "query")
    })
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam @ApiParam(value = "数据ID", required = true) Long hardshipId) {
        int retVal = tabPbHardshipService.deleteId(hardshipId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查询单个困难党员详情", notes = "查询单个困难党员详情", httpMethod = "POST")
    @PostMapping("/findById")
    public TabPbHardship findById(@RequestParam @ApiParam(value = "数据ID", required = true) Long hardshipId) {
        return tabPbHardshipService.selectAndDeleteId(hardshipId);
    }

    @ApiOperation(value = "增加困难党员", notes = "增加困难党员", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "困难党员实体") @RequestBody TabPbHardship tabPbHardship) {
        int retVal = tabPbHardshipService.insertSelective(tabPbHardship);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改困难党员", notes = "修改困难党员", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "困难党员实体") @RequestBody TabPbHardship tabPbHardship) {
        //首先查询出困难表的单个字段
        TabPbHardship tabPbHardshi = tabPbHardshipService.selectAndDeleteId(tabPbHardship.getHardshipId());
        //判断表是否为空
        if (tabPbHardshi != null) {
            //如果不为空就进行单表的修改操作
            int i = tabPbHardshipService.updateByPrimaryKeySelective(tabPbHardship);
            //在进行判断输入的名字，跟数据库中的名字是否相等
            if (!tabPbHardshi.getUsername().equals(tabPbHardship.getUsername())) {
                //如果不等就进行name的修改
                tabPbHardshipService.updateUsernaneById(tabPbHardshi);
            }
            return ReturnUtil.buildSuccess();
        } else {
            return ReturnUtil.buildFail();
        }
    }

    @ApiOperation(value = "根据userid党员困难详情", notes = "根据userid党员困难详情", httpMethod = "POST")
    @PostMapping("/findByUserId")
    public TabPbHardship findByUserId(@RequestParam @ApiParam(value = "userId", required = true) Long userId) {
        return tabPbHardshipService.selectByUserId(userId);
    }

}
