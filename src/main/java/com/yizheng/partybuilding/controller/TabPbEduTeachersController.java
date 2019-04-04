package com.yizheng.partybuilding.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduTeachers;
import com.yizheng.partybuilding.entity.TabPbEduTeachersCourse;
import com.yizheng.partybuilding.service.inf.TabPbEduTeachersService;
import com.yizheng.commons.util.DateUtil;
import com.yizheng.commons.util.ExcelUtil;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.*;
import lombok.var;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YangYingXiang on 2018/12/10
 */
@Api(tags = "党员教育-师资管理模块 蒋安")
@RestController
@RequestMapping("/teachers")
public class TabPbEduTeachersController {

    @Autowired
    private TabPbEduTeachersService teachersService;

    @ApiOperation(value = "教师资格列表信息(分页)", httpMethod = "GET", notes = "可以根据区域查询，region")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "region", value = "区域 dict QY", paramType = "query"),
            @ApiImplicitParam(name = "teacherType", value = "师资类型 dict QY", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "老师姓名", paramType = "query")
    })
    public PageInfo<TabPbEduTeachers> listPage(Long region, Long teacherType, String name, @ApiParam Page page) {
        return new PageInfo<>(teachersService.listData(new EntityWrapper<>(new TabPbEduTeachers().setRegion(region).setTeacherType(teacherType).setName(name)), page));
    }

    @ApiOperation(value = "添加", httpMethod = "POST")
    @PostMapping("/add")
    public Long add(@ApiParam(value = "保存师资信息") @RequestBody TabPbEduTeachers teachers) {
        return teachersService.add(teachers);
    }

    @ApiOperation(value = "查询单条数据", httpMethod = "GET")
    @GetMapping("/findById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "主键id", paramType = "query", required = true),
            @ApiImplicitParam(name = "flag", value = "是否需要获取课程信息", paramType = "query", dataType = "boolean"),
    })
    public TabPbEduTeachers findById(Long teacherId, boolean flag) {
        return teachersService.findByIdData(teacherId, flag);
    }

    @ApiOperation(value = "修改", notes = "修改除了主键Id都不是必填")
    @PutMapping("/edit")
    public ReturnEntity edit(@ApiParam(value = "修改师资信息") @RequestBody TabPbEduTeachers teachers) {
        int edit = teachersService.edit(teachers);
        return ReturnUtil.buildReturn(edit);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{teacherId}")
    public ReturnEntity delete(@PathVariable Long teacherId) {
        int delete = teachersService.delete(teacherId);
        return ReturnUtil.buildReturn(delete);
    }

    @ApiOperation(value = "检查账号是否重复，返回false为账号名已存在。true为不存在。")
    @GetMapping("/checkAccount")
    public ReturnEntity checkAccount(@RequestParam("account") @ApiParam(name = "account", value = "账号") String account) {
        int retVal = teachersService.checkAccount(account);
        return ReturnUtil.buildReturn(retVal);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出", notes = "可指定条件导出", httpMethod = "GET")
    public void export(HttpServletResponse response, Long region) throws IOException {
        var list = teachersService.listData(new EntityWrapper<>(new TabPbEduTeachers().setRegion(region)), null);
        String[] title = {"区域", "账号", "职位", "工作单位", "邮箱", "擅长领域", "创建时间"};
        List<String[]> exportDataList = list.stream().map(teachers -> new String[]{
                teachers.getRegion().toString(),
                teachers.getPosition(),
                teachers.getHealth(),
                teachers.getEmail(),
                teachers.getGoodField().toString(),
                DateUtil.ConversionString(teachers.getCreateTime())

        }).collect(Collectors.toList());
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook("师资管理", title, exportDataList.toArray(new String[0][7]), null);
        ExcelUtil.setResponseStream(response, "师资管理.xls");
        workbook.write(response.getOutputStream());

    }

    @ApiOperation(value = "师资课程(分页)", httpMethod = "GET")
    @GetMapping("/listCourse")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "师资id", paramType = "query"),
    })
    public PageInfo<TabPbEduTeachersCourse> selectList(Long teacherId, @ApiParam Page page) {
        return new PageInfo<>(teachersService.selectList(new TabPbEduTeachersCourse().setTeacherId(teacherId), page));
    }

    @ApiOperation(value = "师资课程(添加)", httpMethod = "POST")
    @PostMapping("/addCourse")
    public ReturnEntity insertSelective(@ApiParam(value = "保存师资课程") @RequestBody TabPbEduTeachersCourse course) {
        int retVal = teachersService.insertSelective(course);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "师资课程(查询单条数据)", httpMethod = "GET")
    @GetMapping("/findByIdCourse")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", paramType = "query", required = true),
    })
    public TabPbEduTeachersCourse findById(Long id) {
        return teachersService.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "师资课程(修改)", notes = "修改除了主键Id都不是必填")
    @PutMapping("/editCourse")
    public ReturnEntity updateByPrimaryKeySelective(@ApiParam(value = "修改师资课程") @RequestBody TabPbEduTeachersCourse course) {
        int retVal = teachersService.updateByPrimaryKeySelective(course);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "师资课程(删除)")
    @DeleteMapping("/tombstone{id}")
    public ReturnEntity tombstone(@PathVariable Long id) {
        int retVal = teachersService.tombstone(id);
        return ReturnUtil.buildReturn(retVal);
    }

}
