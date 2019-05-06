package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SecretaryMemberQueryBean {


    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "名字")
    private String realname;

    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织")
    private String orgRange;

    @ApiModelProperty("组织ID")
    private Long rangeDeptId;

    @ApiModelProperty(value = "职务，手动录入：如：中共长沙市委基层党建工作领导小组办公室常务副主任")
    private String postive;


}
