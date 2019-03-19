package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "考生考试时间实体")
@Data
public class TabPbEduTestarrange {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "试卷ID")
    private Integer testId;

    @ApiModelProperty(value = "党员ID")
    private Long userId;

    @ApiModelProperty(value = "考试开始时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date beginTime;

    @ApiModelProperty(value = "考试结束时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date endTime;
}