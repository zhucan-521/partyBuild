package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * desc: 困难党员实体
 * Created by FanYanGen on 2019/4/22 14:04
 */
@Data
@Accessors(chain = true)
public class TabPbHardship {

    private Long difficultyLevel;

    private Long hardshipId;

    private Long orgId;

    private Long userId;

    private String hardshipType;

    private Long ifAllowances;

    private Long ifPension;

    private Byte ifOldMember;

    private String description;

    private Long health;

    private String implementation;

    private String username;

    private String orgName;

    // ===============================

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