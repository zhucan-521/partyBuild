package com.egovchina.partybuilding.partybuild.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "接收下达文件通知党组织")
public class MsgNoticeDeptDTO  {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "下达通知id")
    private Long noticeId;

    @ApiModelProperty(value = "接收党组织id",required = true)
    private Long deptId;

    @ApiModelProperty(value = "接收党组织name",required = true)
    private String deptName;

    @ApiModelProperty(value = "签收人员")
    private Long recevieUserId;

    @ApiModelProperty(value = "签收人员姓名")
    private String recevieUsername;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "签收日期", example = "yyyy-MM-dd")
    private Date recevieTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd")
    private Date stateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间", example = "yyyy-MM-dd")
    private Date endTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "图片")
    private Long photo;

    @TableField(exist = false)
    @ApiModelProperty(value = "文档")
    private Long doc;


    @ApiModelProperty(value = "发布日期", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    @ApiModelProperty(value = "通知类别-dict，普通通知、会议通知、文件通知")
    private Long noticeType;

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

}