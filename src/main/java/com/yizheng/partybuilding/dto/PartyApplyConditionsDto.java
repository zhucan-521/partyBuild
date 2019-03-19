package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2019/01/24
 */

@ApiModel("入党申请查询条件")
@Data
@Accessors(chain = true)
public class PartyApplyConditionsDto {

    @ApiModelProperty("发展步骤id")
    private Long dpId;
    @ApiModelProperty("发展状态")
    private Long status;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("组织id")
    private Long orgId;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("身份证号")
    private String idCardNo;
    @ApiModelProperty("范围")
    private Long orgRange;
}
