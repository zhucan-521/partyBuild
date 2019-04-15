package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbAbroad;
import com.egovchina.partybuilding.partybuild.service.ITabPbAbroadService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/11/26
 * Update by FanYanGen in 2019/04/04 16:42:19
 */
@Api(tags = "党员-出国出境管理-范焱根")
@RestController
@RequestMapping(value = "/abroad")
public class TabPbAbroadController {

    @Autowired
    private ITabPbAbroadService iTabPbAbroadService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "添加出国出境信息", notes = "添加指定党员的出国出境信息", httpMethod = "POST")
    public int add(@RequestBody @Validated TabPbAbroad abroad) {
        return iTabPbAbroadService.add(abroad);
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取出国出境信息列表", notes = "可指定条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "党员id", name = "abroadId", dataType = "Long"),
            @ApiImplicitParam(value = "组织id", name = "orgId", dataType = "Long"),
            @ApiImplicitParam(value = "范围", name = "orgRange", dataType = "Long"),
            @ApiImplicitParam(value = "用户id", name = "userId", dataType = "Long"),
            @ApiImplicitParam(value = "用户名", name = "userName", dataType = "String"),
            @ApiImplicitParam(value = "身份证号", name = "idCardNo", dataType = "Long"),
    })
    public PageInfo<TabPbAbroad> list(Long abroadId, Long orgId, Long orgRange, Long userId, String userName, String idCardNo, Page page) {
        return this.iTabPbAbroadService.selectList(abroadId, orgId, orgRange, userId, userName, idCardNo, page);
    }

    @DeleteMapping(value = "/delete/{abroadId}")
    @ApiOperation(value = "删除指定的出国出境信息", notes = "需要指定主键")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "党员id", name = "abroadId", dataType = "Long", required = true, paramType = "path")
    })
    public int delete(@PathVariable Long abroadId) {
        return iTabPbAbroadService.delete(abroadId);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "更新出国出境信息列表", notes = "需指定主键, 及更新信息")
    public int update(@RequestBody @Validated TabPbAbroad abroad) {
        return iTabPbAbroadService.update(abroad);
    }

}
