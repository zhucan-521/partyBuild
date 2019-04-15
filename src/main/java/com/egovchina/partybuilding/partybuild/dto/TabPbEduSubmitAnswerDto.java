package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "考生考试提交试卷dto")
@Data
public class TabPbEduSubmitAnswerDto {

    @ApiModelProperty(value = "考试时间Id")
    private int arrangeId;

    @ApiModelProperty(value = "试卷题目答案，题目id与答案集合，如：1&A,2&B")
    private String answerContent;

}
