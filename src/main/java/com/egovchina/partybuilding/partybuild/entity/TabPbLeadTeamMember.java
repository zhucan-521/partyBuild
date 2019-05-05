package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;


@Data
public class TabPbLeadTeamMember {

    private Long memberId;

    private Long orgId;

    private Long leadTeamId;

    private String rank;

    private Long userId;

    private String personName;

    private Long positiveId;

    private String positiveName;

    private Long tenureMode;

    private Date tenureBegin;

    private Long tenureDuration;

    private Date tenureLeave;

    private String approvalNumber;

    private Byte asCommitteeMember;

    private Byte asCommitteeDirector;

    private Long leadMemberOrder;

    private String description;

    private String eblFlag;

    private String delFlag;

    private Long orderNum;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private Long version;

}