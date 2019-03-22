package com.yizheng.partybuilding.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
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

    @ApiModelProperty(value = "id")
    @TableId(value = "rewards_id", type = IdType.AUTO)
    private Long rewardsId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "奖励日期")
    private Date rewardsDate;

    @ApiModelProperty(value = "奖励机构", required = true)
    private String rewardsOrgnize;

    @ApiModelProperty(value = "奖励机构Id", required = true)
    private Long rewardsOrgnizeId;

    @ApiModelProperty(value = "userId", required = true)
    private Long userId;

    @ApiModelProperty(value = "奖励内容")
    private Long rewards;

    @ApiModelProperty(value = "奖励原因")
    private Long rewardsReason;

    @ApiModelProperty(value = "批准机关级别 dict PZJGJB")
    @JsonSerialize(using = DictSerializer.class)
    private Long approvedLevel;

    @ApiModelProperty(value = "奖励撤销日期")
    private Date revokeDate;

    @JsonIgnore
    @ApiModelProperty(value = "有效标记 1有效 0无效")
    private String eblFlag;

    @JsonIgnore
    @ApiModelProperty(value = "删除标志 0正常 1删除")
    private String delFlag;

    @ApiModelProperty(value = "排序号")
    private Long orderNum;

    @ApiModelProperty(value = "描述")
    private String description;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Long createUserid;

    @JsonIgnore
    private String createUsername;

    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    private Long updateUserid;

    @JsonIgnore
    private String updateUsername;

    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "奖励名称 dict DYJCJL", required = true)
    @JsonSerialize(using = DictSerializer.class)
    private String rewardsName;

    @ApiModelProperty(value = "奖励类型", required = true)
    private String rewardsType;

    @ApiModelProperty(value = "党员基本情况")
    private String baseStatus;

    @ApiModelProperty(value = "主要事迹")
    private String deed;

    @ApiModelProperty(value = "备注：奖励说明")
    private String comment;

    @ApiModelProperty(value = "文档",example = "[\"1f8c1cff-fe95-03a9420620af.jpg\",\"1f8c1cff-fe95-03a9420620af.jpg\"]")
    private List<String> fileList;

    @ApiModelProperty(value = "图片",example = "[\"1f8c1cff-fe95-03a9420620af.jpg\",\"1f8c1cff-fe95-03a9420620af.jpg\"]")
    private List<String> imgList;

    private Long attachmentType;

    @ApiModelProperty(value = "图片数量（统计展示用，无法直接修改添加）")
    private Long imgTool;

    @ApiModelProperty(value = "文件数量（统计展示用，无法直接修改添加）")
    private Long fileTool;
}