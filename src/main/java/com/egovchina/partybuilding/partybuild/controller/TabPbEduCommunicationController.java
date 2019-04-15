package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduCommunication;
import com.egovchina.partybuilding.partybuild.service.TabPbEduCommunicationService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YangYingXiang on 2018/12/13
 */
@Deprecated
@Api(tags = "党员教育-学习交流模块 蒋安")
@RestController
@RequestMapping("/communication")
public class TabPbEduCommunicationController {

    @Autowired
    private TabPbEduCommunicationService communicationService;

    @Deprecated
    @ApiOperation(value = "学习交流模块(分页)",httpMethod = "GET",notes = "根据类型查询，type")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类别", paramType = "query")
    })
    public PageInfo<TabPbEduCommunication> listPage(Long type, @ApiParam Page page){
        return new PageInfo<>(communicationService.communicationList(type,page));
    }

    @Deprecated
    @ApiOperation(value = "添加",httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "保存学习交流信息")@RequestBody TabPbEduCommunication communication){
        int retVal = communicationService.add(communication);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "查询单条数据",httpMethod = "GET")
    @GetMapping("/findById")
    public TabPbEduCommunication findById(@RequestParam("id") @ApiParam(name = "id", value = "主键ID", required = true)Long id){
        return communicationService.findByIdData(id);
    }

    @Deprecated
    @ApiOperation(value = "修改",notes = "修改除了主键Id都不是必填，类型不可以填")
    @PutMapping("/edit")
    public ReturnEntity edit(@ApiParam(value = "修改学习交流信息")@RequestBody TabPbEduCommunication communication){
        int retVal = communicationService.edit(communication);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@PathVariable Long id ){
        int retVal = communicationService.deleteFindById(id);
        return ReturnUtil.buildReturn(retVal);
    }
}
