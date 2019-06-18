package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class TabPbParticipant {

    /**
     * 数据Id
     */
    private Long participantId;

    /**
     * 组织生活Id
     */
    private Long activitiesId;

    /**
     * 人员Id
     */
    private Long userId;

    /**
     * 人员姓名
     */
    private String realName;

    /**
     * 人员类型
     */
    private String realType;

    /**
     * 通知时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss
     */
    private Date notifyTime;

    /**
     * 报名时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss
     */
    private Date signupTime;

    /**
     * 签到时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss
     */
    private Date signinTime;

    /**
     * 签到密码
     */
    private String signinPwd;

    /**
     * 缺席原因 查询数据字典QXYY 1为因病因事缺席 2为无故缺席
     */
    private Byte absentReason;

    /**
     * 是否组织者
     */
    private Byte ifOrgnizer;

    /**
     * 是否主持人 0为不是主持人,1为是主持人
     */
    private Byte ifModerator = 0;

    /**
     * 是否秘书长
     */
    private Byte ifSecretaryGeneral;

    /**
     * 是否主席团成员
     */
    private Byte ifPresidium;

    /**
     * 是否副秘书长
     */
    private Byte ifDeputySecretaryGeneral;

    /**
     * 是否列席会议
     */
    private Byte ifSitIn;

    /**
     * 是否主席团常委
     */
    private Byte ifStandingCommittee;

    /**
     * 是否上级参加人员 0为进社区党员，1为社区党员
     */
    private Byte ifLeader;

    /**
     * 是否联点领导  0否，1是
     */
    private Byte ifLinkLeader;

    /**
     * 活动类型
     */
    private String activitytype;

    /**
     * 组织评定等级
     */
    private Long orgAssess;

    /**
     * 组织评定意见
     */
    private String orgAssessContent;

    /**
     * 参会时所在组织Id
     */
    private Long inOrgId;

    /**
     * 上级组织评定等级
     */
    private Long parentOrgAssess;

    /**
     * 上级组织评定意见
     */
    private String parentOrgAssessContent;

    /**
     * 表现
     */
    private String performance;

    /**
     * 发言
     */
    private String speech;

    /**
     * 审核时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss
     */
    private Date auditTime;

    /**
     * 审核人
     */
    private Long auditUser;

    /**
     * 审核机构
     */
    private Long auditOrg;

    /**
     * 审核说明
     */
    private String auditComment;

    /**
     * 数据描述
     */
    private String description;

    /**
     * 所在党小组id
     */
    private Long groupId;

    /**
     * 所在党小组名称
     */
    private String groupName;

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
     * 创建时间 yyyy-MM-dd hh:mm:ss", example = "yyyy-MM-dd hh:mm:ss
     */
    private Date createTime;

    /**
     * 创建用户Id
     */
    private Long createUserid;

    /**
     * 创建用户姓名
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

    /**
     *
     */
    private Long signInState;
}