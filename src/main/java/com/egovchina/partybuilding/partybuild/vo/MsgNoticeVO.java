package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.partybuild.entity.TabPbMsgNoticeDept;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "文件通知视图")
@Data
public class MsgNoticeVO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "发布党组织名称id")
    private Long deptId;

    @ApiModelProperty(value = "发布党组织名称")
    private String deptName;

    @ApiModelProperty(value = "通知类别-dict，普通通知、会议通知、文件通知")
    @JsonSerialize(using = DictSerializer.class)
    private Long noticeType;

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

    @ApiModelProperty(value = "通知内容")
    private String noticeContent;

    @ApiModelProperty(value = "发布状态，0.未发布、1.已发布(发布后可以取消)")
    private String state;

    @ApiModelProperty(value = "发布日期", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishTime;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

    @ApiModelProperty(value = "组织list")
    private List<TabPbMsgNoticeDept> noticeDeptList;

    @ApiModelProperty(value = "签收情况")
    private String signingSituation;

    @ApiModelProperty(value = "图片")
    private Long photo;

    @ApiModelProperty(value = "文档")
    private Long doc;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd")
    private Date stateTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间", example = "yyyy-MM-dd")
    private Date endTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", example = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "发布人Id")
    private Long publisherId;

    @ApiModelProperty(value = "发布人姓名")
    private String publisherName;

}
