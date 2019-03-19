package com.yizheng.partybuilding.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "试卷-题库关联实体")
@Data
public class TabPbEduSubjectfortest {

    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "试卷ID")
    private Integer testId;
    @ApiModelProperty(value = "题目ID")
    private Integer subjectId;
    @ApiModelProperty(value = "分数")
    private Float score;

}