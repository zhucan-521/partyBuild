package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 领导班子查询bean
 *
 * @author Zhang Fan
 **/
@ApiModel("领导班子查询实体")
@Data
public class LeadTeamQueryBean {

    @ApiModelProperty("列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long rangeDeptId;

    @ApiModelProperty("姓名")
    private String personName;

    @ApiModelProperty("身份证")
    private String idCardNo;

    @ApiModelProperty("组织名称")
    private String orgName;

}
