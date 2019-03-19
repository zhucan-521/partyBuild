package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduSubscribe;
import com.yizheng.partybuilding.service.inf.TabPbEduSubscribeSerivce;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "党员教育-预约模块")
@RestController
@RequestMapping("/tabPbSubscribe")
public class TabPbEduSubscribeController {

    @Autowired
    TabPbEduSubscribeSerivce tabPbEduSubscribeSerivce;

    @ApiOperation(value = "预约添加,返回预约人数", notes = "业务类型：1代表学习中心,2代表讲义课堂，3代表师资队伍，4代表教育阵地,5代表智能选学，6代表精品课程，7代表重点培训", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@RequestBody TabPbEduSubscribe tabPbEduSubscribe) {
        int insert = tabPbEduSubscribeSerivce.insert(tabPbEduSubscribe);
        return ReturnUtil.buildReturn(insert);
    }

    @ApiOperation(value = "预约查询", notes = "业务类型：1代表学习中心,2代表讲义课堂，3代表师资队伍，4代表教育阵地,5代表智能选学，6代表精品课程，7代表重点培训", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "likeType", value = "业务类型", paramType = "query"),
            @ApiImplicitParam(name = "busId", value = "业务id", paramType = "query"),
            @ApiImplicitParam(name = "subscribeId", value = "预约主键", paramType = "query")
    })
    @PostMapping("/select")
    public PageInfo<TabPbEduSubscribe> select(@ApiIgnore TabPbEduSubscribe tabPbEduSubscribe, Page page) {
        return tabPbEduSubscribeSerivce.select(tabPbEduSubscribe, page);
    }

    @ApiOperation(value = "修改预约", notes = "必须附带subscribeId", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@RequestBody TabPbEduSubscribe tabPbEduSubscribe) {
        int flag = tabPbEduSubscribeSerivce.update(tabPbEduSubscribe);
        return ReturnUtil.buildReturn(flag);
    }

    @ApiOperation(value = "删除", notes = "根据id删除", httpMethod = "DELETE")
    @ApiImplicitParam(value = "预约主键", name = "subscribeId", paramType = "query")
    @DeleteMapping("/delete")
    public ReturnEntity delete(Long subscribeId) {
        int flag = tabPbEduSubscribeSerivce.delete(subscribeId);
        return ReturnUtil.buildReturn(flag);
    }

    @ApiOperation(value = "查询单个详情", notes = "传入id", httpMethod = "GET")
    @GetMapping("/selectOne")
    public TabPbEduSubscribe selectOne(@RequestParam @ApiParam(value = "id", required = true) Long id) {
        return tabPbEduSubscribeSerivce.selectOneById(id);
    }


}
