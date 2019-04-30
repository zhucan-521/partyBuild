package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName(value = "tab_pb_family")
public class TabPbFamily implements Serializable {


    private Long relationId;

    private Long userId;

    private Long relation;

    private Long relationUserId;

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

    private String username;

    private Long gender;

    private String idCardNo;

    private Long nation;

    private Long policyFace;

    private String phone;

    private Long positive;

    private Date birthday;

    private String whetherAbroad;

    private Long abroad;




}