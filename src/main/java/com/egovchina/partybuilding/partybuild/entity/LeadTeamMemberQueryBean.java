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

    @ApiModelProperty("班子届数")
    private Long sessionYear;

    @ApiModelProperty("组织Id")
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
