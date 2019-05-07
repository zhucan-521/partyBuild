package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 党员基本信息-视图对象
 * Created by FanYanGen on 2019/5/6 15:53
 */
@Data
@ApiModel("党员基本信息-视图对象")
public class PartyMemberBaseVO {

    @ApiModelProperty(value = "党员ID")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String realName;

}
