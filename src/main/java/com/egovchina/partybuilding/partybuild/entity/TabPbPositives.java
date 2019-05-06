package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
@TableName("tab_pb_positives")
public class TabPbPositives {

    private Long positiveId;

    private Long userId;

    private Long positiveType;

    private Long positiveOrgId;

    private String positiveOrg;

    private Long positiveName;

    private String positiveNameDict;

    private Long positiveLevel;

    private Date positiveStart;

    private Date positiveFinished;

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

    private Long leftReason;

    private Long leftType;

    private Long sort;

    private Byte businessFrom;

    private Long businessId;
}