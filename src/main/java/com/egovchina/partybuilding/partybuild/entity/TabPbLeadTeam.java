package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TabPbLeadTeam {

    private Long leadTeamId;

    private Long orgId;

    private Long sessionYear;

    private Date changeDate;

    private Long duringYear;

    private Long voteType;

    private Long dueCount;

    private Long factCount;

    private Long voteCount;

    private Long validVoteCount;

    private Byte current;

    private Long electedType;

    private Date electedTime;

    private Long commMemNum;

    private Long commNumParttime;

    private Long parttimeCommNum;

    private Long standingMemNum;

    private Long backMemNum;

    private String description;

    private String voteStatus;

    private String voteResult;

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