package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 入党申请-查询条件
 * Created by FanYanGen on 2019/4/23 16:48
 */
@Data
@ApiModel("入党申请查询条件")
public class PartyApplyQueryBean {

    @ApiModelProperty("发展ID")
    private Long dpId;

    @ApiModelProperty("状态")
    private Long status;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty(value = "组织ID", required = true)
    private Long orgId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("身份证号")
    private String idCardNo;

    @ApiModelProperty("组织范围")
    private Long orgRange;

}
