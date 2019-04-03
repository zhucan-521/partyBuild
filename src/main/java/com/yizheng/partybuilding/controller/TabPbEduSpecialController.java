package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduSpecialDto;
import com.yizheng.partybuilding.service.inf.TabPbEduSpecialService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 课程体系控制器
 *
 * @author wuyunjie
 * Date 2019-01-11 9:35
 */
@Api(value = "课程体系", tags = {"党员教育-课程体系-吴云杰"})
@RestController
@RequestMapping("/special")
public class TabPbEduSpecialController {

    @Autowired
    private TabPbEduSpecialService tabPbEduSpecialService;

    @ApiOperation(value = "新增课程体系", notes = "新增课程体系")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "课程体系TDO") @RequestBody TabPbEduSpecialDto tabPbEduSpecialDto) {
        int retVal = tabPbEduSpecialService.add(tabPbEduSpecialDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "根据id查询课程体系",notes = "根据id查询课程体系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程id", paramType = "query", dataType = "String")
    })
    @GetMapping("/queryCourseSystemById")
    public TabPbEduSpecialDto queryCourseSystemById(Long id) {
        return tabPbEduSpecialService.queryCourseSystemById(id);
    }

    @ApiOperation(value = "分页查询课程体系", notes = "分页查询课程体系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "title", value = "课程名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "region", value = "区域", paramType = "query", dataType = "Long")
    })
    @GetMapping("/pagingQueryCourseSystem")
    public PageInfo<TabPbEduSpecialDto> pagingQueryCourseSystem(Long id, String title, Long region, Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", id);
        conditions.put("title", title);
        conditions.put("region", region);
        conditions.put("delFlag", "0");
        List<TabPbEduSpecialDto> tabPbEduSpecialDtos = tabPbEduSpecialService.pagingQueryCourseSystem(conditions, page);
        PageInfo<TabPbEduSpecialDto> pageInfo = new PageInfo<>(tabPbEduSpecialDtos);
        return pageInfo;
    }

    @ApiOperation(value = "删除课程体系", notes = "删除课程体系")
    @DeleteMapping("/deleteCourseSystem/{id}")
    public ReturnEntity deleteCourseSystem(@ApiParam(value = "课程体系ID", required = true) @PathVariable Long id) {
        int retVal = tabPbEduSpecialService.deleteCourseSystem(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改课程体系", notes = "修改课程体系")
    @PutMapping("/modifyTheCurriculumSystem")
    public ReturnEntity modifyTheCurriculumSystem(@ApiParam(value = "课程体系TDO") @RequestBody TabPbEduSpecialDto tabPbEduSpecialDto) {
        int retVal = tabPbEduSpecialService.modifyTheCurriculumSystem(tabPbEduSpecialDto);
        return ReturnUtil.buildReturn(retVal);
    }
}
