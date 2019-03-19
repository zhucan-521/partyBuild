package com.yizheng.partybuilding.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "考生回答试卷实体")
@Data
public class TabPbEduAnswer {

    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "试卷id")
    private Integer testId;
    @ApiModelProperty(value = "考生-党员Id")
    private Long userId;
    @ApiModelProperty(value = "考试时间Id")
    private Long arrangeId;
    @ApiModelProperty(value = "状态(是否出成绩 0为 没有  1为已经完成)")
    private Integer state;
    @ApiModelProperty(value = "分数")
    private Float score;
    @ApiModelProperty(value = "提交试卷时间", hidden = true)
    private Date createTime;
}