package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 工作总结VO
 *
 * @author Zhang Fan
 **/
@ApiModel("工作总结VO")
@Data
public class WorkSummaryVO {

    @ApiModelProperty(value = "计划ID")
    private Long planId;

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "计划年份")
    private Long planYear;

    @ApiModelProperty(value = "总结日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date summaryDate;

    @ApiModelProperty(value = "总结审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date summaryCheckDate;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "工作总结")
    private String planSummary;

    @ApiModelProperty(value = "总结审核情况")
    private String summaryCheck;

    @ApiModelProperty(value = "总结审核机构")
    private Long summaryCheckOrg;

    @ApiModelProperty(value = "总结审核机构名称")
    private String summaryCheckOrgName;

    @ApiModelProperty(value = "总结审核结果")
    @JsonSerialize(using = DictSerializer.class)
    private Long summaryCheckResult;

    @ApiModelProperty(value = "总结审核人")
    private Long summaryCheckUser;

    @ApiModelProperty(value = "总结审核人姓名")
    private String summaryCheckUserName;

    @ApiModelProperty(value = "总结附件实体集合")
    private List<TabPbAttachment> attachments;

    @ApiModelProperty(value = "文档数")
    private Integer docNum;

    @ApiModelProperty(value = "图片数")
    private Integer imgNum;

    @ApiModelProperty(value = "视频数")
    private Integer videoNum;
}
