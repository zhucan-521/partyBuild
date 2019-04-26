package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.OrgClassifyDTO;
import com.egovchina.partybuilding.partybuild.service.OrgClassifyService;
import com.egovchina.partybuilding.partybuild.vo.OrgClassifyVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 分类定等模块-v1
 * Created by FanYanGen on 2019/4/24 21:47
 */
@Api(tags = "党员-分类定等模块v1-范焱根")
@RequestMapping("/v1/classifies")
@RestController
public class OrgClassifyController {

    @Autowired
    private OrgClassifyService orgClassifyService;

    @ApiOperation(value = "新增分类定等", notes = "新增分类定等", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insert(@ApiParam("分类定等信息") @RequestBody @Validated OrgClassifyDTO orgClassifyDTO) {
        return ReturnUtil.buildReturn(orgClassifyService.insertOrgClassify(orgClassifyDTO));
    }

    @ApiOperation(value = "更新定等计划", notes = "更新定等计划", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity update(@ApiParam("分类定等信息") @RequestBody @Validated OrgClassifyDTO orgClassifyDTO) {
        return ReturnUtil.buildReturn(orgClassifyService.updateOrgClassify(orgClassifyDTO));
    }

    @ApiOperation(value = "删除定等数据", notes = "定等数据删除", httpMethod = "DELETE")
    @ApiImplicitParam(name = "orgClassifyId", value = "定等数据ID", paramType = "path", required = true)
    @DeleteMapping("/{orgClassifyId}")
    public ReturnEntity delete(@PathVariable Long orgClassifyId) {
        return ReturnUtil.buildReturn(orgClassifyService.deleteOrgClassify(orgClassifyId));
    }

    @ApiOperation(value = "定等数据详情", notes = "定等数据详情", httpMethod = "GET")
    @ApiImplicitParam(name = "orgClassifyId", value = "定等数据ID", paramType = "path", required = true)
    @GetMapping("/{orgClassifyId}")
    public OrgClassifyVO getClassifyDetails(@PathVariable Long orgClassifyId) {
        return orgClassifyService.findOrgClassifyVOByOrgClassifyId(orgClassifyId);
    }

    @ApiOperation(value = "分类定等列表", notes = "分类定等列表", httpMethod = "GET")
    @ApiImplicitParam(name = "orgLevel", value = "定等级别 dict FLDD", paramType = "query")
    @GetMapping
    public PageInfo<OrgClassifyVO> getClassifyList(Page page, OrgRange orgRange, String orgLevel) {
        return orgClassifyService.findOrgClassifyVOWithConditions(page, orgRange, orgLevel);
    }

}