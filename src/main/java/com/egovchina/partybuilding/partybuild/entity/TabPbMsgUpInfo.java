package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "信息上报收到实体")
@Data
public class TabPbMsgUpInfo {

    private Long id;

    private Long upDeptId;

    private String upDeptName;

    private Long upUserId;

    private Long upType;

    private String upUsername;

    private Long recevieDeptId;

    private String recevieDeptName;

    private Long recevieUserId;

    private String recevieUsername;

    private String title;

    private Date upTime;

    private String titleLabel;

    private String delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private String content;

    private Long auditDeptId;

    private String auditDeptName;

    private Long auditUserId;

    private String auditUsername;

    private Date auditTime;

    private String auditComment;

    private Long auditAssess;

    private String realDeptName;

    private Long realDeptId;

}