package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNotice;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNoticeDept;
import com.egovchina.partybuilding.partybuild.service.TabPbMsgNoticeService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author YangYingXiang on 2018/12/29
 */
@Api(tags = "上传下达-文件通知 朱灿")
@RestController
@RequestMapping("/msgNotice")
public class TabPbMsgNoticeController {

    @Autowired
    private TabPbMsgNoticeService noticeService;

    @ApiOperation(value = "发出文件通知列表信息(分页)",httpMethod = "GET")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织", paramType = "query",required = true),
            @ApiImplicitParam(name = "stateTime", value = "开始时间", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "发布状态", paramType = "query"),
            @ApiImplicitParam(name = "noticeType", value = "通知类别", paramType = "query"),
            @ApiImplicitParam(name = "orgRange ",value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织",dataType = "long"),
    })
    public PageInfo<TabPbMsgNotice> listPage(TabPbMsgNotice notice, @ApiParam Page page) throws ParseException {
        return new PageInfo<>(noticeService.selectList(notice,page));
    }

    @ApiOperation(value = "添加",httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "保存信息")@RequestBody TabPbMsgNotice notice){
        int add = noticeService.add(notice);
        return ReturnUtil.buildReturn(add);
    }

    @ApiOperation(value = "查询单条数据",httpMethod = "GET",notes = "需要多条明细传id，单条明细传noticeId")
    @GetMapping("/findById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", paramType = "query"),
            @ApiImplicitParam(name = "noticeId", value = "明细id",paramType = "query"),
    })
    public TabPbMsgNotice findById(Long id,Long noticeId){
        return noticeService.findById(id,noticeId);
    }

    @ApiOperation(value = "修改",notes = "修改除了主键Id都不是必填")
    @PutMapping("/edit")
    public ReturnEntity edit(@ApiParam(value = "修改信息")@RequestBody TabPbMsgNotice notice){
        int edit = noticeService.edit(notice);
        return ReturnUtil.buildReturn(edit);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public ReturnEntity delete(@PathVariable Long id ){
        int retVal = noticeService.tombstone(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "改变状态")
    @PutMapping("/editState")
    public ReturnEntity editState(@ApiParam(value = "改变状态信息,只需要传主键id和状态值")@RequestBody TabPbMsgNotice notice){
        int retVal = noticeService.editState(notice);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "签收")
    @PutMapping("/updateSigning")
    public ReturnEntity updateSigning(@ApiParam(value = "签收需要传主键id")@RequestBody TabPbMsgNoticeDept noticeDept){
        int retVal = noticeService.updateSigning(noticeDept);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "收到文件通知列表信息(分页)",httpMethod = "GET")
    @GetMapping("/selectNoticeDeptList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织", paramType = "query"),
            @ApiImplicitParam(name = "stateTime", value = "开始时间", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query"),
            @ApiImplicitParam(name = "orgRange ",value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织",dataType = "long"),
    })
    public PageInfo<TabPbMsgNoticeDept> selectNoticeDeptList(Long deptId, String stateTime,String endTime,Long orgRange,  @ApiParam Page page) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        TabPbMsgNoticeDept noticeDept = new TabPbMsgNoticeDept();
        noticeDept.setDeptId(deptId);
        noticeDept.setStateTime(format.parse(stateTime));
        noticeDept.setEndTime(format.parse(endTime));
        noticeDept.setOrgRange(orgRange);
        return new PageInfo<>(noticeService.selectNoticeDeptList(noticeDept,page));
    }
}
