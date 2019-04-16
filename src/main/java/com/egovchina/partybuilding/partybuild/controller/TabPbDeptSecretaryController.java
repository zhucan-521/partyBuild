package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
import com.egovchina.partybuilding.partybuild.service.TabPbDeptSecretaryService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YangYingXiang on 2019/03/01
 */
@Api(tags = "党员-书记 朱灿")
@RestController
@RequestMapping("/secretary")
public class TabPbDeptSecretaryController {
    @Autowired
    TabPbDeptSecretaryService secretaryService;

    @ApiOperation(value = "添加", httpMethod = "POST",notes = "杨颖翔")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "保存信息") @RequestBody TabPbDeptSecretary secretary) {
        int add = secretaryService.insertSelective(secretary);
        return ReturnUtil.buildReturn(add);
    }

    @ApiOperation(value = "查询单条数据", httpMethod = "GET",notes = "杨颖翔")
    @GetMapping("/findById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "secretaryId", value = "主键id", paramType = "query"),
    })
    public TabPbDeptSecretary findById(Long secretaryId) {
        return secretaryService.selectByPrimaryKey(secretaryId);
    }

    @ApiOperation(value = "修改", notes = "修改除了主键Id都不是必填 杨颖翔")
    @PutMapping("/edit")
    public ReturnEntity edit(@ApiParam(value = "修改信息") @RequestBody TabPbDeptSecretary secretary) {
        int edit = secretaryService.updateByPrimaryKeySelective(secretary);
        return ReturnUtil.buildReturn(edit);
    }

    @ApiOperation(value = "删除",notes = "杨颖翔")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@PathVariable Long id) {
        int retVal = secretaryService.tombstone(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "书记(分页)", httpMethod = "GET",notes = "杨颖翔")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query"),
            @ApiImplicitParam(name = "realname", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "positiveLevel",value = "职务")
    })
    public PageInfo<TabPbDeptSecretary> selectList(String idCardNo, String realname,
                                                   Page page, OrgRange orgRange,Long positiveLevel) {
        TabPbDeptSecretary secretary = new TabPbDeptSecretary();
        secretary.setIdCardNo(idCardNo);
        secretary.setRealname(realname);
        orgRange.paddingToEntity(secretary);
        //查询职务
        secretary.setPositiveLevel(positiveLevel);
        return new PageInfo<>(secretaryService.selectList(secretary, page));
    }

    @PutMapping("/editNum")
    @ApiOperation(value = "修改排序码",notes = "杨颖翔")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldId",value = "需要替换的人员id"),
            @ApiImplicitParam(name = "oldNum",value = "需要替换的人员排序码"),
            @ApiImplicitParam(name = "newId",value = "替换人员id"),
            @ApiImplicitParam(name = "newNum",value = "替换人员排序码"),
    })
    public ReturnEntity updateOrderNum(Long oldId, Long oldNum, Long newId, Long newNum,Page page){
        return ReturnUtil.buildReturn(secretaryService.updateOrderNum(oldId, oldNum, newId, newNum));
    }
}
