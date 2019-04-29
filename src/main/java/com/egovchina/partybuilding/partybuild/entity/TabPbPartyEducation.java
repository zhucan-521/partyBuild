package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "党员学历实体")
@Data
public class TabPbPartyEducation {
    private Long educationId;

    private Long userId;


    private Long level;

    private String levelName;


    private Long degree;


    private String graduatedSchool;


    private String spec;


    private Boolean delFlag;


    private Date createTime;


    private Long createUserid;


    private String createUsername;


    private Date updateTime;


    private Long updateUserid;


    private String updateUsername;
}