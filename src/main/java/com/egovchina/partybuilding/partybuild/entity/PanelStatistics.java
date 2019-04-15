package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 面板统计实体
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "面板统计实体")
@Data
public class PanelStatistics {

    @ApiModelProperty(value = "任务")
    @JsonSerialize(using = DictSerializer.class)
    private Long taskId;

    @ApiModelProperty(value = "预期值")
    private Long expected;

    @ApiModelProperty(value = "实际值")
    private Long actual;

    @ApiModelProperty(value = "比率")
    private Double rate;

    @ApiModelProperty(value = "支部面板项跳转锚点")
    private String anchor;

}
