package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class TabPbPunishment {

    private static final long serialVersionUID = 1L;

    private Long punishmentId;

    private Date punishDate;

    private String punishName;

    private Long punishType;

    private String judiciaryFlag;

    private String punishOrg;

    private Long punishOrgId;

    private Long userId;

    private Long approvedLevel;

    private Long punishReason;

    private Long punishResult;

    private Date revokeDate;

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

    private String baseStatus;

    private String mistake;

    private String followUp;

    private String comment;

    private Long imgTool;

    private Long fileTool;

}