package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysDept;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.OrganizationDTO;
import com.egovchina.partybuilding.partybuild.dto.OrganizationPositionDTO;
import com.egovchina.partybuilding.partybuild.entity.OrganizationQueryBean;
import com.egovchina.partybuilding.partybuild.service.OrganizationService;
import com.egovchina.partybuilding.partybuild.vo.OrganizationPartyBuildingWorkVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationPositionVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 组织API
 *
 * @Author Zhang Fan
 **/
@Api(tags = "党组织-组织信息模块v1-张帆")
@RestController
@RequestMapping("/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @ApiOperation(value = "组织信息列表", notes = "组织信息列表", httpMethod = "GET")
    @HasPermission(value = "party_tissueInfo")
    @GetMapping
    public PageInfo<OrganizationVO> getOrganizationList(OrganizationQueryBean queryBean, Page page) {
        List<OrganizationVO> list = organizationService.selectOrganizationVOWithCondition(queryBean, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "新增组织", notes = "新增组织", httpMethod = "POST")
    @HasPermission(value = "party_orgInfo_add")
    @PostMapping
    public ReturnEntity insertOrganization(@ApiParam(value = "组织实体") @RequestBody @Validated OrganizationDTO organizationDTO) {
        orgDataVerification(organizationDTO);
        return ReturnUtil.buildReturn(organizationService.insertOrganization(organizationDTO));
    }

    @ApiOperation(value = "修改组织", notes = "修改组织", httpMethod = "PUT")
    @HasPermission(value = "party_orgInfo_edit")
    @PutMapping
    public ReturnEntity updateOrganization(@ApiParam(value = "组织实体") @RequestBody @Validated OrganizationDTO organizationDTO) {
        orgDataVerification(organizationDTO);
        return ReturnUtil.buildReturn(organizationService.updateOrganization(organizationDTO));
    }

    @ApiOperation(value = "组织信息详细", notes = "组织信息详细", httpMethod = "GET")
    @ApiImplicitParam(value = "组织id", name = "orgId", dataType = "long", paramType = "path", required = true)
    @HasPermission(value = "party_orgInfo_abstract")
    @GetMapping("/{orgId}")
    public OrganizationVO getOrganization(@PathVariable("orgId") Long orgId) {
        return organizationService.selectOrganizationVOByOrgId(orgId);
    }

    @ApiOperation(value = "组织信息删除", notes = "组织信息删除", httpMethod = "DELETE")
    @ApiImplicitParam(value = "组织id", name = "orgId", dataType = "long", paramType = "path", required = true)
    @HasPermission(value = "party_orgInfo_delete")
    @DeleteMapping("/{orgId}")
    public ReturnEntity deleteOrganization(@PathVariable("orgId") Long orgId) {
        return ReturnUtil.buildReturn(organizationService.logicDeleteById(orgId));
    }

    @ApiOperation(value = "组织阵地列表", notes = "组织阵地列表", httpMethod = "GET")
    @GetMapping("/positions")
    public PageInfo<OrganizationPositionVO> getOrganizationPositionList(Page page, OrgRange orgRange) {
        List<OrganizationPositionVO> list = organizationService.selectOrganizationPositionVOByCondition(orgRange.toMap(), page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "组织阵地详细", notes = "组织阵地详细", httpMethod = "GET")
    @ApiImplicitParam(value = "组织id", name = "orgId", dataType = "long", paramType = "path", required = true)
    @GetMapping("/{orgId}/positions")
    public OrganizationPositionVO getOrganizationPosition(@PathVariable("orgId") Long orgId) {
        return organizationService.selectOrganizationPositionVOAndAttachmentsById(orgId);
    }

    @ApiOperation(value = "组织阵地修改", notes = "组织阵地修改", httpMethod = "PUT")
    @PutMapping("/positions")
    public ReturnEntity updateOrganizationPosition(@ApiParam(value = "组织阵地实体") @RequestBody @Validated OrganizationPositionDTO organizationPositionDTO) {
        return ReturnUtil.buildReturn(organizationService.updateOrganizationPosition(organizationPositionDTO));
    }

    @ApiOperation(value = "简单组织列表", notes = "对比组织列表返回数据较少", httpMethod = "GET")
    @GetMapping("/simple")
    public PageInfo<HashMap<String, Object>> getSimpleList(@RequestParam @ApiParam(value = "组织名称", required = true) String orgName,
                                                           Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("name", orgName);
        List<HashMap<String, Object>> list = organizationService.selectOrganizationSimpleListByCondition(conditions, page);
        return new PageInfo<>(list);
    }

    @ApiOperation(value = "组织党建工作信息", notes = "组织党建工作信息", httpMethod = "GET")
    @ApiImplicitParam(value = "组织id", name = "orgId", dataType = "long", paramType = "path", required = true)
    @GetMapping("/{orgId}/party-building-works")
    public OrganizationPartyBuildingWorkVO getPartyBuildingWorkInfo(@PathVariable("orgId") Long orgId) {
        return organizationService.selectOrganizationPartyBuildingWorkVOByOrgId(orgId);
    }

    @ApiOperation(value = "维护组织结对共建信息", notes = "维护组织结对共建信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pairOrgId", value = "结对共建组织id", paramType = "query", dataType = "Long", required = true),
            @ApiImplicitParam(name = "orgId", value = "组织id", paramType = "path", dataType = "Long", required = true)
    })
    @PutMapping("/{orgId}/paired-construction")
    public Integer pushPairOrgIdToDeptTable(@RequestParam Long pairOrgId, @PathVariable Long orgId) {
        return organizationService.pushPairOrgIdToDeptTable(pairOrgId, orgId);
    }

    /**
     * 组织数据校验
     *
     * @param organizationDTO
     */
    private void orgDataVerification(OrganizationDTO organizationDTO) {
        //校验组织名称有效性
        checkOrganizationNameAvailability(organizationDTO);

        //校验组织编码有效性
        checkOrganizationCodeAvailability(organizationDTO);

        SysDept parentDept = organizationService.selectAloneByPrimaryKey(organizationDTO.getParentId());
        if (parentDept == null) {
            throw new BusinessDataInvalidException("上级组织不存在");
        }
        //单位上级相同
        if (organizationDTO.getUnitState() == 59139L) {
            organizationDTO.setUnitName(parentDept.getUnitName());
            organizationDTO.setUnitId(parentDept.getUnitId());
        }

    }

    /**
     * 检验组织编码可用性
     *
     * @param organizationDTO
     */
    private void checkOrganizationCodeAvailability(OrganizationDTO organizationDTO) {
        boolean availability = Optional.ofNullable(organizationService.checkOrgCodeAvailability(
                organizationDTO.getOrgCode(), organizationDTO.getDeptId()))
                .orElse(false);
        if (availability) {
            throw new BusinessDataCheckFailException("组织编码已被占用");
        }
    }

    /**
     * 检验组织名称可用性
     *
     * @param organizationDTO 组织DTO
     */
    private void checkOrganizationNameAvailability(OrganizationDTO organizationDTO) {
        boolean availability = Optional.ofNullable(organizationService.checkOrgNameAvailability(
                organizationDTO.getName(), organizationDTO.getDeptId()))
                .orElse(false);
        if (availability) {
            throw new BusinessDataCheckFailException("组织名称已被占用");
        }
    }
}
