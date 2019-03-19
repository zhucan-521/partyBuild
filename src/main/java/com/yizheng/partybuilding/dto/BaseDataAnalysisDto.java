package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础统计分析dto
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "基础统计分析dto")
@Data
public class BaseDataAnalysisDto<T> implements Serializable {

    @ApiModelProperty(value = "展示名")
    private String label;

    @ApiModelProperty(value = "对应数据")
    private T datas;
}
