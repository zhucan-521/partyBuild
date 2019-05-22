package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/22 10:17:23
 */
@Data
@Accessors(chain = true)
public class TabPbPartyMassesParticipant {

    /**
     * 数据id
     */
    private Long participantId;

    /**
     * 党群活动Id
     */
    private Long partyMassesActivityId;

    /**
     * 人员Id
     */
    private Long userId;

    /**
     * 人员姓名
     */
    private String realName;

    /**
     * 通知时间
     */
    private Date notifyTime;

    /**
     * 报名时间
     */
    private Date signupTime;

    /**
     * 签到时间
     */
    private Date signinTime;

    /**
     * 签到密码
     */
    private String signinPwd;

    /**
     * 缺席原因
     */
    private Long absentReason;

    /**
     * 是否组织者
     */
    private Integer ifOrgnizer;

    /**
     * 是否主持人
     */
    private Integer ifModerator;

    /**
     * 是否秘书长
     */
    private Integer ifSecretaryGeneral;

    /**
     * 是否主席团成员
     */
    private Integer ifPresidium;

    /**
     * 是否副秘书长
     */
    private Integer ifDeputySecretaryGeneral;

    /**
     * 是否列席会议
     */
    private Integer ifSitIn;

    /**
     * 是否主席团常委
     */
    private Integer ifStandingCommittee;

    /**
     * 是否上级参加人员
     */
    private Integer ifLeader;

    /**
     * 是否联点领导
     */
    private Integer ifLinkLeader;

    /**
     * 表现
     */
    private String performance;

    /**
     * 发言
     */
    private String speech;

    /**
     * 活动类型
     */
    private Long activitytype;

    /**
     * 组织评定等级
     */
    private Long orgAssess;

    /**
     * 组织评定意见
     */
    private String orgAssessContent;

    /**
     * 参与时所在组织
     */
    private Long inOrgId;

    /**
     * 组织评定等级
     */
    private Long parentOrgAssess;

    /**
     * 组织评定意见
     */
    private String parentOrgAssessContent;

    /**
     * 审核时间
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
     * 数据描述
     */
    private String description;

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

    /**
     * 人员类型 dict USERTAG
     */
    private String realType;

    /**
     * 党小组Id
     */
    private Long groupId;

    /**
     * 党小组名称
     */
    private String groupName;

    /**
     * 签到状态
     */
    private Long signInState;

}
