package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "减少党员")
public class  TabPbMemberReduceList {
    private Long memberReduceId;

    private Long deptId;

    private Long userId;

    private String realName;

    private Date reduceTime;

    private Long outType;

    private Long toOrgId;

    private String toOrgCode;

    private String toOrgName;

    private String toOrgPlace;

    private Long reduceReason;

    private Long oldIdentity;

    private Long quitType;

    private Long quitReason;

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

    private String comment;

    private String personStatus;

    private String orgRange;

    private Long abroadId;

    private Date recoveryTime;
}