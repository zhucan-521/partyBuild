package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 附件信息
 * 上传-获取附件信息
 * AttachmentType 里面维护下面的业务值
 * attachment_type : 1.工作计划，2.领导班子，3.组织阵地，4.工作总结，5.双评双述，6.党员扩展信息，8
 * 9.社区活动，11.奖励，12.处分，13.课程管理，14.直播管理，15.阵地管理，16.师资管理，17.重点培训，18.留言板
 */
@Api(tags = "附件信息模块")
@RestController
@RequestMapping("/attach")
public class TabPbAttachmentController {

    @Autowired
    private ITabPbAttachmentService attachmentService;

    @ApiOperation(value = "通过Id获取附件信息", notes = "通过Id获取附件信息", httpMethod = "GET")
    @GetMapping("/{attachmentId}")
    public TabPbAttachment getAttachment(@ApiParam(value = "附件Id", required = true) @PathVariable Long attachmentId) {
        return attachmentService.getById(attachmentId);
    }

    @ApiOperation(value = "通过hostId获取多个附件信息", notes = "通过hostId获取多个附件信息", httpMethod = "GET")
    @GetMapping("/listByHostId/{hostId}/{attachmentType}")
    public List<TabPbAttachment> listByHostId(@ApiParam(value = "附件HostId", required = true) @PathVariable Long hostId, @ApiParam(value = "业务类型", required = true) @PathVariable String attachmentType) {
        return attachmentService.listByHostId(hostId, attachmentType);
    }

    @ApiOperation(value = "添加附件", notes = "添加附件", httpMethod = "POST")
    @PostMapping
    public TabPbAttachment add(@ApiParam("附件对象") @Valid @RequestBody TabPbAttachment attachment) {
        return attachmentService.add(attachment);
    }

    @ApiOperation(value = "根据Id删除附件", notes = "根据Id删除附件", httpMethod = "DELETE")
    @DeleteMapping("/{attachmentId}")
    public ReturnEntity delById(@ApiParam(value = "附件Id", required = true) @PathVariable Long attachmentId) {
        int retVal = attachmentService.deleteById(attachmentId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "根据HostId删除附件", notes = "根据HostId删除附件", httpMethod = "DELETE")
    @DeleteMapping("/delByHostId/{hostId}")
    public ReturnEntity delByHostId(@ApiParam(value = "附件HostId", required = true) @PathVariable Long hostId) {
        int retVal = attachmentService.deleteByHostId(hostId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "附件上传",notes = "附件上传",httpMethod = "POST")
    @PostMapping("/add")
    public int intelligentOperation(@RequestBody List<TabPbAttachment> pendingList, Long hostId, String attType){
          return attachmentService.intelligentOperation(pendingList,hostId,attType);
    }
}
