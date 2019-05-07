package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TabPbPartyGroupMember {

    private Long memberId;

    private Long userId;

    private Long groupId;

    private Integer isLeader;

    // base field

    private Integer delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

}