package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduLiveDto;
import com.yizheng.partybuilding.entity.TabPbEduLive;
import com.yizheng.partybuilding.service.inf.TabPbEduLiveService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 * 直播管理控制器
 *
 * @author wuyunjie
 * Date 2018-12-10 11:42
 */
@Api(tags = "党员教育-直播管理模块")
@RestController
@RequestMapping("/live")
public class TabPbEduLiveController {

    @Autowired
    private TabPbEduLiveService tabPbEduLiveService;

    /**
     * 创建直播
     *
     * @param tabPbEduLive
     * @return
     */
    @ApiOperation(value = "创建直播", notes = "创建直播", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity addLive(@RequestBody TabPbEduLive tabPbEduLive) {
        int retVal = tabPbEduLiveService.insertSelective(tabPbEduLive);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "根据直播id查询直播信息", notes = "根据直播id查询直播信息", httpMethod = "GET")
    @GetMapping("/{liveId}")
    public TabPbEduLiveDto getLive(@ApiParam(value = "直播ID", required = true) @PathVariable Long liveId) {
        return tabPbEduLiveService.getLiveByLiveId(liveId);
    }

    /**
     * 删除直播
     *
     * @param liveId
     * @return
     */
    @ApiOperation(value = "删除直播", notes = "删除直播", httpMethod = "DELETE")
    @DeleteMapping("/delete/{liveId}")
    public ReturnEntity deleteLive(@ApiParam(value = "直播ID", required = true) @PathVariable Long liveId) {
        int retVal = tabPbEduLiveService.deleteByPrimaryKey(liveId);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 编辑直播
     *
     * @param tabPbEduLiveDto
     * @return
     */
    @ApiOperation(value = "编辑直播", notes = "编辑直播", httpMethod = "PUT")
    @PutMapping("/update")
    public ReturnEntity updateLive(@RequestBody TabPbEduLiveDto tabPbEduLiveDto) {
        int retVal = tabPbEduLiveService.updateLive(tabPbEduLiveDto);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 查看直播列表
     *
     * @param name
     * @param page
     * @return
     */
    @ApiOperation(value = "查看直播列表", notes = "查看直播列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "视频名称", paramType = "query"),
            @ApiImplicitParam(name = "plan", value = "排课计划", paramType = "query"),
            @ApiImplicitParam(name = "region", value = "区域", paramType = "query")
    })
    @GetMapping("/showLive")
    public PageInfo<TabPbEduLiveDto> showLive(String name, Long plan, Long region, Page page) {
        TabPbEduLive tabPbEduLive = new TabPbEduLive();
        tabPbEduLive.setName(name);
        tabPbEduLive.setPlan(plan);
        tabPbEduLive.setRegion(region);
        List<TabPbEduLiveDto> list = tabPbEduLiveService.selectLive(tabPbEduLive, page);
        PageInfo<TabPbEduLiveDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
