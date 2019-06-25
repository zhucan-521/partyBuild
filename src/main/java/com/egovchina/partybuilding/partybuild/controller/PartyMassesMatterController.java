package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesMatterDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesMatterQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyMassesMatterService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesMatterVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description:接口类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@Api(tags = "党组织-党群服务事项-v1-吴云杰")
@RestController
@RequestMapping("/v1/party-masses-matters")
public class PartyMassesMatterController {

    @Autowired
    private PartyMassesMatterService partyMassesMatterService;

    @ApiOperation(value = "添加")
    @HasPermission("party_masses_edit")
    @PostMapping
    public ReturnEntity save(@ApiParam(name = "党群服务事项DTO") @RequestBody @Validated PartyMassesMatterDTO partyMassesMatterDTO) {
        return ReturnUtil.buildReturn(partyMassesMatterService.save(partyMassesMatterDTO));
    }

    @ApiOperation(value = "根据id更新")
    @HasPermission("party_masses_edit")
    @PutMapping
    public ReturnEntity update(@ApiParam(name = "党群服务事项DTO") @RequestBody @Validated PartyMassesMatterDTO partyMassesMatterDTO) {
        return ReturnUtil.buildReturn(partyMassesMatterService.updateById(partyMassesMatterDTO));
    }

    @ApiOperation(value = "根据id删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要删除的id", dataType = "Long", required = true)
    @HasPermission("party_masses_edit")
    @DeleteMapping("/{id}")
    public ReturnEntity deleteById(@PathVariable("id") Long id) {
        return ReturnUtil.buildReturn(partyMassesMatterService.deleteById(id));
    }

    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "Long", required = true)
    @HasPermission({"party_serviceCenterManagement","party_masses_look"})
    @GetMapping("/{id}")
    public PartyMassesMatterVO findByIdPartyMassesMatter(@PathVariable("id") Long id) {
        return partyMassesMatterService.selectById(id);
    }

    @ApiOperation(value = "查询列表")
    @HasPermission({"party_serviceCenterManagement","party_masses_look"})
    @GetMapping
    public PageInfo<PartyMassesMatterVO> selectList(@ApiParam(name = "党群服务事项查询实体") @Validated PartyMassesMatterQueryBean partyMassesMatterQueryBean, Page page){
        return new PageInfo<>(partyMassesMatterService.selectList(partyMassesMatterQueryBean, page));
    }
}
