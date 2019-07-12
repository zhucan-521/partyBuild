package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.OrgClassifyDTO;
import com.egovchina.partybuilding.partybuild.entity.ClassifyQueryBean;
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
@Api(tags = "党组织-分类定等模块v1-范焱根")
@RequestMapping("/v1/classifies")
@RestController
public class OrgClassifyController {

    @Autowired
    private OrgClassifyService orgClassifyService;

    @ApiOperation(value = "新增分类定等", notes = "新增分类定等", httpMethod = "POST")
    @HasPermission(value = "party_classificationSetEtc_add")
    @PostMapping
    public ReturnEntity insertOrgClassify(@RequestBody @Validated @ApiParam("分类定等信息") OrgClassifyDTO orgClassifyDTO) {
        return ReturnUtil.buildReturn(orgClassifyService.insertOrgClassify(orgClassifyDTO));
    }

    @ApiOperation(value = "更新定等计划", notes = "更新定等计划", httpMethod = "PUT")
    @HasPermission(value = "party_classificationSetEtc_edit")
    @PutMapping
    public ReturnEntity updateOrgClassify(@RequestBody @Validated @ApiParam("分类定等信息") OrgClassifyDTO orgClassifyDTO) {
        return ReturnUtil.buildReturn(orgClassifyService.updateOrgClassify(orgClassifyDTO));
    }

    @ApiOperation(value = "删除定等数据", notes = "定等数据删除", httpMethod = "DELETE")
    @ApiImplicitParam(name = "orgClassifyId", value = "定等数据ID", paramType = "path", required = true)
    @HasPermission(value = "party_classificationSetEtc_del")
    @DeleteMapping("/{orgClassifyId}")
    public ReturnEntity deleteOrgClassify(@PathVariable Long orgClassifyId) {
        return ReturnUtil.buildReturn(orgClassifyService.deleteOrgClassify(orgClassifyId));
    }

    @ApiOperation(value = "定等数据详情", notes = "定等数据详情", httpMethod = "GET")
    @ApiImplicitParam(name = "orgClassifyId", value = "定等数据ID", paramType = "path", required = true)
    @HasPermission(value = "party_classificationSetEtc_detail")
    @GetMapping("/{orgClassifyId}")
    public OrgClassifyVO getClassifyDetails(@PathVariable Long orgClassifyId) {
        return orgClassifyService.findOrgClassifyVOByOrgClassifyId(orgClassifyId);
    }

    @ApiOperation(value = "分类定等列表", notes = "分类定等列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<OrgClassifyVO> getClassifyList(@Validated ClassifyQueryBean classifyQueryBean, Page page) {
        return orgClassifyService.findOrgClassifyVOWithConditions(classifyQueryBean, page);
    }

}
