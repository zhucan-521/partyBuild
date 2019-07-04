package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesConfigurationDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesConfigurationQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyMassesConfigurationService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesConfigurationVO;
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
@Api(tags = "党组织-党群配置-v1-吴云杰")
@RestController
@RequestMapping("/v1/party-masses-configurations")
public class PartyMassesConfigurationController {

    @Autowired
    private PartyMassesConfigurationService partyMassesConfigurationService;

    @ApiOperation(value = "添加党群配置")
    @HasPermission("party_masses_edit")
    @PostMapping
    public ReturnEntity save(@ApiParam(name = "党群配置DTO") @RequestBody @Validated PartyMassesConfigurationDTO partyMassesConfigurationDTO) {
        return ReturnUtil.buildReturn(partyMassesConfigurationService.save(partyMassesConfigurationDTO));
    }

    @ApiOperation(value = "根据id更新党群配置")
    @HasPermission("party_masses_edit")
    @PutMapping
    public ReturnEntity update(@ApiParam(name = "党群配置DTO") @RequestBody @Validated PartyMassesConfigurationDTO partyMassesConfigurationDTO) {
        return ReturnUtil.buildReturn(partyMassesConfigurationService.updateById(partyMassesConfigurationDTO));
    }

    @ApiOperation(value = "根据id删除党群配置")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要删除的id", dataType = "Long", required = true)
    @HasPermission("party_masses_edit")
    @DeleteMapping("/{id}")
    public ReturnEntity deleteById(@PathVariable("id") Long id) {
        return ReturnUtil.buildReturn(partyMassesConfigurationService.deleteById(id));
    }

    @ApiOperation(value = "根据id查询党群配置", notes = "根据id查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "Long", required = true)
    @HasPermission({"party_serviceCenterManagement","party_masses_look"})
    @GetMapping("/{id}")
    public PartyMassesConfigurationVO findByIdPartyMassesConfiguration(@PathVariable("id") Long id) {
        return partyMassesConfigurationService.selectById(id);
    }

    @ApiOperation(value = "查询党群配置列表")
    @HasPermission({"party_serviceCenterManagement","party_masses_look"})
    @GetMapping
    public PageInfo<PartyMassesConfigurationVO> selectList(@ApiParam(name = "党群配置查询实体") @Validated PartyMassesConfigurationQueryBean partyMassesConfigurationQueryBean, Page page){
        return new PageInfo<>(partyMassesConfigurationService.selectList(partyMassesConfigurationQueryBean, page));
    }
}
