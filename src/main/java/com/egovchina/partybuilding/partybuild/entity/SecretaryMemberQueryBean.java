package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("书记查询条件")
public class SecretaryMemberQueryBean {

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "名字")
    private String realname;

    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织")
    private String orgRange;

    @ApiModelProperty("组织ID")
    private Long rangeDeptId;

    @ApiModelProperty(value = "党内职务 dict DNZW")
    private Long positiveName;

}