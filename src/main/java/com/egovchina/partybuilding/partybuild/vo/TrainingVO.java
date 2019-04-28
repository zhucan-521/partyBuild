package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TrainingVO {

    @ApiModelProperty(value = "培训Id")
    private Long traningId;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty(value = "组织主键")
    private Long deptId;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date trainingStartDate;

    @ApiModelProperty(value = "结束日期")
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date viewDate;

    @ApiModelProperty(value = "培训内容")
    private String trainingContent;

    @ApiModelProperty(value = "个人主要收获")
    private String trainingGain;

    @ApiModelProperty(value = "培训意见")
    private String trainingView;


}
