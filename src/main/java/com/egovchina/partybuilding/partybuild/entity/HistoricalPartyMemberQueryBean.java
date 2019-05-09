package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@ApiModel(value = "历史党员查询实体")
@Data
@Accessors(chain = true)
public class HistoricalPartyMemberQueryBean {
    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织不能为空")
    private Long deptId;

    @ApiModelProperty("用户名/姓名")
    private String realName;

    @ApiModelProperty("减少方式 字典")
    private Long outType;

    @ApiModelProperty(value = "出党方式 字典")
    private Long quitType;

    @ApiModelProperty("列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；不传查本级")
    private String orgRange;
}
