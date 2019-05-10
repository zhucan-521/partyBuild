package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TabPbOrgClassify {

    private Long orgClassifyId;

    private Long deptId;

    private Long orgLevel;

    private Date levelDate;

    private Long orderNum;

    private String description;

    //base field

    private String eblFlag;

    private String delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private Long version;

    private String deptName;

}