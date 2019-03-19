package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbDeptSecretary;
import com.yizheng.partybuilding.service.inf.ITabPbDeptSecretaryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YangYingXiang on 2019/03/01
 */
@Api(tags = "党员-书记")
@RestController
@RequestMapping("/secretary")
public class TabPbDeptSecretaryController {
    @Autowired
    ITabPbDeptSecretaryService secretaryService;

    @ApiOperation(value = "添加",httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "保存信息")@RequestBody TabPbDeptSecretary secretary){
        int add = secretaryService.insertSelective(secretary);
        return ReturnUtil.buildReturn(add);
    }

    @ApiOperation(value = "查询单条数据",httpMethod = "GET")
    @GetMapping("/findById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "secretaryId", value = "主键id", paramType = "query"),
    })
    public TabPbDeptSecretary findById(Long secretaryId){
        return secretaryService.selectByPrimaryKey(secretaryId);
    }

    @ApiOperation(value = "修改",notes = "修改除了主键Id都不是必填")
    @PutMapping("/edit")
    public ReturnEntity edit(@ApiParam(value = "修改信息")@RequestBody TabPbDeptSecretary secretary){
        int edit = secretaryService.updateByPrimaryKeySelective(secretary);
        return ReturnUtil.buildReturn(edit);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@PathVariable Long id ){
        int retVal = secretaryService.tombstone(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "书记(分页)",httpMethod = "GET")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rangeDeptId", value = "组织id", paramType = "query",required = true),
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query"),
            @ApiImplicitParam(name = "realname", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "orgRange ",value = "组织范围 0 全组织 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织）",dataType = "long"),
    })
    public PageInfo<TabPbDeptSecretary> selectList(Long rangeDeptId,String idCardNo,String realname,Long orgRange, Page page) {
        TabPbDeptSecretary secretary = new TabPbDeptSecretary();
        secretary.setRangeDeptId(rangeDeptId);
        secretary.setOrgRange(orgRange);
        secretary.setIdCardNo(idCardNo);
        secretary.setRealname(realname);
        return new PageInfo<>(secretaryService.selectList(secretary,page));
    }
}
