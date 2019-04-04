package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduIntelligentStudyDto;
import com.yizheng.partybuilding.dto.TabPbEduPositionDto;
import com.yizheng.partybuilding.entity.TabPbEduPosition;
import com.yizheng.partybuilding.service.inf.TabPbEduPositionService;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教育基地控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "党员教育-教育基地模块-张帆")
@RestController
@RequestMapping("/eduPosition")
public class TabPbEduPositionController {

    @Autowired
    private TabPbEduPositionService tabPbEduPositionService;

    @ApiOperation(value = "基地列表", notes = "基地列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "基地名称", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "基地类型 dict JDLX", paramType = "query"),
            @ApiImplicitParam(name = "property", value = "基地属性 dict JDSX", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "基地地址", paramType = "query"),
            @ApiImplicitParam(name = "region", value = "区域 dict QY", paramType = "query"),
            @ApiImplicitParam(name = "quantity", value = "接待容量 dict JDRL", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbEduPosition> list(String name, Long type, Long property, String region,
                                           String quantity, String address, Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("name", name);
        conditions.put("type", type);
        conditions.put("property", property);
        conditions.put("address", address);
        conditions.put("region", region);
        conditions.put("quantity", quantity);
        conditions.put("delFlag", "0");
        List<TabPbEduPosition> list = tabPbEduPositionService.selectWithConditions(conditions, page);
        PageInfo<TabPbEduPosition> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "新增基地", notes = "新增基地", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "基地信息") @RequestBody @Validated TabPbEduPositionDto tabPbEduPosition) {
        int retVal = tabPbEduPositionService.insertWithAbout(tabPbEduPosition);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改基地", notes = "修改基地", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "基地信息") @RequestBody @Validated TabPbEduPositionDto tabPbEduPosition) {
        int retVal = tabPbEduPositionService.updateWithAbout(tabPbEduPosition);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查看基地", notes = "查看基地", httpMethod = "GET")
    @GetMapping("/details")
    public TabPbEduPositionDto details(@RequestParam("id") @ApiParam(name = "id", value = "基地ID", required = true) Long id) {
        return tabPbEduPositionService.selectWithAboutInfoById(id);
    }

    @ApiOperation(value = "智能选学基地详情", notes = "智能选学基地详情", httpMethod = "GET")
    @GetMapping("/zlxxDetails")
    public TabPbEduIntelligentStudyDto zlxxDetails(@RequestParam("id") @ApiParam(name = "id", value = "基地ID", required = true) Long id) {
        return tabPbEduPositionService.selectZLLXWithAboutInfoById(id);
    }

    @ApiOperation(value = "删除基地", notes = "删除基地", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam("id") @ApiParam(name = "id", value = "基地ID", required = true) Long id) {
        //逻辑删除
        int retVal = tabPbEduPositionService.logicDeleteById(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "智能选学列表", notes = "智能选学列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "region", value = "区域 dict QY", paramType = "query"),
            @ApiImplicitParam(name = "jdType", value = "基地类型 dict ZDLX", paramType = "query"),
            @ApiImplicitParam(name = "dateRange", value = "时间范围 dict SJFW 请使用value字段", paramType = "query"),
            @ApiImplicitParam(name = "quantity", value = "接待容量 dict JDRL", paramType = "query"),
            @ApiImplicitParam(name = "key", value = "关键词 老师姓名、基地名称", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数限制", paramType = "query")
    })
    @GetMapping("/intelligentStudy")
    public PageInfo<TabPbEduIntelligentStudyDto> intelligentStudy(String region, String jdType, String dateRange,
                                                                  String quantity, String key, Page page) {
        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put("region", region);
        conditions.put("jdType", jdType);
        conditions.put("dateRange", dateRange);
        conditions.put("quantity", quantity);
        conditions.put("key", key);
        List<TabPbEduIntelligentStudyDto> list = tabPbEduPositionService.selectZLXXWithConditions(conditions, page);
        PageInfo<TabPbEduIntelligentStudyDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
