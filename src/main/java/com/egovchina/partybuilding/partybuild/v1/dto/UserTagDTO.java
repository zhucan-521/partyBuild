package com.egovchina.partybuilding.partybuild.v1.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author create by GuanYingxin on 2019/4/22 20:40
 * @description
 */
@ApiModel("党员标记")
@Data
@Accessors(chain = true)
public class UserTagDTO {

    @ApiModelProperty("用户ID")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "用户标签字典Id 码表值 USERTAG")
    @JsonSerialize(using = DictSerializer.class)
    @NotNull
    private Long tagType;
}
