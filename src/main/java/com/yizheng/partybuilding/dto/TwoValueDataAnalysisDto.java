package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 两个值的分析实体
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "两个值的分析dto实体")
@Data
public class TwoValueDataAnalysisDto {

    @ApiModelProperty(value = "展示名")
    private String label;

    @ApiModelProperty(value = "左值")
    private Long leftValue;

    @ApiModelProperty(value = "右值")
    private Long rightValue;

}
