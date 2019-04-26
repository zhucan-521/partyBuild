package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 出国出境模块-查询条件
 * Created by FanYanGen on 2019/4/22 16:08
 */
@Data
@ApiModel("出国出境模块-查询条件")
public class AbroadQueryBean {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "范围")
    private Long orgRange;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

}
