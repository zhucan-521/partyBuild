package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.PartyResourceDto;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.service.inf.ITabPbLearnManagerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/10
 */
@Api(tags = "党员教育-资源管理 杨颖翔")
@RestController
@RequestMapping("/partyLearn")
public class PartyLearnManagerController {

    @Autowired
    private ITabPbLearnManagerService service;

    @GetMapping
    @ApiOperation(value = "学习管理附件信息查询", notes = "attachmentType的值, 13:课程管理，14:直播管理，15:阵地管理，16:师资管理，17:重点培训")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "附件名", name = "attachmentDescription", dataType = "String"),
            @ApiImplicitParam(value = "附件类型", name = "attachmentType", dataType = "Long"),
            @ApiImplicitParam(value = "附件文件类型-数据字典", name = "attachmentFileType", dataType = "Long"),
    })
    public PageInfo<PartyResourceDto> find(String attachmentDescription, Long attachmentType, Long attachmentFileType, @ApiParam Page page) {
        TabPbAttachment attr = new TabPbAttachment();
        attr.setAttachmentDescription(attachmentDescription);
        attr.setAttachmentFileType(attachmentFileType);
        attr.setAttachmentType(attachmentType);
        return this.service.find(attr, page);
    }

}



