package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduComParticipate;
import com.yizheng.partybuilding.service.inf.TabPbEduComParticipateService;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YangYingXiang on 2018/12/14
 */

@RestController
@RequestMapping("/participate")
@Api(tags = "党员教育-(明细)学习交流 朱灿")
public class TabPbEduComParticipateController {

    @Autowired
    private TabPbEduComParticipateService participateService;

    @ApiOperation(value = "(明细)添加", httpMethod = "POST", notes = "红星的为必填")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "(明细)保存学习交流信息") @RequestBody TabPbEduComParticipate participate) {
        int retVal = participateService.add(participate);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "(明细)学习交流模块(分页)", httpMethod = "GET", notes = "没有必填的")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "审核状态", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类别", paramType = "query"),
            @ApiImplicitParam(name = "cId", value = "主表学校交流id", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "党员id", paramType = "query")
    })
    public PageInfo<TabPbEduComParticipate> listPage(String state, Long type, Long cId, Long userId, @ApiParam Page page) {
        return new PageInfo<>(participateService.listParticipate(new TabPbEduComParticipate().setState(state).setType(type)
                .setCId(cId).setUserId(userId), page));
    }

    @ApiOperation(value = "(明细)查询单条数据", httpMethod = "GET")
    @GetMapping("/findById")
    public TabPbEduComParticipate findById(@RequestParam("id") @ApiParam(name = "id", value = "主键ID", required = true) Long id) {
        return participateService.findByIdData(id);
    }

    @ApiOperation(value = "(明细)修改", notes = "修改除了主键Id都不是必填，类型不可以填")
    @PutMapping("/edit")
    public ReturnEntity edit(@ApiParam(value = "修改学习交流信息") @RequestBody TabPbEduComParticipate participate) {
        int retVal = participateService.edit(participate);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "(明细)删除")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@PathVariable Long id) {
        int retVal = participateService.deleteFindById(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "(明细)改变状态", httpMethod = "GET")
    @GetMapping("/changeState")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "审核状态", paramType = "query", required = true),
            @ApiImplicitParam(name = "id", value = "主键id", paramType = "query", required = true),
            @ApiImplicitParam(name = "shResult", value = "审核结果", paramType = "query"),
    })
    public ReturnEntity changeState(Long id, String state, String shResult) {
        int retVal = participateService.changeState(id, state, shResult);
        return ReturnUtil.buildReturn(retVal);
    }
}
