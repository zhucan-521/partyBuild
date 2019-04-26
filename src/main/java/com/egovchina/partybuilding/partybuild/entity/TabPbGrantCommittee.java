package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbGrantCommittee {

    private Long orgRange;

    private Long grantCommitteeId;

    private Long orgId;

    private String orgName;

    private Byte current;

    private Long sessionYear;

    private Date changeDate;

    private Long duringYear;

    private Long voteType;

    private Long dueCount;

    private Long factCount;

    private Long voteCount;

    private Long validVoteCount;

    private Long electedType;

    private Date electedTime;

    private Long commMemNum;

    private Long commNumParttime;

    private Long parttimeCommNum;

    private Long standingMemNum;

    private Long backMemNum;

    private Date foundedDate;

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

    private String voteStatus;

}