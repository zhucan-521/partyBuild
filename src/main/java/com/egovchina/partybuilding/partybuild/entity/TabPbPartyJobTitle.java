package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "党员技术职务实体")
@Data
public class TabPbPartyJobTitle {

    private Long jobTitleId;

    private Long userId;

    private Long post;

    private Long qualifications;

    private Date getDate;

    private Date appointStartDate;

    private Date appointEndDate;

    private Boolean delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;
}