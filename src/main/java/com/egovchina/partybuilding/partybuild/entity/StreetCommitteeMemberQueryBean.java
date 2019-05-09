package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("大工委成员查询实体")
public class StreetCommitteeMemberQueryBean {
    @ApiModelProperty(value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private Long orgRange;

    @ApiModelProperty(value = "组织主键", required = true)
    @NotNull(message = "组织主键不能为空")
    private Long orgId;

    @ApiModelProperty(value = "成员名称")
    private String personName;

    @ApiModelProperty(value = "职位")
    private String positiveName;

}