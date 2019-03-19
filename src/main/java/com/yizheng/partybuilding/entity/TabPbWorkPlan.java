package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.commons.domain.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "工作计划实体")
public class TabPbWorkPlan {
    @ApiModelProperty(value = "计划ID")
    private Long planId;

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "计划年份 新增工作计划必传")
    private Long planYear;

    @ApiModelProperty(value = "上报日期", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reportDate;

    @ApiModelProperty(value = "总结日期", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date summaryDate;

    @ApiModelProperty(value = "计划审核日期", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planCheckDate;

    @ApiModelProperty(value = "总结审核日期", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date summaryCheckDate;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "计划内容 新增计划必传")
    private String planContent;

    @ApiModelProperty(value = "工作总结")
    private String planSummary;

    @ApiModelProperty(value = "计划审核情况")
    private String planCheck;

    @ApiModelProperty(value = "总结审核情况")
    private String summaryCheck;

    @ApiModelProperty(value = "计划审核机构")
    private Long checkOrg;

    @ApiModelProperty(value = "计划审核机构名称")
    private String checkOrgName;

    @ApiModelProperty(value = "计划审核结果 dict SHJG")
    @JsonSerialize(using = DictSerializer.class)
    private Long checkResult;

    @ApiModelProperty(value = "计划审核人")
    private Long checkUser;

    @ApiModelProperty(value = "计划审核人姓名")
    private String checkUserName;

    @ApiModelProperty(value = "总结审核机构")
    private Long summaryCheckOrg;

    @ApiModelProperty(value = "总结审核机构名称")
    private String summaryCheckOrgName;

    @ApiModelProperty(value = "总结审核结果 dict SHJG")
    @JsonSerialize(using = DictSerializer.class)
    private Long summaryCheckResult;

    @ApiModelProperty(value = "总结审核人")
    private Long summaryCheckUser;

    @ApiModelProperty(value = "总结审核人姓名")
    private String summaryCheckUserName;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    //==================

    @ApiModelProperty(value = "文档数")
    private Integer docNum;

    @ApiModelProperty(value = "图片数")
    private Integer imgNum;

    @ApiModelProperty(value = "视频数")
    private Integer videoNum;

    @ApiModelProperty(value = "计划附件实体集合")
    private List<TabPbAttachment> planAttachments;

    @ApiModelProperty(value = "总结附件实体集合")
    private List<TabPbAttachment> summaryAttachments;

    /**
     * 有效标记
     */
    @JsonIgnore
    private String eblFlag;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 排序码
     */
    @JsonIgnore
    private Long orderNum;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改用户Id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;

    /**
     * 版本
     */
    @JsonIgnore
    private Long version;

}