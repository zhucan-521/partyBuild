package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "信息上报收到实体")
@Data
public class TabPbMsgUpInfo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "向上报送组织id")
    private Long upDeptId;

    @ApiModelProperty(value = "向上报送组织")
    private String upDeptName;

    @ApiModelProperty(value = "报送人id")
    private Long upUserId;

    @JsonIgnore
    private Long upType;

    @ApiModelProperty(value = "报送人姓名")
    private String upUsername;

    @ApiModelProperty(value = "接收党组织id")
    @NotNull(message = "没有上级组织，无法上报")
    private Long recevieDeptId;

    @ApiModelProperty(value = "接收党组织name")
    private String recevieDeptName;

    @ApiModelProperty(value = "接收人id")
    private Long recevieUserId;

    @ApiModelProperty(value = "接收人姓名")
    private String recevieUsername;

    @ApiModelProperty(value = "报送信息标题")
    private String title;

    @ApiModelProperty(value = "上报日期（开始）")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date upTime;

    @ApiModelProperty(value = "主题、专题标签 码表ZTZT")
    @JsonSerialize(using = DictSerializer.class)
    private String titleLabel;

    @JsonIgnore
    private String delFlag;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @JsonIgnore
    private Long createUserid;

    @JsonIgnore
    private String createUsername;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

    @JsonIgnore
    private Long updateUserid;

    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "报送内容")
    private String content;

    @ApiModelProperty(value = "审核组织id")
    private Long auditDeptId;

    @ApiModelProperty(value = "审核组织")
    private String auditDeptName;

    @ApiModelProperty(value = "审核人id")
    private Long auditUserId;

    @ApiModelProperty(value = "审核人")
    private String auditUsername;

    @ApiModelProperty(value = "审核时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date auditTime;

    @ApiModelProperty(value = "审核说明")
    private String auditComment;

    @ApiModelProperty(value = "审核结果 码表SHJG")
    @JsonSerialize(using = DictSerializer.class)
    private Long auditAssess;

    @ApiModelProperty(value = "党组织名称")
    private String realDeptName;

    @ApiModelProperty(value = "党组织id")
    private Long realDeptId;

}