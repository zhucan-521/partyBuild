package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@ApiModel(value = "党员培训情况实体",description = "党员培训情况")
@TableName("tab_pb_training")
public class TabPbTraining implements Serializable {

    @ApiModelProperty(value = "培训Id")
    private Long traningId;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty(value = "组织主键")
    private Long deptId;

    @ApiModelProperty(value = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date trainingStartDate;

    @ApiModelProperty(value = "结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date trainingFinishedDate;

    @ApiModelProperty(value = "培训地址")
    private String trainingPlace;

    @ApiModelProperty(value = "学时 码表KCSC")
    @JsonSerialize(using = DictSerializer.class)
    private Long trainingHours;

    @ApiModelProperty(value = "培训类别")
    private Long trainingType;

    @ApiModelProperty(value = "培训机构")
    private String trainingOrg;

    @ApiModelProperty(value = "考试成绩")
    private Long trainingScore;

    @ApiModelProperty(value = "填写意见时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date viewDate;

    @ApiModelProperty(value = "培训内容")
    private String trainingContent;

    @ApiModelProperty(value = "个人主要收获")
    private String trainingGain;

    @ApiModelProperty(value = "培训意见")
    private String trainingView;


    @JsonIgnore
    private String eblFlag;

    @JsonIgnore
    private String delFlag;

    @JsonIgnore
    private Long orderNum;

    @JsonIgnore
    private String description;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @JsonIgnore
    private Long createUserid;

    @JsonIgnore
    private String createUsername;

    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

    @JsonIgnore
    private Long updateUserid;

    @JsonIgnore
    private String updateUsername;

    @JsonIgnore
    private Long version;



}