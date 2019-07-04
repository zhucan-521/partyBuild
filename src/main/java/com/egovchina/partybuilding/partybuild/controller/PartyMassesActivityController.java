package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesActivityDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesActivityQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyMassesActivityService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesActivityVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description:接口类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@Api(tags = "党组织-党群活动-v1-吴云杰")
@RestController
@RequestMapping("/v1/party-masses-activity")
public class PartyMassesActivityController {

    @Autowired
    private PartyMassesActivityService partyMassesActivityService;

    @ApiOperation(value = "添加党群活动")
    @PostMapping
    public ReturnEntity save(@ApiParam(name = "党群活动DTO") @RequestBody @Validated PartyMassesActivityDTO partyMassesActivityDTO) {
        return ReturnUtil.buildReturn(partyMassesActivityService.save(partyMassesActivityDTO));
    }

    @ApiOperation(value = "根据id更新党群活动")
    @PutMapping
    public ReturnEntity update(@ApiParam(name = "党群活动DTO") @RequestBody @Validated PartyMassesActivityDTO partyMassesActivityDTO) {
        return ReturnUtil.buildReturn(partyMassesActivityService.updateById(partyMassesActivityDTO));
    }

    @ApiOperation(value = "根据id删除党群活动")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要删除的id", dataType = "Long", required = true)
    @DeleteMapping("/{id}")
    public ReturnEntity deleteById(@PathVariable("id") Long id) {
        return ReturnUtil.buildReturn(partyMassesActivityService.deleteById(id));
    }

    @ApiOperation(value = "根据id查询党群活动", notes = "根据id查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public PartyMassesActivityVO findByIdPartyMassesActivity(@PathVariable("id") Long id) {
        return partyMassesActivityService.selectById(id);
    }

    @ApiOperation(value = "查询党群活动列表")
    @GetMapping
    public PageInfo<PartyMassesActivityVO> selectList(@ApiParam(name = "党群活动查询实体") @Validated PartyMassesActivityQueryBean partyMassesActivityQueryBean, Page page) {
        return new PageInfo<>(partyMassesActivityService.selectList(partyMassesActivityQueryBean, page));
    }
}
