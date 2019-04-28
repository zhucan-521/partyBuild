package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "工作信息实体")
@Data
public class PartyWorkDTO {
    @ApiModelProperty(value = "主键")
    private Long workId;

    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "工作单位")
    private String unit;

    @ApiModelProperty(value = "岗位")
    private String post;

    @ApiModelProperty(value = "一线情况 dict YXQK")
    private Long frontLine;

    @ApiModelProperty(value = "新阶层类型 dict NEWCLASS")
    private Long stratum;

    @ApiModelProperty(value = "开始时间 yyyy-MM-dd",example ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty(value = "结束时间 yyyy-MM-dd",example ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
