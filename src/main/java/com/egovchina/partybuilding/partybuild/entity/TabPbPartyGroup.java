package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TabPbPartyGroup {

    private Long groupId;

    private String groupName;

    private Long orgId;

    private Integer delFlag;

    private Integer isRevoke;

    private String revokeName;

    private Date revokeTime;

    private Date buildTime;

    private String remarks;

    private Integer orderNum;

    // base field

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

}