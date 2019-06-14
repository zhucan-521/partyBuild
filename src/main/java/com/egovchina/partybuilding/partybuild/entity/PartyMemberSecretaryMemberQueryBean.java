package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@ApiModel("书记列表查询参数")
@Accessors(chain = true)
@Data
public class PartyMemberSecretaryMemberQueryBean {

    @ApiModelProperty(value = "名字")
    private String realname;

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织id不能为空")
    private Long rangeDeptId;

    @ApiModelProperty(value = "党内职务 dict DNZW")
    private Long positiveName;

}
