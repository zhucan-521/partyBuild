package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduMsgBoardDto;
import com.yizheng.partybuilding.entity.TabPbEduMsgBoard;
import com.yizheng.partybuilding.service.inf.TabPbEduMsgBoardService;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Jiang An
 **/
@Api(tags = "党员教育-留言板模块-蒋安")
@RestController
@RequestMapping("/msgBoard/")
public class TabPbEduMsgBoardController {

    @Autowired
    private TabPbEduMsgBoardService msgBoardService;

    @ApiOperation(value = "添加评论", notes = "添加评论", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@RequestBody TabPbEduMsgBoardDto record, Long courseId) {
        int retVal = msgBoardService.insert(record, courseId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查询所有评论", notes = "查询所有评论", httpMethod = "GET")
    @GetMapping("/list")
    public PageInfo<TabPbEduMsgBoard> findByAll(Page page) {
        List<TabPbEduMsgBoard> list = msgBoardService.selectWithConditions(page);
        PageInfo<TabPbEduMsgBoard> tabPbEduMsgBoardPageInfo = new PageInfo<>(list);

        return tabPbEduMsgBoardPageInfo;
    }

    @ApiOperation(value = "查询单个评论详情", notes = "查询单个评论详情,", httpMethod = "GET")
    @ApiImplicitParam(name = "msgId", value = "留言板Id", paramType = "query")
    @GetMapping("/findById")
    public TabPbEduMsgBoard findById(@RequestParam long msgId) {
        TabPbEduMsgBoard tabPbEduMsgBoard = msgBoardService.selectByPrimaryKey(msgId);
        return tabPbEduMsgBoard;
    }

    @ApiOperation(value = "删除单个评论", notes = "删除单个评论", httpMethod = "GET")
    @ApiImplicitParam(name = "msgId", value = "留言板Id", paramType = "query")
    @GetMapping("/delete")
    public ReturnEntity delete(long msgId) {
        int retVal = msgBoardService.deleteByPrimaryKey(msgId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查询登陆用户的留言信息",
            notes = "查询登陆用户的留言信息,msgType留言类型 1代表学习中心,2代表讲义课堂，3代表师资队伍，4代表教育阵地,5代表智能选学，6代表精品课程，7代表重点培训")
    @GetMapping("/getMsgBoardByUserId")
    public PageInfo<TabPbEduMsgBoard> getMsgBoardByUserId(Page page) {
        List<TabPbEduMsgBoard> list =
                msgBoardService.getMsgBoardByUserId(UserContextHolder.currentUser().getUserId().longValue(), page);
        PageInfo<TabPbEduMsgBoard> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
