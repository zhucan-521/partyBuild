package com.egovchina.partybuilding.partybuild.dto;


import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel(value = "驻村帮扶队伍")
@Data
public class HelpTeamDTO {

    @ApiModelProperty(value = "主键")
    @NotNull(message = "请附带id", groups = Update.class)
    private Long teamId;

    @ApiModelProperty(value = "队伍名称", required = true)
    @NotNull(message = "队伍名称不能为空")
    private String teamName;

    @ApiModelProperty(value = "队长")
    private Long teamLeader;

    @ApiModelProperty(value = "情况说明")
    private String situationStatement;

    @ApiModelProperty(value = "党组织id", required = true)
    @NotNull(message = "党组织id不能为空")
    private Long orgId;

    @ApiModelProperty(value = "党组织名称")
    private String orgName;

    @ApiModelProperty(value = "成立日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date setDate;

    @ApiModelProperty(value = "帮扶队伍成员集合")
    List<HelpTeamMemberDTO> helpTeamMemberDTOS;

    @ApiModelProperty(value = "附件集合")
    private List<TabPbAttachment> attachments;

}