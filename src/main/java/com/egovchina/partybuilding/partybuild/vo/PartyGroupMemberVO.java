package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 党小组成员-视图对象
 * Created by FanYanGen on 2019/4/29 15:48
 */
@Data
@ApiModel("党小组成员-视图对象")
public class PartyGroupMemberVO {

    @ApiModelProperty(value = "主键")
    private Long memberId;

    @ApiModelProperty(value = "党员ID")
    private Long userId;

    @ApiModelProperty(value = "党员名称")
    private String username;

    @ApiModelProperty(value = "是否是组长 0-否 1-是")
    private Integer isLeader;

}
