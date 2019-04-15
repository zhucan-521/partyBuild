package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "题目答案得分dto")
@Data
public class TabPbEduSubjectAnswerDto {

    @ApiModelProperty(value = "题库ID")
    private Integer subjectId;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "题目分数")
    private Float score;
}
