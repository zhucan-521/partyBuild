package com.yizheng.partybuilding.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TabPbMemberAddList {
    private Long memberAddId;

    private Long personId;

    private Long deptId;

    private String realName;

    private Date addTime;

    private Long inType;

    private Long fromOrgId;

    private String fromOrgCode;

    private String fromOrgName;

    private String fromOrgPlace;

    private Long addReason;

    private Long oldIdentity;

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

    private String comment;
}