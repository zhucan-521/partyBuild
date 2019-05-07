package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "接收下达文件通知党组织")
public class MsgNoticeDeptVO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "下达通知id")
    private Long noticeId;

    @ApiModelProperty(value = "接收党组织id")
    private Long deptId;

    @ApiModelProperty(value = "接收党组织name")
    private String deptName;

    @ApiModelProperty(value = "签收人员")
    private Long recevieUserId;

    @ApiModelProperty(value = "签收人员姓名")
    private String recevieUsername;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "签收日期", example = "yyyy-MM-dd")
    private Date recevieTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd")
    private Date stateTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间", example = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织")
    private Long orgRange;

    @ApiModelProperty(value = "图片")
    private Long photo;

    @ApiModelProperty(value = "文档")
    private Long doc;

    @ApiModelProperty(value = "发布日期", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishTime;

    @ApiModelProperty(value = "通知类别-dict，普通通知、会议通知、文件通知")
    @JsonSerialize(using = DictSerializer.class)
    private Long noticeType;

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

}