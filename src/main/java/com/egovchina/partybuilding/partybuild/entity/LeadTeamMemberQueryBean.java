package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 领导班子成员查询实体
 *
 * @author Zhang Fan
 **/
@ApiModel("领导班子成员查询实体")
@Data
public class LeadTeamMemberQueryBean {

    @ApiModelProperty("列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty("组织Id")
    private Long rangeDeptId;

    @ApiModelProperty("身份证")
    private String idCardNo;

    @ApiModelProperty("姓名")
    private String personName;

    @ApiModelProperty("职务级别")
    private Long rank;

    @ApiModelProperty("职务")
    private Long positiveId;
}
