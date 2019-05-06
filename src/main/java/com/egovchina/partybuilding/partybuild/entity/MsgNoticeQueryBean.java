package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MsgNoticeQueryBean {

    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织)")
    private Long orgRange;

    @ApiModelProperty(value = "发布党组织名称id", required = true)
    private Long deptId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd")
    private Date stateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间", example = "yyyy-MM-dd")
    private Date endTime;


    @ApiModelProperty(value = "通知类别-dict，普通通知、会议通知、文件通知")
    private Long noticeType;

    @ApiModelProperty(value = "发布状态，0.未发布、1.已发布(发布后可以取消)")
    private String state;

}
