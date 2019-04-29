package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("党员统计返回实体")
@Data
public class SysUserCountVO implements Serializable {
    @ApiModelProperty(value = "组织id")
    private Long deptId;

    @ApiModelProperty(value = "直管党员")
    private Long directPartyUser;

    @ApiModelProperty(value = "流动党员")
    private Long flowUser;

    @ApiModelProperty(value = "在职党员")
    private Long positiveUser;

    @ApiModelProperty(value = "班子成员")
    private Long leadTeamMemberUser;

    @ApiModelProperty(value = "困难党员")
    private Long hardshipUser;

    @ApiModelProperty(value = "退休党员")
    private Long retiredUser;
}
