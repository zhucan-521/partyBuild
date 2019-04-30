package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class FlowInMemberQueryBean {


    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;


    @ApiModelProperty(value = "列表范围 0查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty(value = "组织ID")
    private Long rangeDeptId;


    @ApiModelProperty(value = "用户姓名")
    private String username;


    @ApiModelProperty(value = "流入状态 码表值LDGC")
    private Long flowInState;



}
