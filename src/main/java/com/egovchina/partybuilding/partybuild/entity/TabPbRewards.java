package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class TabPbRewards {

    private static final long serialVersionUID = 1L;

    private Long rewardsId;

    private Date rewardsDate;

    private String rewardsOrgnize;

    private Long rewardsOrgnizeId;

    private Long userId;

    private Long rewards;

    private Long rewardsReason;

    private Long approvedLevel;

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

    private String rewardsName;

    private String rewardsType;

    private String baseStatus;

    private String deed;

    private String comment;

    private Long imgTool;

    private Long fileTool;
}