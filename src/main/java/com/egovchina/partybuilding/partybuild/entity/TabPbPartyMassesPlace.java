package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbPartyMassesPlace {

    /**
     * 党群场地id
     */
    private Long partyMassesPlaceId;

    /**
     * 党群id
     */
    private Long partyMassesId;

    /**
     * 场地名称
     */
    private String placeName;

    /**
     * 容纳人数
     */
    private Long capacity;

    /**
     * 开放日
     */
    private String openDay;

    /**
     * 开放时间段
     */
    private String openTimePeriod;

    /**
     * 场地地址
     */
    private String address;

    /**
     * 封面图
     */
    private String cover;

    /**
     * 服务内容
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