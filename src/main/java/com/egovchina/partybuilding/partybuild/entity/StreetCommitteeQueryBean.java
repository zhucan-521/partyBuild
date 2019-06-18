package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("大工委查询实体")
public class StreetCommitteeQueryBean {
    @ApiModelProperty(value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private Long orgRange;

    @ApiModelProperty(value = "组织主键")
    private Long orgId;

    @ApiModelProperty(value = "年届")
    private Long sessionYear;

}