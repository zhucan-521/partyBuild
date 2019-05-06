package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class TabPbLeadTeamMember {

    /**
     * 班子成员id
     */
    private Long memberId;

    /**
     * 党组织主键
     */
    private Long orgId;

    /**
     * 领导班子id
     */
    private Long leadTeamId;

    /**
     * 职务级别 dict JB
     */
    private String rank;

    /**
     * 人员Id
     */
    private Long userId;

    /**
     * 人员姓名
     */
    private String personName;

    /**
     * 党内职务主键
     */
    private Long positiveId;

    /**
     * 党内职务名称
     */
    private String positiveName;

    /**
     * 任职方式
     */
    private Long tenureMode;

    /**
     * 任职时间 yyyy-MM-dd", example = "yyyy-MM-dd
     */
    private Date tenureBegin;

    /**
     * 任职年限
     */
    private Long tenureDuration;

    /**
     * 离任时间 yyyy-MM-dd", example = "yyyy-MM-dd
     */
    private Date tenureLeave;

    /**
     * 批准文号
     */
    private String approvalNumber;

    /**
     * 是否兼任村委会委员
     */
    private Byte asCommitteeMember;

    /**
     * 是否兼任村委主任
     */
    private Byte asCommitteeDirector;

    /**
     * 班子职务排序
     */
    private Long leadMemberOrder;

    /**
     * 图片
     */
    private String pictures;

    /**
     * 附件集合
     */
    private List<TabPbAttachment> attachments;

    /**
     * 数据描述
     */
    private String description;

    /**
     * 有效标记
     */
    private String eblFlag;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 排序码
     */
    private Long orderNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户Id
     */
    private Long createUserid;

    /**
     * 创建人姓名
     */
    private String createUsername;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改用户Id
     */
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    private String updateUsername;

    /**
     * 版本
     */
    private Long version;

}