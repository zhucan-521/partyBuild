package com.egovchina.partybuilding.partybuild.controller;

import com.alibaba.fastjson.JSON;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.GoAbroadDTO;
import com.egovchina.partybuilding.partybuild.dto.ReturnAbroadDTO;
import com.egovchina.partybuilding.partybuild.entity.AbroadQueryBean;
import com.egovchina.partybuilding.partybuild.service.AbroadService;
import com.egovchina.partybuilding.partybuild.vo.AbroadVO;
import com.egovchina.partybuilding.partybuild.vo.BackAbroadDetailsVO;
import com.egovchina.partybuilding.partybuild.vo.GoAbroadDetailsVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 出国出境模块-v1
 * Created by FanYanGen on 2019/4/20 11:33
 */
@Api(tags = "党员-出国出境管理v1-范焱根")
@RequestMapping("/v1")
@RestController
public class AbroadController {

    @Autowired
    private AbroadService abroadService;

    @ApiOperation(value = "添加出国信息", notes = "添加出国信息", httpMethod = "POST")
    @PostMapping("/overseas")
    public ReturnEntity insertGoAbroad(@RequestBody @Validated @ApiParam("出国信息") GoAbroadDTO goAbroadDTO) {
        return ReturnUtil.buildReturn(abroadService.insertGoAbroad(goAbroadDTO));
    }

    @ApiOperation(value = "更新出国信息", notes = "更新出国信息", httpMethod = "PUT")
    @PutMapping("/overseas")
    public ReturnEntity updateGoAbroad(@RequestBody @Validated @ApiParam("出国信息") GoAbroadDTO goAbroadDTO) {
        return ReturnUtil.buildReturn(abroadService.updateGoAbroad(goAbroadDTO));
    }

    @ApiOperation(value = "更新（添加）回国信息 - 用作出国信息添加和更新", notes = "更新（添加）回国信息 - 用作出国信息添加和更新", httpMethod = "PUT")
    @PutMapping("/repatriations")
    public ReturnEntity updateReturnAbroad(@RequestBody @Validated @ApiParam("回国信息") ReturnAbroadDTO returnAbroadDTO) {
        return ReturnUtil.buildReturn(abroadService.updateReturnAbroad(returnAbroadDTO));
    }

    @ApiOperation(value = "删除指定的出国出境信息", notes = "删除指定的出国出境信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "abroadId", value = "出国出境ID", paramType = "path", required = true)
    @DeleteMapping(value = "/overseas-repatriations/{abroadId}")
    public ReturnEntity deleteAbroad(@PathVariable Long abroadId) {
        return ReturnUtil.buildReturn(abroadService.deleteAbroad(abroadId));
    }

    @ApiOperation(value = "根据出国出境ID获取出国详情", notes = "根据出国出境ID获取出国详情", httpMethod = "GET")
    @ApiImplicitParam(name = "abroadId", value = "出国出境ID", paramType = "path", required = true)
    @GetMapping(value = "/overseas/{abroadId}")
    public GoAbroadDetailsVO getGoAbroadDetails(@PathVariable Long abroadId) {
        return abroadService.findGoAbroadDetailsVOByAbroadId(abroadId);
    }

    @ApiOperation(value = "根据出国出境ID获取回国详情", notes = "根据出国出境ID获取回国详情", httpMethod = "GET")
    @ApiImplicitParam(name = "abroadId", value = "出国出境ID", paramType = "path", required = true)
    @GetMapping(value = "/repatriations/{abroadId}")
    public BackAbroadDetailsVO getBackAbroadDetails(@PathVariable Long abroadId) {
        return abroadService.findBackAbroadDetailsVOByAbroadId(abroadId);
    }

    @ApiOperation(value = "获取出国出境信息列表", notes = "获取出国出境信息列表", httpMethod = "GET")
    @GetMapping("/overseas-repatriations")
    public PageInfo<AbroadVO> getOverseaList(@Validated AbroadQueryBean abroadQueryBean, Page page) {
        return abroadService.findAbroadVOWithConditions(abroadQueryBean, page);
    }

}
