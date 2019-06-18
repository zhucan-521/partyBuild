package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "驻村帮扶记录")
@Data
public class HelpRecordVO {

    @ApiModelProperty(value = "主键")
    private Long recordId;

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "记录日趋 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recordDate;

    @ApiModelProperty(value = "记录标题")
    private String recordTitle;

    @ApiModelProperty(value = "记录内容")
    private String content;

    @ApiModelProperty(value = "记录队伍")
    List<HelpRecordTeamVO> helpRecordTeams;

}