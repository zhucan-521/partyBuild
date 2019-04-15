package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.entity.SysTaskConfigurer;
import com.egovchina.partybuilding.partybuild.service.SysTaskConfigurerService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统任务配置控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "系统任务配置 蒋安")
@RestController
@RequestMapping("/taskConfigurer")
public class SysTaskConfigurerController {

    @Autowired
    private SysTaskConfigurerService sysTaskConfigurerService;

    @ApiOperation(value = "系统任务配置列表", notes = "系统任务配置列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年度 yyyy", paramType = "query"),
            @ApiImplicitParam(name = "cycle", value = "周期 dict RWZQ", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务 dict", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<SysTaskConfigurer> list(String orgRange, Long rangeDeptId, Integer year, Long cycle,
                                            Long taskId, Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("orgRange", orgRange);
        if (rangeDeptId == null || rangeDeptId == 0L) {
            rangeDeptId = UserContextHolder.getOrgId();
        }
        conditions.put("rangeDeptId", rangeDeptId);
        conditions.put("year", year);
        conditions.put("cycle", cycle);
        conditions.put("taskId", taskId);
        conditions.put("delFlag", "0");
        List<SysTaskConfigurer> list = sysTaskConfigurerService.selectWithConditions(conditions, page);
        PageInfo<SysTaskConfigurer> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "新增任务配置", notes = "新增任务配置", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "配置实体") @RequestBody @Validated SysTaskConfigurer configurer) {
        int retVal = sysTaskConfigurerService.insertAheadDelete(configurer);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改任务配置", notes = "修改任务配置", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam("配置实体") @RequestBody @Validated SysTaskConfigurer configurer) {
        int retVal = sysTaskConfigurerService.updateConfigurer(configurer);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "任务配置删除", notes = "任务配置删除", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam("id") @ApiParam(name = "id", value = "配置ID", required = true) Long id) {
        //逻辑删除
        int retVal = sysTaskConfigurerService.logicDeleteById(id);
        return ReturnUtil.buildReturn(retVal);
    }
}
