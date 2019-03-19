package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @Author Zhang Fan
 **/
@Data
@ApiModel(value = "结对共建党员dto实体")
public class AssessUserDto {

    @ApiModelProperty(value = "党员ID")
    private Long userId;

    @ApiModelProperty(value = "党员名称")
    private String username;
}
