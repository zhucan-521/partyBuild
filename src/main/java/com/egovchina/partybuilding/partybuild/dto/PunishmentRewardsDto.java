package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YangYingXiang on 2019/03/01
 */
@Data
public class PunishmentRewardsDto implements Serializable {

    @ApiModelProperty(value = "时间")
    private Date startingTime;

    @ApiModelProperty(value = "奖惩名称")
    private String name;


}
