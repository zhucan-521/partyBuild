package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("困难党员")
public class TabPbHardship {

    @ApiModelProperty("困难程度")
    @JsonSerialize(using = DictSerializer.class)
    private Long difficultyLevel;

    @ApiModelProperty("困难记录ID")
    private Long hardshipId;

    @ApiModelProperty("组织主键")
    private Long orgId;

    @ApiModelProperty("人员Id")
    private Long userId;

    @ApiModelProperty("生活困难类型 dict KNLX")
    @JsonSerialize(using = DictSerializer.class)
    private String hardshipType;

    @ApiModelProperty("是否享受诚征最低生活保障费")
    private Long ifAllowances;

    @ApiModelProperty("是否享受优抚对象抚恤补助")
    private Long ifPension;

    @ApiModelProperty("建国前没有工作老党员")
    private Byte ifOldMember;

    @ApiModelProperty("数据描述")
    private String description;

    @ApiModelProperty("健康状况")
    @JsonSerialize(using = DictSerializer.class)
    private Long health;

    @ApiModelProperty("生活困难补充情况")
    private String implementation;

    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("组织名称")
    private String orgName;

    /**
     * 有效标记
     */
    @JsonIgnore
    private String eblFlag;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 排序码
     */
    @JsonIgnore
    private Long orderNum;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改用户Id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;

    /**
     * 版本
     */
    @JsonIgnore
    private Long version;
}