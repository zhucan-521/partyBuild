package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;
@Data
public class TabPbPositives {

    private static final long serialVersionUID = 1L;

    private Integer positiveId;

    private Long userId;

    private Integer positiveType;

    private Integer positiveOrgId;

    private String positiveOrg;

    private Long positiveName;

    private String positiveNameDict;

    private Long positiveLevel;

    private Date positiveStart;

    private Date positiveFinished;

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

    private Long leftReason;

    private Long leftType;

    private Long sort;

}