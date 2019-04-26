package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.AbroadDTO;
import com.egovchina.partybuilding.partybuild.entity.AbroadQueryBean;
import com.egovchina.partybuilding.partybuild.service.AbroadService;
import com.egovchina.partybuilding.partybuild.vo.AbroadDetailsVO;
import com.egovchina.partybuilding.partybuild.vo.AbroadVO;
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
@RequestMapping("/v1/overseas")
@RestController
public class AbroadController {

    @Autowired
    private AbroadService abroadService;

    @ApiOperation(value = "添加出国出境信息", notes = "添加出国出境信息", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insert(@ApiParam("出国出境信息") @RequestBody @Validated AbroadDTO abroadDTO) {
        return ReturnUtil.buildReturn(abroadService.insert(abroadDTO));
    }

    @ApiOperation(value = "更新出国出境信息列表", notes = "更新出国出境信息列表", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity update(@ApiParam("出国出境信息") @RequestBody @Validated AbroadDTO abroadDTO) {
        return ReturnUtil.buildReturn(abroadService.update(abroadDTO));
    }

    @ApiOperation(value = "删除指定的出国出境信息", notes = "删除指定的出国出境信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "abroadId", value = "出国出境ID", paramType = "path", required = true)
    @DeleteMapping(value = "/{abroadId}")
    public ReturnEntity delete(@PathVariable Long abroadId) {
        return ReturnUtil.buildReturn(abroadService.delete(abroadId));
    }

    @ApiOperation(value = "根据出国出境ID获取详情", notes = "根据出国出境ID获取详情", httpMethod = "GET")
    @ApiImplicitParam(name = "abroadId", value = "出国出境ID", paramType = "path", required = true)
    @GetMapping(value = "/{abroadId}")
    public AbroadDetailsVO getAbroadDetails(@PathVariable Long abroadId) {
        return abroadService.findAbroadVOByAbroadId(abroadId);
    }

    @ApiOperation(value = "获取出国出境信息列表", notes = "获取出国出境信息列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<AbroadVO> getOverseaList(AbroadQueryBean abroadQueryBean, Page page) {
        return abroadService.findAbroadVOWithConditions(abroadQueryBean, page);
    }

}
