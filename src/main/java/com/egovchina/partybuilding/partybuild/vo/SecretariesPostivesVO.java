package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SecretariesPostivesVO {
    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "党内职务 dict DNZW")
    private Long positiveName;
}
