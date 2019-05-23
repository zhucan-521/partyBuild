package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesPlaceDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesPlaceQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyMassesPlaceService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesPlaceVO;
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
@Api(tags = "党组织-党群场地-v1-吴云杰")
@RestController
@RequestMapping("/v1/party-masses-places")
public class PartyMassesPlaceController {

    @Autowired
    private PartyMassesPlaceService partyMassesPlaceService;

    @ApiOperation(value = "添加")
    @PostMapping
    public ReturnEntity save(@ApiParam(name = "党群场地DTO") @RequestBody @Validated PartyMassesPlaceDTO partyMassesPlaceDTO) {
        return ReturnUtil.buildReturn(partyMassesPlaceService.save(partyMassesPlaceDTO));
    }

    @ApiOperation(value = "根据id更新")
    @PutMapping
    public ReturnEntity update(@ApiParam(name = "党群场地DTO") @RequestBody @Validated PartyMassesPlaceDTO partyMassesPlaceDTO) {
        return ReturnUtil.buildReturn(partyMassesPlaceService.updateById(partyMassesPlaceDTO));
    }

    @ApiOperation(value = "根据id删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要删除的id", dataType = "Long", required = true)
    @DeleteMapping("/{id}")
    public ReturnEntity deleteById(@PathVariable("id") Long id) {
        return ReturnUtil.buildReturn(partyMassesPlaceService.deleteById(id));
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public PartyMassesPlaceVO findByIdPartyMassesPlace(@PathVariable("id") Long id) {
        return partyMassesPlaceService.selectById(id);
    }

    @ApiOperation(value = "查询列表")
    @GetMapping
    public PageInfo<PartyMassesPlaceVO> selectList(@ApiParam(name = "党群场地查询实体") @Validated PartyMassesPlaceQueryBean partyMassesPlaceQueryBean, Page page) {
        return new PageInfo<>(partyMassesPlaceService.selectList(partyMassesPlaceQueryBean, page));
    }

}
