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

    @ApiModelProperty("联点组织id，添加联点领导时排除当前组织已经联点的领导")
    private Long jointPointOrgId;

}
