package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "驻村帮扶队伍成员")
@Data
public class HelpTeamMemberDTO {

    @ApiModelProperty(value = "主键")
    private Long memberId;

    @ApiModelProperty(value = "队伍主键", required = true)
    @NotNull(message = "队伍主键不能为空")
    private Long teamId;

    @ApiModelProperty(value = "用户Id", required = true)
    @NotNull(message = "用户Id不能为空")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String username;

}