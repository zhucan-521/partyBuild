package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesQueryBean;
import com.egovchina.partybuilding.partybuild.service.PartyMassesService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesTree;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:接口类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@Api(tags = "党组织-党群-v1-吴云杰")
@RestController
@RequestMapping("/v1/party-masses")
public class PartyMassesController {

    @Autowired
    private PartyMassesService partyMassesService;

    @ApiOperation(value = "添加党群")
    @HasPermission("party_masses_add")
    @PostMapping
    public ReturnEntity save(@ApiParam(name = "党群DTO") @RequestBody @Validated PartyMassesDTO partyMassesDTO) {
        return ReturnUtil.buildReturn(partyMassesService.save(partyMassesDTO));
    }

    @ApiOperation(value = "根据id更新党群")
    @HasPermission("party_masses_edit")
    @PutMapping
    public ReturnEntity update(@ApiParam(name = "党群DTO") @RequestBody @Validated PartyMassesDTO partyMassesDTO) {
        return ReturnUtil.buildReturn(partyMassesService.updateById(partyMassesDTO));
    }

    @ApiOperation(value = "根据id删除党群")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要删除的id", dataType = "Long", required = true)
    @HasPermission("party_masses_del")
    @DeleteMapping("/{id}")
    public ReturnEntity deleteById(@PathVariable("id") Long id) {
        return ReturnUtil.buildReturn(partyMassesService.deleteById(id));
    }

    @ApiOperation(value = "根据id查询党群", notes = "根据id查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "Long", required = true)
    @HasPermission({"party_serviceCenterManagement","party_partyMap"})
    @GetMapping("/{id}")
    public PartyMassesVO findByIdPartyMasses(@PathVariable("id") Long id) {
        return partyMassesService.selectById(id);
    }

    @ApiOperation(value = "查询党群列表")
    @HasPermission({"party_serviceCenterManagement","party_partyMap"})
    @GetMapping
    public PageInfo<PartyMassesVO> selectList(@ApiParam(name = "党群查询实体") @Validated PartyMassesQueryBean partyMassesQueryBean, Page page) {
        return new PageInfo<>(partyMassesService.selectList(partyMassesQueryBean, page));
    }

    @ApiOperation(value = "根据父id获取党群列表", notes = "根据父id获取党群列表", httpMethod = "GET")
    @ApiImplicitParam(value = "父id", name = "parentId", dataType = "long", paramType = "path", required = true)
    @HasPermission({"party_serviceCenterManagement","party_partyMap"})
    @GetMapping("/parent/{parentId}")
    public List<PartyMassesTree> getPartyMassesListByOrgParentId(@PathVariable Long parentId) {
        return partyMassesService.getPartyMassesListByOrgParentId(parentId);
    }

}
