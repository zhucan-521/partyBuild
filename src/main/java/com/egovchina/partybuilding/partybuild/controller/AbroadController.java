package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.AbroadDTO;
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
    @HasPermission(value = "party_goAbroad_add")
    @PostMapping("/overseas")
    public ReturnEntity insertGoAbroad(@RequestBody @Validated @ApiParam("出国信息") GoAbroadDTO goAbroadDTO) {
        return ReturnUtil.buildReturn(abroadService.insertGoAbroad(goAbroadDTO));
    }

    @ApiOperation(value = "添加回国信息-(在原出国信息基础上更新)", notes = "添加回国信息-(在原出国信息基础上更新)", httpMethod = "PUT")
    @HasPermission(value = "party_goAbroad_returnHome")
    @PutMapping("/repatriations")
    public ReturnEntity updateReturnAbroad(@RequestBody @Validated @ApiParam("回国信息") ReturnAbroadDTO returnAbroadDTO) {
        return ReturnUtil.buildReturn(abroadService.updateReturnAbroad(returnAbroadDTO));
    }

    @ApiOperation(value = "更新出国回国信息", notes = "更新出国回国信息", httpMethod = "PUT")
    @HasPermission(value = "party_goAbroad_edit")
    @PutMapping("/overseas-repatriations")
    public ReturnEntity updateGoAbroad(@RequestBody @Validated @ApiParam("出国回国信息") AbroadDTO abroadDTO) {
        return ReturnUtil.buildReturn(abroadService.updateAbroad(abroadDTO));
    }

    @ApiOperation(value = "删除指定的出国出境信息", notes = "删除指定的出国出境信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "abroadId", value = "出国出境ID", paramType = "path", required = true)
    @HasPermission(value = "party_goAbroad_delete")
    @DeleteMapping(value = "/overseas-repatriations/{abroadId}")
    public ReturnEntity deleteAbroad(@PathVariable Long abroadId) {
        return ReturnUtil.buildReturn(abroadService.deleteAbroad(abroadId));
    }

    @ApiOperation(value = "根据出国出境ID获取出国详情", notes = "根据出国出境ID获取出国详情", httpMethod = "GET")
    @ApiImplicitParam(name = "abroadId", value = "出国出境ID", paramType = "path", required = true)
    @HasPermission(value = "party_goAbroad_detail")
    @GetMapping(value = "/overseas/{abroadId}")
    public GoAbroadDetailsVO getGoAbroadDetails(@PathVariable Long abroadId) {
        return abroadService.findGoAbroadDetailsVOByAbroadId(abroadId);
    }

    @ApiOperation(value = "根据出国出境ID获取回国详情", notes = "根据出国出境ID获取回国详情", httpMethod = "GET")
    @ApiImplicitParam(name = "abroadId", value = "出国出境ID", paramType = "path", required = true)
    @HasPermission(value = "party_goAbroad_detail")
    @GetMapping(value = "/repatriations/{abroadId}")
    public BackAbroadDetailsVO getBackAbroadDetails(@PathVariable Long abroadId) {
        return abroadService.findBackAbroadDetailsVOByAbroadId(abroadId);
    }

    @ApiOperation(value = "获取出国出境信息列表", notes = "获取出国出境信息列表", httpMethod = "GET")
    @HasPermission(value = "party_partyFlow")
    @GetMapping("/overseas-repatriations")
    public PageInfo<AbroadVO> getOverseaList(@Validated AbroadQueryBean abroadQueryBean, Page page) {
        return abroadService.findAbroadVOWithConditions(abroadQueryBean, page);
    }

}
