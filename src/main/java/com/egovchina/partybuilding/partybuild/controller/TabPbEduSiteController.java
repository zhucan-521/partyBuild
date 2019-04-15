package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduSiteDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSite;
import com.egovchina.partybuilding.partybuild.service.TabPbEduSiteService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远教站点控制器
 *
 * @Author Zhang Fan
 **/
@Deprecated
@Api(tags = "党员教育-远教站点模块-张帆")
@RestController
@RequestMapping("/eduSite")
public class TabPbEduSiteController {

    @Autowired
    private TabPbEduSiteService tabPbEduSiteService;

    @Deprecated
    @ApiOperation(value = "站点列表", notes = "站点列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "站点名称", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "站点类型 dict ZDLX", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "站点地址", paramType = "query"),
            @ApiImplicitParam(name = "region", value = "区域 dict QY", paramType = "query"),
            @ApiImplicitParam(name = "netPlay", value = "上网方式 dict SWFS", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbEduSite> list(String name, Long type, String region,
                                       String address, Long netPlay, Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("siteName", name);
        conditions.put("siteType", type);
        conditions.put("siteAddress", address);
        conditions.put("region", region);
        conditions.put("siteNetPlay", netPlay);
        conditions.put("delFlag", "0");
        List<TabPbEduSite> list = tabPbEduSiteService.selectWithConditions(conditions, page);
        PageInfo<TabPbEduSite> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Deprecated
    @ApiOperation(value = "新增站点", notes = "新增站点", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "站点信息") @RequestBody @Validated TabPbEduSiteDto tabPbEduSite) {
        int retVal = tabPbEduSiteService.insertWithAbout(tabPbEduSite);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "修改站点", notes = "修改站点", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "站点信息") @RequestBody @Validated TabPbEduSiteDto tabPbEduSite) {
        int retVal = tabPbEduSiteService.updateWithAbout(tabPbEduSite);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "查看站点", notes = "查看站点", httpMethod = "GET")
    @GetMapping("/details")
    public TabPbEduSiteDto details(@RequestParam() @ApiParam(name = "id", value = "站点ID", required = true) Long id) {
        return tabPbEduSiteService.selectWithAboutInfoById(id);
    }

    @Deprecated
    @ApiOperation(value = "删除站点", notes = "删除站点", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam() @ApiParam(name = "id", value = "站点ID", required = true) Long id) {
        int retVal = tabPbEduSiteService.logicDeleteById(id);
        return ReturnUtil.buildReturn(retVal);
    }

}
