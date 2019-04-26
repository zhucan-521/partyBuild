package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty(value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private Long orgRange;

    @ApiModelProperty(value = "组织范围ID", required = true)
    @NotNull(message = "组织范围ID不能为空")
    private Long rangeDeptId;

    @ApiModelProperty("生活困难类型 dict KNLX")
    @JsonSerialize(using = DictSerializer.class)
    private String hardshipType;

}
