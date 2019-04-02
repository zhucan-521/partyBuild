package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduCommunication;
import com.yizheng.partybuilding.service.inf.TabPbEduCommunicationService;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YangYingXiang on 2018/12/13
 */
@Api(tags = "党员教育-学习交流模块 杨颖翔")
@RestController
@RequestMapping("/communication")
public class TabPbEduCommunicationController {

    @Autowired
    private TabPbEduCommunicationService communicationService;

    @ApiOperation(value = "学习交流模块(分页)",httpMethod = "GET",notes = "根据类型查询，type")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类别", paramType = "query")
    })
    public PageInfo<TabPbEduCommunication> listPage(Long type, @ApiParam Page page){
        return new PageInfo<>(communicationService.communicationList(type,page));
    }

    @ApiOperation(value = "添加",httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "保存学习交流信息")@RequestBody TabPbEduCommunication communication){
        int retVal = communicationService.add(communication);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查询单条数据",httpMethod = "GET")
    @GetMapping("/findById")
    public TabPbEduCommunication findById(@RequestParam("id") @ApiParam(name = "id", value = "主键ID", required = true)Long id){
        return communicationService.findByIdData(id);
    }

    @ApiOperation(value = "修改",notes = "修改除了主键Id都不是必填，类型不可以填")
    @PutMapping("/edit")
    public ReturnEntity edit(@ApiParam(value = "修改学习交流信息")@RequestBody TabPbEduCommunication communication){
        int retVal = communicationService.edit(communication);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@PathVariable Long id ){
        int retVal = communicationService.deleteFindById(id);
        return ReturnUtil.buildReturn(retVal);
    }
}
