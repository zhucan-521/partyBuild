package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbOrgnizeChange {

    private Long changeId;

    private Long deptId;

    private Long changeType;

    private Long oldSuperiorId;

    private String oldSuperiorName;

    private Long nowSuperiorId;

    private String nowSuperiorName;

    private String orgnizeName;

    private String shortName;

    private String orgnizeCode;

    private Date changeDate;

    private String fileNumber;

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

    private String changeReason;

}