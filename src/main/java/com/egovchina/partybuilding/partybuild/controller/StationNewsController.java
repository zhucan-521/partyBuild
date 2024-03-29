package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.dto.MessageAddDTO;
import com.egovchina.partybuilding.partybuild.dto.MessageUpdateDTO;
import com.egovchina.partybuilding.partybuild.entity.StationNewsQueryBean;
import com.egovchina.partybuilding.partybuild.service.StationNewsService;
import com.egovchina.partybuilding.partybuild.vo.MessageSendVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author create by GuanYingxin on 2019/5/23 9:44
 * @description 站内消息控制器
 */
@Api(tags = "党组织-站内消息v1-官颖鑫")
@RestController
@RequestMapping("/v1/station-news")
public class StationNewsController {

    @Autowired
    private StationNewsService stationNewsService;

    @ApiOperation(value = "添加站内消息（可批量）", notes = "添加站内消息（可批量）", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertStationNews(@RequestBody @Validated @ApiParam("新增消息dto") MessageAddDTO messageAddDTO) {
        return ReturnUtil.buildReturn(stationNewsService.batchInsertStationNews(messageAddDTO));
    }

    @Deprecated
    @ApiOperation(value = "党员获取发送给自己的消息列表", notes = "党员获取发送给自己的消息列表", httpMethod = "GET")
    @ApiImplicitParam(name = "receiverId", value = "接收者id", dataType = "long", paramType = "path", required = true)
    @GetMapping("/{receiverId}/personal")
    public PageInfo<MessageSendVO> getMessageSendList(@ApiParam("分页参数") Page page, @PathVariable Long receiverId) {
        return new PageInfo<>(stationNewsService.getMessageSendVOList(page, receiverId));
    }

    @Deprecated
    @ApiOperation(value = "修改站内消息(可批量)", notes = "修改站内消息(可批量)", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateStationNews(@RequestBody @Validated @ApiParam("更新参数dto") MessageUpdateDTO messageUpdateDTO) {
        return ReturnUtil.buildReturn(stationNewsService.batchUpdateStationNews(messageUpdateDTO));
    }

    @ApiOperation(value = "账号获取自己所管理组织的消息列表", notes = "账号获取自己所管理组织的消息列表", httpMethod = "GET")
    @GetMapping("/organization")
    public PageInfo<MessageSendVO> getOrgMessageSendList(@ApiParam("分页参数") Page page, @ApiParam("查询参数") @Validated StationNewsQueryBean stationNewsQueryBean) {
        return new PageInfo<>(stationNewsService.getOrgMessageSendList(page, stationNewsQueryBean));
    }

    @ApiOperation(value = "查看某一个消息详情(同时更新消息接收状态)", notes = "查看某一个消息详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiveId", value = "消息接收id", dataType = "long", paramType = "path", required = true)
    })
    @GetMapping("/{receiveId}/details")
    public MessageSendVO getMessageSend(@PathVariable Long receiveId) {
        return stationNewsService.getMessageSendVO(receiveId);
    }

    @ApiOperation(value = "显示未提醒的信息", notes = "显示未提醒的信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rangeDeptId", value = "组织id", dataType = "long", paramType = "path", required = true),
            @ApiImplicitParam(name = "orgRange", value = "组织范围", dataType = "long", paramType = "query")
    })
    @GetMapping("/{rangeDeptId}/not-remind")
    public PageInfo<MessageSendVO> getNotRemindedMessage(@PathVariable Long rangeDeptId, Long orgRange) {
        return new PageInfo<>(stationNewsService.getNotRemindedMessageVO(rangeDeptId, orgRange));
    }

}
