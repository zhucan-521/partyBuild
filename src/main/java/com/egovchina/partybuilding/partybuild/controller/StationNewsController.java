package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.MessageAddDTO;
import com.egovchina.partybuilding.partybuild.dto.MessageUpdateDTO;
import com.egovchina.partybuilding.partybuild.service.StationNewsService;
import com.egovchina.partybuilding.partybuild.service.TimedTransmissionService;
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

    @Autowired
    private TimedTransmissionService timedTransmissionService;

    @ApiOperation(value = "添加站内消息（可批量）", notes = "添加站内消息（可批量）", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertStationNews(@RequestBody @Validated @ApiParam("新增消息dto") MessageAddDTO messageAddDTO) {
        return ReturnUtil.buildReturn(stationNewsService.batchInsertStationNews(messageAddDTO));
    }

    @ApiOperation(value = "党员获取发送给自己的消息列表", notes = "党员获取发送给自己的消息列表", httpMethod = "GET")
    @ApiImplicitParam(name = "receiverId", value = "接收者id", dataType = "long", paramType = "path", required = true)
    @GetMapping("/{receiverId}")
    public PageInfo<MessageSendVO> getMessageSendList(@ApiParam("分页参数") Page page, @PathVariable Long receiverId) {
        return new PageInfo<>(stationNewsService.getMessageSendVOList(page, receiverId));
    }

    @ApiOperation(value = "党员获取在自己组织下的消息列表", notes = "党员获取在自己组织下的消息列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receiverOrgId", value = "接收者组织id", dataType = "long", paramType = "path", required = true),
            @ApiImplicitParam(name = "receiverType", value = "接收者类型", dataType = "long", paramType = "path", required = true)
    })
    @GetMapping("/{receiverOrgId}/{receiverType}")
    public PageInfo<MessageSendVO> getOrgMessageSendList(@ApiParam("分页参数") Page page, @PathVariable Long receiverOrgId, @PathVariable Long receiverType) {
        return new PageInfo<>(stationNewsService.getOrgMessageSendList(page, receiverOrgId, receiverType));
    }

    @ApiOperation(value = "查看某一个消息详情", notes = "查看某一个消息详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sendId", value = "消息发送id", dataType = "long", paramType = "path", required = true),
    })
    @GetMapping("/{sendId}/details")
    public MessageSendVO getMessageSend(@PathVariable Long sendId) {
        return stationNewsService.getMessageSendVO(sendId);
    }

    @Deprecated
    @ApiOperation(value = "修改站内消息(可批量)", notes = "修改站内消息(可批量)", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateStationNews(@RequestBody @Validated @ApiParam("更新参数dto") MessageUpdateDTO messageUpdateDTO) {
        return ReturnUtil.buildReturn(stationNewsService.batchUpdateStationNews(messageUpdateDTO));
    }

    @ApiOperation(value = "提醒领导班子", notes = "提醒领导班子", httpMethod = "GET")
    @GetMapping("/remind-lead")
    public ReturnEntity remindTheLeadershipTeam() {
        return ReturnUtil.buildReturn(timedTransmissionService.remindTheLeadershipTeam());
    }
}
