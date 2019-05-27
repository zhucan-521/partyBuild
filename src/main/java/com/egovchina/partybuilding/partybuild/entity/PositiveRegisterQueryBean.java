package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(value = "党员报到查询实体")
@Data
public class PositiveRegisterQueryBean {

    @ApiModelProperty(value = "社区id", required = true)
    @NotNull(message = "社区id不能为空")
    private Long communityId;

    @ApiModelProperty(value = "党员姓名")
    private String realname;

    @ApiModelProperty(value = "状态 1为已报到，2为已返回")
    private Byte revokeTag;

    @ApiModelProperty(value = "报到日期-开始 yyyy-MM-dd", example = "2019-01-01")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "报到时间-开始 格式有误")
    private String registDateBegin;

    @ApiModelProperty(value = "报到日期-截止 yyyy-MM-dd", example = "2020-01-01")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "报到时间-截止 格式有误")
    private String registDateEnd;

}
