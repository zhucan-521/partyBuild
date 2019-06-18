package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel(value = "驻村帮扶记录")
@Data
public class HelpRecordDTO {

    @ApiModelProperty(value = "主键")
    @NotNull(message = "recordId不能为空", groups = Update.class)
    private Long recordId;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "orgId不能为空")
    private Long orgId;

    @ApiModelProperty(value = "记录日趋 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

    @ApiModelProperty(value = "记录标题", required = true)
    @NotNull(message = "记录标题不能为空")
    private String recordTitle;

    @ApiModelProperty(value = "记录内容")
    private String content;

    @ApiModelProperty(value = "记录队伍")
    List<HelpRecordTeamDTO> helpRecordTeams;

}