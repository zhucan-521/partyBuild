package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "驻村帮扶队伍成员")
@Data
public class HelpTeamMemberVO {

    @ApiModelProperty(value = "主键")
    private Long memberId;

    @ApiModelProperty(value = "队伍主键")
    private Long teamId;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String username;

}