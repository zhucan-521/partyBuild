package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.MsgNoticeDTO;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeDeptQueryBean;
import com.egovchina.partybuilding.partybuild.entity.MsgNoticeQueryBean;
import com.egovchina.partybuilding.partybuild.service.MsgNoticeService;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeDeptVO;
import com.egovchina.partybuilding.partybuild.vo.MsgNoticeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author zhucan on 2018/12/29
 */
@Api(tags = "上传下达-文件通知-v1-朱灿")
@RestController
@RequestMapping("/v1/notices")
public class MsgNoticeController {

    @Autowired
    private MsgNoticeService noticeService;

    @ApiOperation(value = "收到文件通知列表信息(分页)", httpMethod = "GET")
    @GetMapping("/receives")
    public PageInfo<MsgNoticeDeptVO> selectReceiveMsgNotice(MsgNoticeDeptQueryBean msgNoticeDeptQueryBean, Page page) {
        return new PageInfo<>(noticeService.selectReceiveMsgNotice(msgNoticeDeptQueryBean, page));
    }

    @ApiOperation(value = "发布文件通知列表信息(分页)", httpMethod = "GET")
    @GetMapping("/sends")
    public PageInfo<MsgNoticeVO> selectSendMsgNoticeList(MsgNoticeQueryBean msgNoticeQueryBean, Page page) {
        return new PageInfo<>(noticeService.selectSendMsgNoticeList(msgNoticeQueryBean, page));
    }

    @ApiOperation(value = "查询发出文件通知只需要传id，查询收到文件通知需要传id和noticeId", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", paramType = "path", required = true),
            @ApiImplicitParam(name = "noticeId", value = "下达文件通知noticeId", paramType = "query"),
    })
    @GetMapping("/{id}")
    public MsgNoticeVO getMsgNotice(@PathVariable Long id, Long noticeId) {
        return noticeService.getMsgNotice(id, noticeId);
    }

    @ApiOperation(value = "发布文件", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addMsgNotice(@ApiParam(name = "保存信息") @RequestBody @Validated MsgNoticeDTO msgNoticeDTO) {
        return ReturnUtil.buildReturn(noticeService.addMsgNotice(msgNoticeDTO));
    }

    @ApiOperation(value = "修改文件信息", notes = "修改除了主键Id都不是必填", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity editMsgNotice(@ApiParam(name = "文件信息") @RequestBody @Validated MsgNoticeDTO msgNoticeDTO) {
        return ReturnUtil.buildReturn(noticeService.editMsgNoticeById(msgNoticeDTO));
    }

    @ApiOperation(value = "改变文件发布状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", paramType = "path", required = true),
            @ApiImplicitParam(name = "state", value = "状态值 0.未发布、1.已发布(发布后可以取消)", paramType = "query"),
    })
    @PutMapping("{id}/state")
    public ReturnEntity editMsgNoticeState(@PathVariable Long id, String state) {
        return ReturnUtil.buildReturn(noticeService.editMsgNoticeState(id,state));
    }

    @ApiOperation(value = "文件签收", httpMethod = "PUT")
    @ApiImplicitParam(name = "id", required = true, value = "签收需要传主键id", paramType = "path")
    @PutMapping("/{id}")
    public ReturnEntity signNotice(@PathVariable Long id) {
        return ReturnUtil.buildReturn(noticeService.signNotice(id));
    }

    @ApiOperation(value = "删除文件", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", required = true, value = "主键id", paramType = "path")
    @DeleteMapping("/{id}")
    public ReturnEntity deleteMsgNotice(@PathVariable Long id) {
        return ReturnUtil.buildReturn(noticeService.deleteMsgNotice(id));
    }

}
