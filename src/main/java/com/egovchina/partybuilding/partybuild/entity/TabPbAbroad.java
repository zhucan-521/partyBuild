package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * desc: 出国出境实体
 * Created by FanYanGen on 2019/4/22 14:04
 */
@Data
@Accessors(chain = true)
public class TabPbAbroad {

    private Long abroadId;

    private Long orgId;

    private Long userId;

    private Long orgRange;

    private String userName;

    private String idCardNo;

    private Date abroadDate;

    private Date planReturn;

    private Date returnDate;

    private Long goCountry;

    private String baseStatus;

    private Long abroadReason;

    private Long returnStatus;

    private Date settleTime;

    // ===============================

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

    private Long registryMode;

    private Long linkStatus;

    private Long returnActivitiesStatus;

    private Date applyActivitiesDate;

    private Date allowActivitiesDate;

    private Date registryReverseDate;

    private Byte isTransOut;

}