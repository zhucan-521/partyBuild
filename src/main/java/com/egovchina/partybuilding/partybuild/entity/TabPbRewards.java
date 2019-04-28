package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@ApiModel(value = "奖励")
@Data
@TableName("tab_pb_rewards")
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