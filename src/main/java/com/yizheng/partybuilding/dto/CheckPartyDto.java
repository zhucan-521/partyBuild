package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/14
 */
@Data
@Accessors(chain = true)
@ApiModel("检查党员能否补录")
public class CheckPartyDto {
    @ApiModelProperty(name = "提示信息, 比如党员已存在等等")
    private String message;
    @ApiModelProperty(name = "党员userId, 如果该党员存在, 则返回userId, 否则为null")
    private Long userId;
    @ApiModelProperty(name = "返回是否能够补录结果, true/false")
    private Boolean result;
    @ApiModelProperty(name = "组织id")
    private Long deptId;
}
