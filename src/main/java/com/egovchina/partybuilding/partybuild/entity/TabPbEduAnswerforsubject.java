package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "试卷题目答案得分实体")
@Data
public class TabPbEduAnswerforsubject {

    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "考生试卷答案ID")
    private Integer answerId;
    @ApiModelProperty(value = "题目ID")
    private Integer subjectId;
    @ApiModelProperty(value = "学生答案")
    private String answerContent;
    @ApiModelProperty(value = "试题得分")
    private Float answerScore;

}