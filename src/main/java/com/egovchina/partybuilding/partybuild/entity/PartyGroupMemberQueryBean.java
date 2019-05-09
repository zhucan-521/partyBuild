package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 党小组人员模块-查询条件
 * Created by FanYanGen on 2019/5/8 11:26
 */
@Data
@ApiModel("党小组人员模块-查询条件")
public class PartyGroupMemberQueryBean {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long deptId;

    @ApiModelProperty(value = "党小组ID")
    private Long groupId;

}
