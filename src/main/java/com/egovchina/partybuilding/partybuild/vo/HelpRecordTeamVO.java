package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "记录驻村帮扶队伍")
@Data
public class HelpRecordTeamVO {

    @ApiModelProperty(value = "记录id")
    private Long recordId;

    @ApiModelProperty(value = "队伍id")
    private Long teamId;

    @ApiModelProperty(value = "队伍id名称")
    private String teamIdName;

}
