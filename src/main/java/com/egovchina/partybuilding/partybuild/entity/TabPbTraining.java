package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("tab_pb_training")
public class TabPbTraining implements Serializable {

    private Long traningId;

    private Long userId;

    private Long deptId;

    private Date trainingStartDate;

    private Date trainingFinishedDate;

    private String trainingPlace;

    private Long trainingHours;

    private Long trainingType;

    private String trainingOrg;

    private Long trainingScore;

    private Date viewDate;

    private String trainingContent;

    private String trainingGain;

    private String trainingView;

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



}