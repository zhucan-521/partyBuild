package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "奖惩查询实体")
@Data
public class RewardsAndPunishmentsQueryBean {
    @ApiModelProperty(value = "用户名")
    private String realName;

    @ApiModelProperty(value = "查询范围")
    private String orgRange;

    @ApiModelProperty(value = "组织")
    private Long orgId;

    @ApiModelProperty(value = "用户id")
    private Long userId;
}
