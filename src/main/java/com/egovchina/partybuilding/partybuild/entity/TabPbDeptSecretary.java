package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "实体")
@Data
public class TabPbDeptSecretary implements Serializable {

    private Long secretaryId;

    private Long userId;

    private Long deptId;

    private String postive;

    private Date joinWorkerTime;

    private String professionalTitles;

    private String professionalSpecialty;

    private String fullTimeSchooling;

    private String education;

    private String collegeMajor;

    private String collegeMajorTwo;

    private Date servingTime;

    private Date servingRealTime;

    private Date incumbentTime;

    private Date incumbentRealTime;

    private String assessmentSituation;

    private String selectionSituation;

    private String promotionSituation;

    private String armyCadresSituation;

    private String reserveCadresSituation;

    private String committeeDuties;

    private Long orderNum;

    private String delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private String resume;

    private String trainingSituation;

    private Long whetherMember;

    private Long whetherSecretary;

    private SysUser user;


}