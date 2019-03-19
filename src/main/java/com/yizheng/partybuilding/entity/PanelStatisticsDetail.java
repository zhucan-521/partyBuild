package com.yizheng.partybuilding.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 面板统计详情实体
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "面板统计详情实体")
@Data
public class PanelStatisticsDetail {

    @ApiModelProperty(value = "上级党组织名称")
    private String parentOrgName;

    @ApiModelProperty(value = "党组织名称")
    private String orgName;

    @ApiModelProperty(value = "实际值")
    private Long actual;
}
