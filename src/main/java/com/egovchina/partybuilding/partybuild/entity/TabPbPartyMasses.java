package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbPartyMasses {

    /**
     * 党群id
     */
    private Long partyMassesId;

    /**
     * 党群名称
     */
    private String partyMassesName;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 坐标
     */
    private String coordinate;

    /**
     * 电话
     */
    private String tel;

    /**
     * 地址
     */
    private String address;

    /**
     * 层级
     */
    private String grade;

    /**
     * 服务时间
     */
    private String serviceHours;

    /**
     * 简介
     */
    private String content;

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
     * 数据描述
     */
    private String description;

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