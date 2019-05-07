package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 党小组模块-查询条件
 * Created by FanYanGen on 2019/4/28 10:43
 */
@Data
@ApiModel("党小组模块-查询条件")
public class PartyGroupQueryBean {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "组织范围  1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织）", required = true)
    @NotNull(message = "组织范围不能为空")
    private Long orgRange;

    @ApiModelProperty(value = "党小组名称")
    private String groupName;

    @ApiModelProperty(value = "组长名称")
    private String leaderName;

    @ApiModelProperty(value = "是否撤销")
    private Integer isRevoke;

}
