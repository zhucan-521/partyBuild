package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "社区")
public class CommunityDTO {


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "社区名称")
    private String label;

}
