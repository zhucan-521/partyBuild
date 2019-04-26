package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class SysDeptUpgradeTemp {

    private Long id;

    private Long deptId;

    private String deptName;

    private String upgradeDeptName;

    private String upgradeShortName;

    private String delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private String deptBranchs;

    private String approveOrgName;
}