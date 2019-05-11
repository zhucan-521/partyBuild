package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 困难党员模块-查询条件
 * Created by FanYanGen on 2019/4/22 17:16
 */
@Data
@ApiModel("困难党员模块-查询条件")
public class HardshipQueryBean {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private Long orgRange;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("生活困难类型")
    private String hardshipType;

}
