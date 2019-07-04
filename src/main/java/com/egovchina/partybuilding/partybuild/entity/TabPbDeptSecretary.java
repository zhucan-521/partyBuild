package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbDeptSecretary implements Serializable {

    /**
     * id主键
     */
    private Long secretaryId;

    /**
     *党员id
     */
    private Long userId;

    /**
     *领导班子id
     */
    private Long leadTeamId;

    /**
     *职务
     */
    private String postive;

    /**
     *参加工作时间
     */
    private Date joinWorkerTime;

    /**
     *专业技术职称
     */
    private String professionalTitles;

    /**
     *熟悉专业有何专长
     */
    private String professionalSpecialty;

    /**
     *学历学位-全日制教育
     */
    private String fullTimeSchooling;

    /**
     *学历学位-在职教育
     */
    private String education;

    /**
     *毕业院校系及专业-全日制
     */
    private String collegeMajor;

    /**
     *毕业院校系及专业-在职教育
     */
    private String collegeMajorTwo;

    /**
     *任现职时间
     */
    private Date servingTime;

    /**
     *任同级实职时间
     */
    private Date servingRealTime;

    /**
     *任现级时间
     */
    private Date incumbentTime;

    /**
     *任同级实职时间
     */
    private Date incumbentRealTime;

    /**
     *近三年度考核情况
     */
    private String assessmentSituation;

    /**
     *任同一班子同级实职时间
     */
    private String selectionSituation;

    /**
     *竞争性选拨情况
     */
    private String promotionSituation;

    /**
     *军转干部情况
     */
    private String armyCadresSituation;

    /**
     *后备干部情况
     */
    private String reserveCadresSituation;

    /**
     *两代表-委员职务
     */
    private String committeeDuties;

    /**
     *排序码
     */
    private Long orderNum;

    /**
     *删除标识
     */
    private String delFlag;

    /**
     *创建时间
     */
    private Date createTime;

    /**
     *创建用户Id
     */
    private Long createUserid;

    /**
     *创建人姓名
     */
    private String createUsername;

    /**
     *修改时间
     */
    private Date updateTime;

    /**
     *修改用户姓名id
     */
    private Long updateUserid;

    /**
     *修改用户姓名
     */
    private String updateUsername;

    /**
     *简历
     */
    private String resume;

    /**
     *近五年培训情况
     */
    private String trainingSituation;

    /**
     *是否委员
     */
    private Long whetherMember;

    /**
     *是否书记
     */
    private Long whetherSecretary;

    /**
     *首次进入两委班子时间
     */
    private Date firstCommitteesDate;

    /**
     *现任职务
     */
    private Long newPosition;

    /**
     *任免时间
     */
    private Date appointmentTime;

    /**
     *原任职务
     */
    private Long oldPosition;

    /**
     *任职时间
     */
    private Date serveTime;

}