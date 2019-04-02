package com.yizheng.partybuilding.controller;

import com.yizheng.partybuilding.entity.TabPbFamily;
import com.yizheng.partybuilding.service.inf.TabPbFamilyService;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YangYingXiang on 2018/11/26
 */
@Api(value = "家庭成员", tags = {"党员-家庭成员操作接口 杨颖翔"})
@RestController
@RequestMapping("/family")
public class TabPbFamilyController {

    @Autowired
    private TabPbFamilyService tabPbFamilyService;

    @ApiOperation(value = "查询list家庭成员信息", notes = "人员ID为必填")
    @ApiImplicitParam(name = "userId", value = "人员ID", required = true, dataType = "long")
    @GetMapping("/list")
    public List<TabPbFamily> getListPage(Long userId) {
        List<TabPbFamily> list = tabPbFamilyService.getListPage(userId);
        return list;
    }

    @ApiOperation(value = "根据家庭成员主键Id查询信息", notes = "家庭成员主键ID为必填")
    @ApiImplicitParam(name = "relationId", value = "家庭成员主键ID", required = true, dataType = "long", paramType = "query")
    @GetMapping("/findById")
    public TabPbFamily selectByPrimaryKey(Long relationId) {
        TabPbFamily tabPbFamily = tabPbFamilyService.selectByPrimaryKey(relationId);
        return tabPbFamily;
    }

    @ApiOperation(value = "根据家庭成员主键ID删除信息", notes = "家庭成员主键ID为必填")
    @ApiImplicitParam(name = "relationId", value = "家庭成员主键ID", required = true, dataType = "long")
    @DeleteMapping("/{relationId}")
    public ReturnEntity deleteByPrimaryKey(@PathVariable Long relationId) {
        int retVal = tabPbFamilyService.deleteByPrimaryKey(relationId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "保存单个家庭成员信息", notes = "添加家庭成员", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity insert(@RequestBody TabPbFamily tabPbFamily) {
        int add = tabPbFamilyService.add(tabPbFamily);
        return ReturnUtil.buildReturn(add);
    }

    @ApiOperation(value = "修改单个家庭成员信息", notes = "家庭成员主键ID为必填")
    @PostMapping("/update")
    public ReturnEntity updateByPrimaryKeySelective(@ApiParam(name = "家庭成员对象") @RequestBody TabPbFamily tabPbFamily) {
        int retVal = tabPbFamilyService.updateByPrimaryKeySelective(tabPbFamily);
        return ReturnUtil.buildReturn(retVal);
    }

}
