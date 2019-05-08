package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 领导班子成员查询实体
 *
 * @author Zhang Fan
 **/
@ApiModel("领导班子成员查询对象")
@Data
public class LeadTeamMemberQueryBean {

    @ApiModelProperty("列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty("班子届数")
    private Long sessionYear;

    @ApiModelProperty(value = "组织Id", required = true)
    @NotNull(message = "组织Id不能为空")
    private Long rangeDeptId;

    @ApiModelProperty("身份证")
    private String idCardNo;

    @ApiModelProperty("姓名")
    private String personName;

    @ApiModelProperty("职务")
    private Long positiveId;

    @ApiModelProperty("状态")
    private String eblFlag;

}
