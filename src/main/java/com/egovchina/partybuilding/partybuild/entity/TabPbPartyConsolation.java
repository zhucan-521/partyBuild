package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TabPbPartyConsolation {

    private Long id;

    private Long userId;

    private Date consolationTime;

    private String consolationInfo;

    private Boolean delFlag;

    private Long createUserid;

    private String createUsername;

    private Date createTime;

    private Long updateUserid;

    private String updateUsername;

    private Date updateTime;

}