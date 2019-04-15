package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.PartyBuildingWorkInfoDto;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDto;
import com.egovchina.partybuilding.partybuild.dto.SysDeptDtoWithCountInfo;
import com.egovchina.partybuilding.partybuild.dto.TabDeptPositionDto;
import com.egovchina.partybuilding.partybuild.service.TabSysDeptService;
import com.egovchina.partybuilding.partybuild.system.entity.SysDept;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "党组织-组织信息模块-张帆")
@RestController
@RequestMapping("/organization")
public class TabSysDeptController {

    @Autowired
    private TabSysDeptService tabSysDeptService;

    @ApiOperation(value = "组织信息列表", notes = "组织信息列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode", value = "组织编码", paramType = "query"),
            @ApiImplicitParam(name = "unitCode", value = "所在单位代码", paramType = "query"),
            @ApiImplicitParam(name = "orgName", value = "组织名称", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", paramType = "query"),
            @ApiImplicitParam(name = "orgStatus", value = "组织状态", paramType = "query"),
            @ApiImplicitParam(name = "orgnizeMaster", value = "书记名称", paramType = "query"),
            @ApiImplicitParam(name = "dependencyRelation", value = "组织隶属关系 dict ZZLS 多个用,号拼接", paramType = "query"),
            @ApiImplicitParam(name = "orgnizeProperty", value = "组织类别 dict ZZLB 多个用,号拼接", paramType = "query"),
            @ApiImplicitParam(name = "unitProperty", value = "单位类型 dict DWLB 多个用,号拼接", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<SysDept> list(String orgCode, String unitCode, String orgName, String unitName, String orgStatus, String orgnizeMaster,
                                  String dependencyRelation, String orgnizeProperty,
                                  String unitProperty, Page page, OrgRange orgRange) {
        Map<String, Object> conditions = orgRange.toMap();
        conditions.put("orgCode", orgCode);
        conditions.put("unitCode", unitCode);
        conditions.put("name", orgName);
        conditions.put("unitName", unitName);
        conditions.put("orgStatus", orgStatus);
        conditions.put("orgnizeMaster", orgnizeMaster);
        conditions.put("dependencyRelation", dependencyRelation);
        conditions.put("orgnizeProperty", orgnizeProperty);
        conditions.put("unitProperty", unitProperty);
        conditions.put("delFlag", "0");
        List<SysDept> list = tabSysDeptService.selectWithConditions(conditions, page);
        PageInfo<SysDept> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "组织信息列表（包括统计信息）", notes = "组织信息列表（包括统计信息）", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode", value = "组织编码", paramType = "query"),
            @ApiImplicitParam(name = "unitCode", value = "所在单位代码", paramType = "query"),
            @ApiImplicitParam(name = "orgName", value = "组织名称", paramType = "query"),
            @ApiImplicitParam(name = "unitName", value = "单位名称", paramType = "query"),
            @ApiImplicitParam(name = "orgStatus", value = "组织状态", paramType = "query"),
            @ApiImplicitParam(name = "orgnizeMaster", value = "书记名称", paramType = "query"),
            @ApiImplicitParam(name = "dependencyRelation", value = "组织隶属关系 dict ZZLS 多个用,号拼接", paramType = "query"),
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(name = "orgnizeProperty", value = "组织类别 dict ZZLB 多个用,号拼接", paramType = "query"),
            @ApiImplicitParam(name = "unitProperty", value = "单位类型 dict DWLB 多个用,号拼接", paramType = "query")
    })
    @GetMapping("/listWithCount")
    public PageInfo<SysDeptDtoWithCountInfo> listWithCount(String orgCode, String unitCode, String orgName, String unitName, String orgStatus, String orgnizeMaster,
                                                           String dependencyRelation, String orgnizeProperty,
                                                           String unitProperty, Page page, OrgRange orgRange) {
        Map<String, Object> conditions = orgRange.toMap();
        conditions.put("orgCode", orgCode);
        conditions.put("unitCode", unitCode);
        conditions.put("name", orgName);
        conditions.put("unitName", unitName);
        conditions.put("orgStatus", orgStatus);
        conditions.put("orgnizeMaster", orgnizeMaster);
        conditions.put("dependencyRelation", dependencyRelation);
        conditions.put("orgnizeProperty", orgnizeProperty);
        conditions.put("unitProperty", unitProperty);
        conditions.put("delFlag", "0");
        List<SysDept> list = tabSysDeptService.selectWithConditions(conditions, page);
        List<SysDeptDtoWithCountInfo> returnList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(sysDept -> {
                SysDeptDtoWithCountInfo withCountInfo = tabSysDeptService.linkCountInfo(sysDept);
                BeanUtil.copyPropertiesIgnoreNull(sysDept, withCountInfo);
                returnList.add(withCountInfo);
            });
        }
        return PageInfoWrapper.wrapper(list, returnList);
    }

