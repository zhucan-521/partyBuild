package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@ApiModel(value = "处分")
@Data
@TableName("tab_pb_punishment")
public class TabPbPunishment {

    private static final long serialVersionUID = 1L;

    private Long punishmentId;

    private Date punishDate;

    private String punishName;

    private Long punishType;

    private String judiciaryFlag;

    private String punishOrg;

    private Long punishOrgId;

    private Long userId;

    private Long approvedLevel;

    private Long punishReason;

    private Long punishResult;

    private Date revokeDate;

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

    private String baseStatus;

    private String mistake;

    private String followUp;

    private String comment;

    private Long imgTool;

    private Long fileTool;

}