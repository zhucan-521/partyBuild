package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;


@TableName(value = "tab_pb_flow_out")
@Data
public class TabPbFlowOut {

    private Long flowOutId;

    private Long orgId;

    private String flowToOrgnizeName;

    private Long flowOutPlace;

    private Date lostTime;

    private Long linkStatus;

    private Long userId;

    private Date flowOutDate;

    private Long flowOutType;

    private Long flowOutRange;

    private Long outIndustry;

    private Long flowOutReason;

    private String flowToOrgPhone;

    private String flowToOrgContactor;

    private String flowToUnitName;

    private String flowToOrgnizeCode;

    private Long flowToOrgnizeId;

    private String contactType;

    private String contactPhone;

    private String contactAddress;

    private Long outProfession;

    private Date returnDate;

    private Byte returnTag;

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

    private Long flowOutState;


}