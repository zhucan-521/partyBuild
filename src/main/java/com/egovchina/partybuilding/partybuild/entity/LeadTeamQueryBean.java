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

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long rangeDeptId;

    @ApiModelProperty("班子届数")
    private Long sessionYear;

}
