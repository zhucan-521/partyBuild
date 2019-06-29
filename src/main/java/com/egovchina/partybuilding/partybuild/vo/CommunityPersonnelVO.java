package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Hu Fan
 **/
@Data
@ApiModel("社区活动人员VO")
public class CommunityPersonnelVO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String realName;

    @ApiModelProperty(value = "人员Id")
    private Long participantId;

    @ApiModelProperty(value = "缺席原因 1为因病因事缺席 2为无故缺席")
    private Long absentReason;

    @ApiModelProperty(value = "参加活动时所在组织id")
    private Long inOrgId;

}
