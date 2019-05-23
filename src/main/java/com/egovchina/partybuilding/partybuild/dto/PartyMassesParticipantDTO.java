package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/22 10:16:01
 */
@ApiModel(value = "党群活动参与人DTO")
@Data
public class PartyMassesParticipantDTO {

    @ApiModelProperty(value = "数据id")
    private Long participantId;

    @ApiModelProperty(value = "党群活动Id")
    private Long partyMassesActivityId;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty(value = "人员姓名")
    private String realName;

    @ApiModelProperty(value = "通知时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date notifyTime;

    @ApiModelProperty(value = "报名时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signupTime;

    @ApiModelProperty(value = "签到时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signinTime;

    @ApiModelProperty(value = "签到密码")
    private String signinPwd;

    @ApiModelProperty(value = "缺席原因")
    private Long absentReason;

    @ApiModelProperty(value = "是否组织者")
    private Integer ifOrgnizer;

    @ApiModelProperty(value = "是否主持人")
    private Integer ifModerator;

    @ApiModelProperty(value = "是否秘书长")
    private Integer ifSecretaryGeneral;

    @ApiModelProperty(value = "是否主席团成员")
    private Integer ifPresidium;

    @ApiModelProperty(value = "是否副秘书长")
    private Integer ifDeputySecretaryGeneral;

    @ApiModelProperty(value = "是否列席会议")
    private Integer ifSitIn;

    @ApiModelProperty(value = "是否主席团常委")
    private Integer ifStandingCommittee;

    @ApiModelProperty(value = "是否上级参加人员")
    private Integer ifLeader;

    @ApiModelProperty(value = "是否联点领导")
    private Integer ifLinkLeader;

    @ApiModelProperty(value = "表现")
    private String performance;

    @ApiModelProperty(value = "发言")
    private String speech;

    @ApiModelProperty(value = "活动类型")
    private Long activitytype;

    @ApiModelProperty(value = "组织评定等级")
    private Long orgAssess;

    @ApiModelProperty(value = "组织评定意见")
    private String orgAssessContent;

    @ApiModelProperty(value = "参与时所在组织")
    private Long inOrgId;

    @ApiModelProperty(value = "组织评定等级")
    private Long parentOrgAssess;

    @ApiModelProperty(value = "组织评定意见")
    private String parentOrgAssessContent;

    @ApiModelProperty(value = "审核时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    @ApiModelProperty(value = "审核人")
    private Long auditUser;

    @ApiModelProperty(value = "审核机构")
    private Long auditOrg;

    @ApiModelProperty(value = "审核说明")
    private String auditComment;

    @ApiModelProperty(value = "人员类型 dict USERTAG")
    private String realType;

    @ApiModelProperty(value = "党小组Id")
    private Long groupId;

    @ApiModelProperty(value = "党小组名称")
    private String groupName;

    @ApiModelProperty(value = "签到状态")
    private Long signInState;

}
