package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("大工委查询实体")
public class StreetCommitteeQueryBean {
    @ApiModelProperty(value = "范围")
    private Long orgRange;

    @ApiModelProperty(value = "组织主键")
    private Long orgId;

    @ApiModelProperty(value = "成员名称")
    private String name;

    @ApiModelProperty(value = "职位")
    private String positiveName;

}