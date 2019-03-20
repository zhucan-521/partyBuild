package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.OrgRange;
import com.yizheng.commons.domain.Page;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
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

    @ApiOperation(value = "添加", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "保存信息") @RequestBody TabPbDeptSecretary secretary) {
        int add = secretaryService.insertSelective(secretary);
        return ReturnUtil.buildReturn(add);
    }

    @ApiOperation(value = "查询单条数据", httpMethod = "GET")
    @GetMapping("/findById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "secretaryId", value = "主键id", paramType = "query"),
    })
    public TabPbDeptSecretary findById(Long secretaryId) {
        return secretaryService.selectByPrimaryKey(secretaryId);
    }

    @ApiOperation(value = "修改", notes = "修改除了主键Id都不是必填")
    @PutMapping("/edit")
    public ReturnEntity edit(@ApiParam(value = "修改信息") @RequestBody TabPbDeptSecretary secretary) {
        int edit = secretaryService.updateByPrimaryKeySelective(secretary);
        return ReturnUtil.buildReturn(edit);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@PathVariable Long id) {
        int retVal = secretaryService.tombstone(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "书记(分页)", httpMethod = "GET")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query"),
            @ApiImplicitParam(name = "realname", value = "姓名", paramType = "query")
    })
    public PageInfo<TabPbDeptSecretary> selectList(String idCardNo, String realname,
                                                   Page page, OrgRange orgRange) {
        TabPbDeptSecretary secretary = new TabPbDeptSecretary();
        secretary.setIdCardNo(idCardNo);
        secretary.setRealname(realname);
        orgRange.paddingToEntity(secretary);
        return new PageInfo<>(secretaryService.selectList(secretary, page));
    }
}
