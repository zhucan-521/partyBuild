package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "活动实体", description = "活动实体")
@Data
@Accessors(chain = true)
public class TabPbActivities implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动ID")
    private Long activitiesId;

    @ApiModelProperty(value = "组织主键", required = true)
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "活动类型")
    @NotNull(message = "活动类型不能为空")
    @JsonSerialize(using = DictSerializer.class)
    private Long activitiesType;

    @ApiModelProperty(value = "活动包含的类型，可能有多个类型，没有多个就是活动来源本身，多个用,分隔")
    @JsonSerialize(using = DictSerializer.class)
    private String activitiesTypeIds;

    @ApiModelProperty(value = "是否结对共建")
    private Byte invitePartener;

    @ApiModelProperty(value = "是否联点领导参加")
    private Byte inviteLinkLeader;

    @ApiModelProperty(value = "谈心次数")
    private Long communeCount;

    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date finishedTime;

    @ApiModelProperty(value = "活动地点")
    private String activityPlace;

    @ApiModelProperty(value = "应到人数")
    private Long dueCount;

    @ApiModelProperty(value = "实到人数")
    private Long factCount;

    @ApiModelProperty(value = "正式党员数")
    private Long formalCount;

    @ApiModelProperty(value = "预备党员数")
    private Long probationaryCount;

    @ApiModelProperty(value = "主题", required = true)
    @NotNull(message = "活动标题不能为空")
    private String subject;

    @ApiModelProperty(value = "准备情况")
    private String prepare;

    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @ApiModelProperty(value = "效果")
    private String effect;

    @ApiModelProperty(value = "整改情况")
    private String correction;

    @ApiModelProperty(value = "状态 dict HDZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long status;

    @ApiModelProperty(value = "上级参加人员")
    private String leaderList;

    @ApiModelProperty(value = "记录时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recordTime;

    @ApiModelProperty(value = "记录人")
    private String recorderPerson;

    @ApiModelProperty(value = "发布时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date distrubTime;

    @ApiModelProperty(value = "发布人")
    private String distrubPerson;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "审核时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "审核人")
    private Long auditUser;

    @ApiModelProperty(value = "审核人姓名")
    private String auditUserName;

    @ApiModelProperty(value = "审核机构")
    private Long auditOrg;

    @ApiModelProperty(value = "审核机构名称")
    private String auditOrgName;

    @ApiModelProperty(value = "审核结果 dict SHJG")
    @JsonSerialize(using = DictSerializer.class)
    private Long auditResult;

    @ApiModelProperty(value = "审核说明")
    private String auditComment;

    @ApiModelProperty(value = "上报类型 1为上级代办； 2为本机办理")
    private Long  reportToType;

    @ApiModelProperty(value = "置顶次数")
    private Long  stickNum;
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

    @ApiModelProperty(value = "创建时间 yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建用户ID")
    private Long createUserid;

    @ApiModelProperty(value = "创建用户姓名")
    private String createUsername;

    /**
     * 修改时间
     */
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
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