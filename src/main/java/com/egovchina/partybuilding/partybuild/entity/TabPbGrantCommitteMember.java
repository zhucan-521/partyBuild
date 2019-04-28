package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbGrantCommitteMember {

    private Long grantCommitteeMemberId;

    private Long grantCommitteeId;

    private Long userId;

    private Long orgId;

    private Long leadTeamId;

    private Long partyMemberId;

    private String personName;

    private Long positiveId;

    private String positiveName;

    private Long tenureMode;

    private Date tenureBegin;

    private Long tenureDuration;

    private Date tenureLeave;

    private Byte asCommitteeMember;

    private Byte asCommitteeDirector;

    private Long leadMemberOrder;

    private String eblFlag;

    private String delFlag;

    private Long orderNum;

    private String description;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private Long version;

    private String orgName;

    private String gender;

    private String phone;

    private Long unitId;

    private String unitName;

    private Long currentOrgId;

    private String currentOrgName;
}