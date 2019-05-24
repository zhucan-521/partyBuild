package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbPartyMassesActivity {

    /**
     * 党群活动id
     */
    private Long partyMassesActivityId;

    /**
     * 党群id
     */
    private Long partyMassesId;

    /**
     * 活动场地id
     */
    private Long placeId;

    /**
     * 标题
     */
    private String subject;

    /**
     * 状态
     */
    private Long status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date finishedTime;

    /**
     * 地点
     */
    private String address;

    /**
     * 封面图
     */
    private String cover;

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

    /**
     * 内容
     */
    private String content;

}