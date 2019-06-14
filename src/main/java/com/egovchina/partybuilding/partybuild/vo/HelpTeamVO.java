package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "驻村帮扶队伍")
@Data
public class HelpTeamVO {

    @ApiModelProperty(value = "主键")
    private Long teamId;

    @ApiModelProperty(value = "队伍名称")
    private String teamName;

    @ApiModelProperty(value = "党组织id")
    private Long orgId;

    @ApiModelProperty(value = "党组织名称")
    private String orgName;

    @ApiModelProperty(value = "成立日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date setDate;

    @ApiModelProperty(value = "人数")
    private Long peopleNumble;

    @ApiModelProperty(value = "帮扶队伍成员集合")
    List<HelpTeamMemberVO> helpTeamMemberDTOS;

}