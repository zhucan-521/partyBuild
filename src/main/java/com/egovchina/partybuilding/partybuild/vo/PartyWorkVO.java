package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "工作信息返回实体")
@Data
public class PartyWorkVO {
    @ApiModelProperty(value = "主键")
    private Long workId;

    @ApiModelProperty(value = "工作单位")
    private String unit;

    @ApiModelProperty(value = "岗位")
    private String post;

    @ApiModelProperty(value = "一线情况 dict YXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long frontLine;

    @ApiModelProperty(value = "新阶层类型 dict NEWCLASS")
    @JsonSerialize(using = DictSerializer.class)
    private Long stratum;

    @ApiModelProperty(value = "开始时间 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @ApiModelProperty(value = "结束时间 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
}
