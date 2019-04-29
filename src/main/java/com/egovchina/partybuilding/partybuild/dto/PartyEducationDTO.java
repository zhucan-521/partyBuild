package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "学历信息实体")
@Data
public class PartyEducationDTO {
    @ApiModelProperty(value = "主键")
    private Long educationId;


    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "学历 dict XL")
    private Long level;

    private String levelName;
    @ApiModelProperty(value = "学位 dict ")
    private Long degree;

    @ApiModelProperty(value = "毕业院校")
    private String graduatedSchool;

    @ApiModelProperty(value = "专业")
    private String spec;
}
