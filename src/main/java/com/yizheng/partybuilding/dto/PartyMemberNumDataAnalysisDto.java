package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 党员数据发展趋势数据分析dto
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "党员数据发展趋势数据分析dto")
@Data
public class PartyMemberNumDataAnalysisDto {

    @ApiModelProperty(value = "总数量")
    private Long count;

    @ApiModelProperty(value = "同比去年增长比率")
    private Double rate;

    @ApiModelProperty(value = "按月统计的数据")
    private List<TwoValueDataAnalysisDto> datas;
}
