package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 试卷发布实体
 */
@ApiModel(value = "试卷发布实体")
@Data
public class TabPbEduTestPublishDto {

    @ApiModelProperty(value = "试卷ID")
    private Integer id;

    @ApiModelProperty(value = "发布状态，发布true，取消发布false")
    private Boolean publishFlag;
}