    @ApiOperation(value = "新增组织机构信息", notes = "新增组织机构信息", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "组织实体") @RequestBody @Validated SysDeptDto sysDeptDto) {

        orgDataVerification(sysDeptDto);
        int retVal = tabSysDeptService.insertWithAbout(sysDeptDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "组织信息修改", notes = "组织信息修改", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "组织实体") @RequestBody @Validated SysDeptDto sysDeptDto) {
        //orgDataVerification(sysDeptDto);
        int retVal = tabSysDeptService.updateWithAbout(sysDeptDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "组织信息详细", notes = "组织信息详细", httpMethod = "GET")
    @GetMapping("/details")
    public SysDeptDto details(@RequestParam("deptId") @ApiParam(name = "deptId", value = "组织ID", required = true) Long deptId) {
        return tabSysDeptService.selectWithAboutInfoByPrimaryKey(deptId);
    }

    @ApiOperation(value = "组织信息删除", notes = "组织信息删除", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam("deptId") @ApiParam(name = "deptId", value = "组织ID", required = true) Long deptId) {
        //逻辑删除
        int retVal = tabSysDeptService.logicDeleteById(deptId);
        return ReturnUtil.buildReturn(retVal);
    }

//    @ApiOperation(value = "导入组织信息数据", notes = "导入组织信息数据", httpMethod = "POST")
//    @PostMapping("/import")
//    public void importExcel(@RequestPart @ApiParam(value = "要导入文件", required = true) MultipartFile file,
//                            HttpServletResponse response) {
//
//        try {
//            tabSysDeptService.excelImport(file.getInputStream());
//
//            //错误文件下载
//            HSSFWorkbook errorWb = ExcelUtil.IMPORT_WB_HOLDER.get();
//            if (errorWb != null) {
//                ExcelUtil.setResponseStream(response, "组织信息导入_错误数据.xls");
//                errorWb.write(response.getOutputStream());
//            }
//        } catch (IOException e) {
//            throw new BusinessException(e);
//        }
//    }

//    @ApiOperation(value = "组织信息导入模板下载", notes = "组织信息导入模板下载", httpMethod = "GET")
//    @GetMapping("/templateDownload")
//    public void excelTemplateDownload(HttpServletResponse response) throws IOException {
//        HSSFWorkbook wb = tabSysDeptService.excelTemplateStream();
//        ExcelUtil.setResponseStream(response, "组织信息导入模板.xml");
//        wb.write(response.getOutputStream());
//    }

    @ApiOperation(value = "组织阵地列表", notes = "组织阵地列表", httpMethod = "GET")
    @GetMapping("/position/list")
    public PageInfo<TabDeptPositionDto> positionList(Page page, OrgRange orgRange) {
        HashMap<String, Object> conditions = orgRange.toMap();
        conditions.put("delFlag", "0");
        List<TabDeptPositionDto> list = tabSysDeptService.selectPositionWithConditions(conditions, page);
        PageInfo<TabDeptPositionDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "组织阵地详细", notes = "组织阵地详细", httpMethod = "GET")
    @GetMapping("/position/details")
    public TabDeptPositionDto positionDetails(@RequestParam("deptId") @ApiParam(name = "deptId", value = "组织ID", required = true) Long deptId) {
        return tabSysDeptService.selectPositionWithAboutInfoByPrimaryKey(deptId);
    }

    @ApiOperation(value = "组织阵地修改", notes = "组织阵地修改", httpMethod = "POST")
    @PostMapping("/position/update")
    public ReturnEntity positionUpdate(@ApiParam(value = "组织阵地实体") @RequestBody TabDeptPositionDto tabDeptPositionDto) {
        SysDept dbSysDept = tabSysDeptService.selectByPrimaryKey(tabDeptPositionDto.getDeptId().longValue());
        if (dbSysDept == null) {
            throw new BusinessDataNotFoundException("组织不存在");
        }
        BeanUtil.copyPropertiesIgnoreNull(tabDeptPositionDto, dbSysDept);
        int retVal = tabSysDeptService.updatePositionWithAnnexs(dbSysDept, tabDeptPositionDto.getArrangePhotos());
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "简单组织列表", notes = "对比组织列表返回数据较少", httpMethod = "GET")
    @GetMapping("/simpleList")
    public PageInfo<HashMap<String, Object>> simpleList(@RequestParam @ApiParam(value = "组织名称", required = true) String orgName,
                                                        Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("name", orgName);
        conditions.put("delFlag", "0");
        List<HashMap<String, Object>> list = tabSysDeptService.selectToMapWithConditions(conditions, page);
        PageInfo<HashMap<String, Object>> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "组织党建工作信息", notes = "组织党建工作信息", httpMethod = "GET")
    @GetMapping("/partyBuildingWorkInfo")
    public PartyBuildingWorkInfoDto partyBuildingWorkInfo(@RequestParam("deptId") @ApiParam(name = "deptId", value = "组织ID", required = true) Long deptId) {
        return tabSysDeptService.countPartyBuildingWorkInfo(deptId);
    }

    /**
     * 组织数据校验
     *
     * @param sysDeptDto
     */
    private void orgDataVerification(SysDeptDto sysDeptDto) {
        String deptName = sysDeptDto.getName();
        if (sysDeptDto.getDeptId() == null && tabSysDeptService.checkOrgNameAvailability(deptName)) {
            throw new BusinessDataInvalidException("组织名称重复");
        }
        SysDept parentDept = tabSysDeptService.selectAloneByPrimaryKey(sysDeptDto.getParentId().longValue());
        if (parentDept == null) {
            throw new BusinessDataInvalidException("上级组织不存在");
        }
        //单位上级相同
        if (sysDeptDto.getUnitState() == 59139L) {
            sysDeptDto.setUnitName(parentDept.getUnitName());
            sysDeptDto.setUnitId(parentDept.getUnitId());
        }
    }
    @ApiOperation(value = "维护组织结对共建信息",notes = "维护组织结对共建信息",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pairOrgId", value = "结对共建组织id", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "orgId", value = "组织id", paramType = "query", dataType = "Long")
    })
    @PostMapping("/pushPairOrgIdToDeptTable")
    public Integer pushPairOrgIdToDeptTable(Long pairOrgId, Long orgId){
        return tabSysDeptService.pushPairOrgIdToDeptTable(pairOrgId,orgId);
    }
}
