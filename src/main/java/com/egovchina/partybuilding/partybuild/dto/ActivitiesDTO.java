package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@ApiModel(value = "联点活动DTO", description = "联点活动DTO")
@Data
@Accessors(chain = true)
public class ActivitiesDTO {

    @ApiModelProperty(value = "活动ID")
    private Long activitiesId;

    @ApiModelProperty(value = "是否联点领导参加")
    private Byte inviteLinkLeader;

    @ApiModelProperty(value = "主题", required = true)
    @NotNull(message = "活动标题不能为空")
    private String subject;

}