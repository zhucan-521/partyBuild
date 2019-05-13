package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 组织调整查询实体
 * @author: WuYunJie
 * @create: 2019-05-11 21:38
 **/
@ApiModel(value = "组织调整查询实体")
@Data
public class OrgChangeQueryBean {
    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private Long orgRange;

    @ApiModelProperty("变动类型")
    private Long changeType;
}
