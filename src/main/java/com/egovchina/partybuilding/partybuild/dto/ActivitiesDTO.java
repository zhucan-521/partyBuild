package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "活动实体DTO", description = "活动实体DTO")
@Data
@Accessors(chain = true)
public class ActivitiesDTO implements Serializable {

    @ApiModelProperty(value = "活动ID")
    private Long activitiesId;

    @ApiModelProperty(value = "是否联点领导参加")
    private Byte inviteLinkLeader;

    @ApiModelProperty(value = "主题", required = true)
    @NotNull(message = "活动标题不能为空")
    private String subject;

}