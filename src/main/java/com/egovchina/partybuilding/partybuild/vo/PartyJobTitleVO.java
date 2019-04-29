package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "职业查询实体")
@Data
public class PartyJobTitleVO {
    @ApiModelProperty(value = "主键")
    private Long jobTitleId;

    @ApiModelProperty(value = "技术职务 dict JSZW")
    @JsonSerialize(using = DictSerializer.class)
    private Long post;

    @ApiModelProperty(value = "技术资格 dict JSZW")
    @JsonSerialize(using = DictSerializer.class)
    private Long qualifications;

    @ApiModelProperty(value = "获得日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date getDate;

    @ApiModelProperty(value = "聘任起始日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date appointStartDate;

    @ApiModelProperty(value = "聘任终止日期 yyyy-MM-dd")
    private Date appointEndDate;
}
