package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "党员考试成绩实体")
@Data
public class TabPbEduUserTestInfoDto {

    @ApiModelProperty(value = "考试答卷id")
    private Integer id;//answerId

    @ApiModelProperty(value = "试卷id")
    private Integer testId;

    @ApiModelProperty(value = "考生-党员Id")
    private Long userId;

    @ApiModelProperty(value = "分数")
    private Float score;

    @ApiModelProperty(value = "状态(是否出成绩 0为 没有  1为已经完成)")
    private Integer state;

    @ApiModelProperty(value = "考试开始时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date beginTime;

    @ApiModelProperty(value = "考试结束时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "试卷类型-dict，与题目类型是一个字典")
    private Integer category;

    @ApiModelProperty(value = "试卷难度 (1简单 2一般  3困难)")
    private Integer difficulty;

    @ApiModelProperty(value = "试卷名称")
    private String title;

    @ApiModelProperty(value = "试卷总分")
    private Integer totalScore;

    @ApiModelProperty(value = "考试时长")
    private Integer duration;
}
