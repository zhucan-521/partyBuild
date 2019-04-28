package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "职业信息实体")
@Data
public class PartyJobTitleDTO {
    @ApiModelProperty(value = "主键")
    private Long jobTitleId;

    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "技术职务 dict JSZW")
    private Long post;

    @ApiModelProperty(value = "技术资格 dict JSZW")
    private Long qualifications;

    @ApiModelProperty(value = "获得日期 yyyy-MM-dd",example ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date getDate;

    @ApiModelProperty(value = "聘任起始日期 yyyy-MM-dd",example ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointStartDate;

    @ApiModelProperty(value = "聘任终止日期 yyyy-MM-dd",example ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointEndDate;
}
