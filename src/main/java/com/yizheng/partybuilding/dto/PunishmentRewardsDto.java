package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author YangYingXiang on 2019/03/01
 */
public class PunishmentRewardsDto {

    @ApiModelProperty(value = "时间")
    private Date startingTime;

    @ApiModelProperty(value = "奖惩名称")
    private String name;


}
