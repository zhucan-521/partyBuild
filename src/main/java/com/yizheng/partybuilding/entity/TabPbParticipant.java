package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@ApiModel(value = "在职党员活动明细表")
@Accessors(chain = true)
public class TabPbParticipant {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据Id")
    private Long participantId;

    @ApiModelProperty(value = "组织生活Id")
    private Long activitiesId;

    @ApiModelProperty(value = "人员Id", required = true)
    private Long userId;

    @ApiModelProperty(value = "人员姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "人员类型")
    private String realType;

    @ApiModelProperty(value = "通知时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date notifyTime;

    @ApiModelProperty(value = "报名时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date signupTime;

    @ApiModelProperty(value = "签到时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date signinTime;

    @ApiModelProperty(value = "签到密码")
    private String signinPwd;

    @ApiModelProperty(value = "缺席原因 查询数据字典QXYY 1为因病因事缺席 2为无故缺席")
    @JsonSerialize(using = DictSerializer.class)
    private Byte absentReason;

    @ApiModelProperty(value = "是否组织者")
    private Byte ifOrgnizer;

    @ApiModelProperty(value = "是否主持人 0为不是主持人,1为是主持人")
    private Byte ifModerator = 0;

    @ApiModelProperty(value = "是否秘书长")
    private Byte ifSecretaryGeneral;

    @ApiModelProperty(value = "是否主席团成员")
    private Byte ifPresidium;

    @ApiModelProperty(value = "是否副秘书长")
    private Byte ifDeputySecretaryGeneral;

    @ApiModelProperty(value = "是否列席会议")
    private Byte ifSitIn;

    @ApiModelProperty(value = "是否主席团常委")
    private Byte ifStandingCommittee;

    @ApiModelProperty(value = "是否上级参加人员 0为进社区党员，1为社区党员")
    private Byte ifLeader;

    @ApiModelProperty(value = "是否联点领导  0否，1是")
    private Byte ifLinkLeader;

    @ApiModelProperty(value = "活动类型")
    private String activitytype;

    @ApiModelProperty(value = "组织评定等级")
    private Long orgAssess;

    @ApiModelProperty(value = "组织评定意见")
    private String orgAssessContent;

    @ApiModelProperty(value = "参会时所在组织Id")
    private Long inOrgId;

    @ApiModelProperty(value = "上级组织评定等级")
    private Long parentOrgAssess;

    @ApiModelProperty(value = "上级组织评定意见")
    private String parentOrgAssessContent;

    @ApiModelProperty(value = "表现")
    private String performance;

    @ApiModelProperty(value = "发言")
    private String speech;

    @ApiModelProperty(value = "审核时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date auditTime;

    @ApiModelProperty(value = "审核人")
    private Long auditUser;

    @ApiModelProperty(value = "审核机构")
    private Long auditOrg;

    @ApiModelProperty(value = "审核说明")
    private String auditComment;

    @ApiModelProperty(value = "数据描述")
    private String description;

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
    @ApiModelProperty(value = "创建时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建用户姓名")
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