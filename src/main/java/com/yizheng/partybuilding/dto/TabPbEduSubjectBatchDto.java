package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 批量新增试题实体
 */
@Data
@ApiModel(value = "批量新增试题实体")
public class TabPbEduSubjectBatchDto {

    @ApiModelProperty(value = "多个题目的所有内容")
    private String subjects;
    @ApiModelProperty(value = "题目类别，数据字典59116")
    private int category;
    @ApiModelProperty(value = "试题类型：单选1  多选2  简答3（默认1）")
    private int type;
    @ApiModelProperty(value = "试题难度：1简单 2一般  3困难")
    private int difficulty;

}
