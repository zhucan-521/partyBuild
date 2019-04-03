package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.DateUtil;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbPositiveRegist;
import com.yizheng.partybuilding.service.inf.TabPbPositiveRegistService;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * @author YangYingXiang on 2018/11/29
 */
@Api(value = "在职党员社区报到", tags = {"党员-在职党员社区报到接口 杨颖翔"})
@RestController
@RequestMapping("/PositiveRegist")
public class TabPbPositiveRegistController {

    private static final String DATA_EXAMPLE = "2018-10-30";
    @Autowired
    private TabPbPositiveRegistService positiveRegistService;

    @ApiOperation(value = "保存报到党员", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@RequestBody TabPbPositiveRegist positiveRegistList) throws ParseException {
        positiveRegistList.setRegistDate(DateUtil.getDateFormat(positiveRegistList.getRegistDate()));
        int add = positiveRegistService.add(positiveRegistList);
        return ReturnUtil.buildReturn(add);
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取社区报到信息列表", notes = "可指定条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "党员名称", name = "userName", dataType = "String"),
            @ApiImplicitParam(value = "状态", name = "revokeTag", dataType = "Byte"),
            @ApiImplicitParam(value = "报到日期", name = "date", dataType = "String"),
            @ApiImplicitParam(value = "组织主键", name = "rangeDeptId", paramType = "query"),
            @ApiImplicitParam(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织", name = "orgRange", paramType = "query"),
    })
    public PageInfo<TabPbPositiveRegist> list(String userName, Byte revokeTag, String date, Long rangeDeptId, Long orgRange, @ApiParam Page page) {
        return new PageInfo<>(this.positiveRegistService.selectListPage(userName, revokeTag, date, rangeDeptId, orgRange, page));
    }

    @ApiOperation(value = "改变登记状态", notes = "1为已报到,2为已返回")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "状态", name = "revokeTag", dataType = "Byte", required = true),
            @ApiImplicitParam(value = "主键Id", name = "positiveRegistId", dataType = "Long", required = true),
    })
    @PutMapping("/edit")
    public ReturnEntity editPositive(Long positiveRegistId, Byte revokeTag) {
        int retVal = positiveRegistService.editPositive(positiveRegistId, revokeTag);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "根据主键ID删除信息", notes = "主键ID为必填")
    @ApiImplicitParam(name = "Id", value = "主键ID", required = true, dataType = "Long")
    @DeleteMapping("/{Id}")
    public ReturnEntity delete(@PathVariable Long Id) {
        int delete = positiveRegistService.delete(Id);
        return ReturnUtil.buildReturn(delete);
    }

    @ApiOperation(value = "根据userId取消报到标识", notes = "userId为必填")
    @ApiImplicitParam(name = "userId", value = "主键ID", required = true, dataType = "Long")
    @DeleteMapping("delectRegistStatus/{userId}")
    public ReturnEntity delectRegistStatus(@PathVariable Long userId) {
        int retVal = positiveRegistService.delectRegistStatus(userId);
        return ReturnUtil.buildReturn(retVal);
    }

    @GetMapping(value = "/getFindById")
    @ApiOperation(value = "查看报到党员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "主键id", name = "positiveRegistId", dataType = "Long" ,required = true),
    })
    public TabPbPositiveRegist editFindById(Long positiveRegistId){
        return positiveRegistService.editFindById(positiveRegistId);
    }

}
