package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@Data
public class TabPbWorkPlan {
    /**
     * 计划ID
     */
    private Long planId;

    /**
     * 组织ID
     */
    private Long orgId;

    /**
     * 计划年份 新增工作计划必传
     */
    private String planYear;

    /**
     * 上报日期", example = "yyyy-MM-dd
     */
    private Date reportDate;

    /**
     * 总结日期", example = "yyyy-MM-dd
     */
    private Date summaryDate;

    /**
     * 计划审核日期", example = "yyyy-MM-dd
     */
    private Date planCheckDate;

    /**
     * 总结审核日期", example = "yyyy-MM-dd
     */
    private Date summaryCheckDate;

    /**
     * 数据描述
     */
    private String description;

    /**
     * 计划内容 新增计划必传
     */
    private String planContent;

    /**
     * 工作总结
     */
    private String planSummary;

    /**
     * 计划审核情况
     */
    private String planCheck;

    /**
     * 总结审核情况
     */
    private String summaryCheck;

    /**
     * 计划审核机构
     */
    private Long checkOrg;

    /**
     * 计划审核机构名称
     */
    private String checkOrgName;

    /**
     * 计划审核结果 dict SHJG
     */
    private Long checkResult;

    /**
     * 计划审核人
     */
    private Long checkUser;

    /**
     * 计划审核人姓名
     */
    private String checkUserName;

    /**
     * 总结审核机构
     */
    private Long summaryCheckOrg;

    /**
     * 总结审核机构名称
     */
    private String summaryCheckOrgName;

    /**
     * 总结审核结果 dict SHJG
     */
    @JsonSerialize(using = DictSerializer.class)
    private Long summaryCheckResult;

    /**
     * 总结审核人
     */
    private Long summaryCheckUser;

    /**
     * 总结审核人姓名
     */
    private String summaryCheckUserName;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 有效标记
     */
    private String eblFlag;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 排序码
     */
    private Long orderNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户Id
     */
    private Long createUserid;

    /**
     * 创建人姓名
     */
    private String createUsername;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改用户Id
     */
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    private String updateUsername;

    /**
     * 版本
     */
    private Long version;
}