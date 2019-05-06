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
 * 工作计划VO
 *
 * @author Zhang Fan
 **/
@ApiModel("工作计划VO实体")
@Data
public class WorkPlanVO {

    @ApiModelProperty(value = "计划ID")
    private Long planId;

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "计划年份")
    private String planYear;

    @ApiModelProperty(value = "上报日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportDate;

    @ApiModelProperty(value = "计划审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planCheckDate;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "计划内容")
    private String planContent;

    @ApiModelProperty(value = "计划审核情况")
    private String planCheck;

    @ApiModelProperty(value = "计划审核机构")
    private Long checkOrg;

    @ApiModelProperty(value = "计划审核机构名称")
    private String checkOrgName;

    @ApiModelProperty(value = "计划审核结果")
    @JsonSerialize(using = DictSerializer.class)
    private Long checkResult;

    @ApiModelProperty(value = "计划审核人")
    private Long checkUser;

    @ApiModelProperty(value = "计划审核人姓名")
    private String checkUserName;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

    @ApiModelProperty(value = "文档数")
    private Integer docNum;

    @ApiModelProperty(value = "图片数")
    private Integer imgNum;

    @ApiModelProperty(value = "视频数")
    private Integer videoNum;

}
