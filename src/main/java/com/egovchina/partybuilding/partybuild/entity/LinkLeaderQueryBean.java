package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 联点领导查询实体
 * @author: WuYunJie
 * @create: 2019-06-14 14:52
 **/
@ApiModel("联点领导查询实体")
@Data
public class LinkLeaderQueryBean {

    @ApiModelProperty("组织范围 1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；其他值 查本组织")
    private String orgRange = "";

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long rangeDeptId;

    @ApiModelProperty(value = "连接领导姓名")
    private String realName;

    @ApiModelProperty(value = "组织名称")
    private String name;

}
