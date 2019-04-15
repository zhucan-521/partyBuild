package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.egovchina.partybuilding.partybuild.service.ITabPbUnitInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 * 单位管理控制器
 *
 * @author wuyunjie
 * Date 2018-12-11 18:03
 */
@Api(tags = "党组织-单位管理-吴云杰")
@RestController
@RequestMapping("/unitInfo")
public class TabPbUnitInfoController {

    @Autowired
    private ITabPbUnitInfoService iTabPbUnitInfoService;

    @ApiOperation(value = "根据单位名称查询单位信息", notes = "根据单位名称查询单位信息", httpMethod = "GET")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "unitName", value = "单位名称", paramType = "query")
    )
    @GetMapping("/showUnitInfo")
    public PageInfo<TabPbUnitInfo> getUnitInfo(String unitName, Page page) {
        List<TabPbUnitInfo> list = iTabPbUnitInfoService.selectByUnitName(unitName, page);
        PageInfo<TabPbUnitInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "根据单位id查询单位信息", notes = "根据单位id查询单位信息", httpMethod = "GET")
    @GetMapping("/getUnit/{unitId}")
    public TabPbUnitInfo getUnitInfoById(@ApiParam(value = "unitId", required = true) @PathVariable Long unitId) {
        return iTabPbUnitInfoService.selectByUnitId(unitId);
    }

    @ApiOperation(value = "根据组织id查询单位信息", notes = "根据组织id查询单位信息", httpMethod = "GET")
    @GetMapping("/unitByOrgId/{orgId}")
    public List<TabPbUnitInfo> getUnitListByOrgId(@ApiParam(value = "orgId", required = true) @PathVariable Long orgId) {
        return iTabPbUnitInfoService.selectByOrgId(orgId);
    }

    @ApiOperation(value = "添加一个单位信息", notes = "添加一个单位信息", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity addUnitInfo(@RequestBody TabPbUnitInfo tabPbUnitInfo) {
        int add = iTabPbUnitInfoService.insertUnitInfo(tabPbUnitInfo);
        return ReturnUtil.buildReturn(add);
    }

    @ApiOperation(value = "删除单位", notes = "删除单位", httpMethod = "DELETE")
    @DeleteMapping("/delUnit/{unitId}")
    public ReturnEntity deleteUnitInfo(@ApiParam(value = "unitId", required = true) @PathVariable Long unitId) {
        int delete = iTabPbUnitInfoService.deleteUnitInfo(unitId);
        return ReturnUtil.buildReturn(delete);
    }

    @ApiOperation(value = "修改单位信息", notes = "修改单位信息", httpMethod = "PUT")
    @PutMapping("/update")
    public ReturnEntity updateUnitInfo(@RequestBody TabPbUnitInfo tabPbUnitInfo) {
        int retVal = iTabPbUnitInfoService.updateUnitInfo(tabPbUnitInfo);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改多个单位信息", notes = "修改多个单位信息", httpMethod = "PUT")
    @PutMapping("/batchUpdate")
    public ReturnEntity batchUpdateUnitInfo(@RequestBody List<TabPbUnitInfo> tabPbUnitInfoList) {
        int retVal = iTabPbUnitInfoService.batchUpdateUnitInfo(tabPbUnitInfoList);
        return ReturnUtil.buildReturn(retVal);
    }

}
