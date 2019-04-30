package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "党员工作信息实体")
@Data
public class TabPbPartyWork {
    private Long workId;

    private Long userId;

    private String unit;

    private String post;

    private Long frontLine;

    private Long stratum;

    private Date startDate;

    private Date endDate;

    private Boolean delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;
}