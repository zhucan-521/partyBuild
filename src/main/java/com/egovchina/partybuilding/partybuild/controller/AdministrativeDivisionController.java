package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.LogCollectionIgnore;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.AdministrativeDivisionDTO;
import com.egovchina.partybuilding.partybuild.entity.AdministrativeDivisionQueryBean;
import com.egovchina.partybuilding.partybuild.service.AdministrativeDivisionService;
import com.egovchina.partybuilding.partybuild.vo.AdministrativeDivisionTree;
import com.egovchina.partybuilding.partybuild.vo.AdministrativeDivisionVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description:行政区划接口类
 *
 * @author WuYunJie
 * @date 2019/05/25 14:46:34
 */
@Api(tags = "党组织-行政区划-v1-吴云杰")
@RestController
@RequestMapping("/administrative-divisions")
public class AdministrativeDivisionController {

    @Autowired
    private AdministrativeDivisionService administrativeDivisionService;

    @ApiOperation(value = "添加行政区划")
    @PostMapping
    public ReturnEntity save(@RequestBody AdministrativeDivisionDTO administrativeDivisionDTO) {
        return ReturnUtil.buildReturn(administrativeDivisionService.save(administrativeDivisionDTO));
    }

    @ApiOperation(value = "根据id更新行政区划")
    @PutMapping
    public ReturnEntity update(@RequestBody AdministrativeDivisionDTO administrativeDivisionDTO) {
        return ReturnUtil.buildReturn(administrativeDivisionService.updateById(administrativeDivisionDTO));
    }

    @ApiOperation(value = "根据id删除行政区划")
    @ApiImplicitParam(paramType = "path", name = "id", value = "要删除的id", dataType = "Long", required = true)
    @DeleteMapping("/{id}")
    public ReturnEntity deleteById(@PathVariable("id") Long id) {
        return ReturnUtil.buildReturn(administrativeDivisionService.deleteById(id));
    }

    @ApiOperation(value = "根据id查询行政区划", notes = "根据id查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键id", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public AdministrativeDivisionVO findByIdAdministrativeDivision(@PathVariable("id") Long id) {
        return administrativeDivisionService.selectById(id);
    }

    @ApiOperation(value = "查询行政区划列表")
    @GetMapping
    public PageInfo<AdministrativeDivisionVO> selectList(AdministrativeDivisionQueryBean administrativeDivisionQueryBean, Page page) {
        return new PageInfo<>(administrativeDivisionService.selectList(administrativeDivisionQueryBean, page));
    }

    @ApiOperation(value = "获取行政区划树列表", notes = "获取行政区划树列表", httpMethod = "GET")
    @LogCollectionIgnore
    @GetMapping("/tree")
    public List<AdministrativeDivisionTree> selectListByParentId() {
        return administrativeDivisionService.selectListByParentId();
    }

    @Deprecated
    @ApiOperation(value = "导入行政区划", notes = "限制上传 .xls", httpMethod = "POST")
    @PostMapping("/upload")
    public boolean upload(@ApiParam(value = "基础信息", required = true) MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        return administrativeDivisionService.batchImport(fileName, file);
    }
}
