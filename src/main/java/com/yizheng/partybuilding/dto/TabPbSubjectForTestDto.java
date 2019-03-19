package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 试卷题目dto
 */
@ApiModel(value = "试卷题目实体")
@Data
public class TabPbSubjectForTestDto {

    @ApiModelProperty(value = "题库ID")
    private Integer id;
    @ApiModelProperty(value = "题目类别 数据字典59116 例如 语文数学之类的")
    private Integer category;
    @ApiModelProperty(value = "题目内容")
    private String title;
    @ApiModelProperty(value = "试题类型 单选1  多选2  简答3(现在只有单选)")
    private Integer type;
    @ApiModelProperty(value = "试题难度 (1简单 2一般  3困难)")
    private Integer difficulty;
    @ApiModelProperty(value = "选择题(多个用|隔开)")
    private String selects;
    @ApiModelProperty(value = "答案")
    private String answer;
    @ApiModelProperty(value = "答案解析")
    private String answerAnalysis;

    @ApiModelProperty(value = "试卷ID")
    private Integer testId;
    @ApiModelProperty(value = "题目分数")
    private Float score;
}
