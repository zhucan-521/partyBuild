package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "职务VO")
@Data
public class PositivesVO {

    @ApiModelProperty(value = "职务名称，字典：党内职务（DNZW）；行政职务（HZZW）")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveName;

    @ApiModelProperty(value = "职务名称中文")
    private String positiveNameDict;
}