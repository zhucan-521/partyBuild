package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduCourseDto;
import com.yizheng.partybuilding.entity.TabPbEduCourse;
import com.yizheng.partybuilding.service.inf.TabPbEduCourseService;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "课程", tags = "党员教育-课程管理模块")
@RestController
@RequestMapping("/course")
public class TabPbEduCourseController {

    @Autowired
    TabPbEduCourseService tabPbEduCourseService;

    @ApiOperation(value = "新增", notes = "返回课程主键", httpMethod = "POST")
    @PostMapping("/insert")
    public Long insert(@RequestBody TabPbEduCourse tabPbEduCourse) {
        return tabPbEduCourseService.insertTabPbEduCourse(tabPbEduCourse);
    }

    @ApiOperation(value = "查询", notes = "不填则查询所有", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "课程名称", name = "name", paramType = "query"),
            @ApiImplicitParam(value = "课程Id", name = "courseId", paramType = "query"),
            @ApiImplicitParam(value = "活动时长 码表KCSC", name = "duration", paramType = "query"),
            @ApiImplicitParam(value = "人数限制 码表JDRL", name = "quantity", paramType = "query"),
            @ApiImplicitParam(value = "业务类型 码表YWLB", name = "busType", paramType = "query"),
    })
    @GetMapping("/selectAll")
    public PageInfo<TabPbEduCourseDto> selectAll(Page page, @ApiIgnore TabPbEduCourseDto dto) {
        return tabPbEduCourseService.selectAllTabPbEduCourse(dto, page);
    }

    @ApiOperation(value = "逻辑删除", notes = "传入courseId", httpMethod = "DELETE")
    @ApiImplicitParam(value = "课程Id", name = "courseId", required = true, paramType = "query")
    @DeleteMapping("/deleter")
    public ReturnEntity delete(Long courseId) {
        int retVal = tabPbEduCourseService.deleteByCouresId(courseId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改", notes = "必须要填写courseId", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@RequestBody TabPbEduCourseDto dto) {
        int retVal = tabPbEduCourseService.updateTabPbEduCourseDtoByCouresId(dto);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "添加观看次数", notes = "附带课程id", httpMethod = "GET")
    @GetMapping("/addWatchCount")
    @ApiImplicitParam(value = "课程id", name = "courseId", paramType = "query")
    public ReturnEntity addWatchCount(Long courseId) {
        int retVal = tabPbEduCourseService.addwatchCount(courseId);
        return ReturnUtil.buildReturn(retVal);
    }

}
