package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.UnitInfoDTO;
import com.egovchina.partybuilding.partybuild.service.UnitInfoService;
import com.egovchina.partybuilding.partybuild.vo.UnitInfoVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 * 单位管理控制器
 *
 * @author wuyunjie
 * Date 2018-12-11 18:03
 */
@Api(tags = "党组织-单位管理-v1-吴云杰")
@RestController
@RequestMapping("/v1")
public class UnitInfoController {

    @Autowired
    private UnitInfoService unitInfoService;

    @ApiOperation(value = "根据单位名称查询单位信息", notes = "根据单位名称查询单位信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "unitName", value = "单位名称", paramType = "query")
    )
    @GetMapping("/units")
    public PageInfo<UnitInfoVO> getUnitInfo(String unitName, Page page) {
        List<UnitInfoVO> list = unitInfoService.selectUnitInfoByUnitName(unitName, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "根据单位id查询单位信息", notes = "根据单位id查询单位信息")
    @ApiImplicitParam(value = "单位id", name = "unitId", paramType = "path", required = true)
    @GetMapping("/units/{unitId}")
    public UnitInfoVO getUnitInfoById(@PathVariable Long unitId) {
        return unitInfoService.selectUnitInfoByUnitId(unitId);
    }

    @ApiOperation(value = "根据组织id查询单位信息", notes = "根据组织id查询单位信息")
    @ApiImplicitParam(value = "组织id", name = "orgId", paramType = "path", required = true)
    @GetMapping("/organizations/{orgId}/units")
    public List<UnitInfoVO> getUnitListByOrgId(@PathVariable Long orgId) {
        return unitInfoService.selectUnitInfoByOrgId(orgId);
    }

    @ApiOperation(value = "添加一个单位信息", notes = "添加一个单位信息")
    @PostMapping("/units")
    public ReturnEntity addUnitInfo(@ApiParam("单位信息") @RequestBody @Validated UnitInfoDTO unitInfoDTO) {
        return ReturnUtil.buildReturn(unitInfoService.insertUnitInfo(unitInfoDTO));
    }

    @ApiOperation(value = "删除单位", notes = "删除单位")
    @ApiImplicitParam(value = "单位Id", name = "unitId", paramType = "path", required = true)
    @DeleteMapping("/units/{unitId}")
    public ReturnEntity deleteUnitInfo(@PathVariable Long unitId) {
        return ReturnUtil.buildReturn(unitInfoService.deleteUnitInfo(unitId));
    }

    @ApiOperation(value = "修改单位信息", notes = "修改单位信息")
    @PutMapping("/units")
    public ReturnEntity updateUnitInfo(@ApiParam("单位信息") @RequestBody @Validated UnitInfoDTO unitInfoDTO) {
        return ReturnUtil.buildReturn(unitInfoService.updateUnitInfo(unitInfoDTO));
    }

    @ApiOperation(value = "修改多个单位信息", notes = "修改多个单位信息")
    @PutMapping("/units/batch")
    public ReturnEntity updateBatchUnitInfo(@ApiParam("单位集合信息") @RequestBody @Validated List<UnitInfoDTO> unitInfoDTOList) {
        return ReturnUtil.buildReturn(unitInfoService.updateBatchUnitInfo(unitInfoDTOList));
    }

}
