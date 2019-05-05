package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.service.PanelStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 面板统计控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "首页-面板统计模块 张帆")
@RestController
@RequestMapping("/statistics")
public class PanelStatisticsController {

    @Autowired
    private PanelStatisticsService panelStatisticsService;

    @ApiOperation(value = "活动照片墙", httpMethod = "GET")
    @GetMapping("activityPhotoWall")
    public List<HashMap<String, Object>> activityPhotoWall(@RequestParam(required = false, defaultValue = "10") @ApiParam(value = "数量限制") Integer limit) {
        return panelStatisticsService.selectActivityPhotos(UserContextHolder.getOrgId(), limit);
    }
}
