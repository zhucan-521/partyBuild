package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbPartyMassesMatter {

    /**
     * 服务事项id
     */
    private Long partyMassesMatterId;

    /**
     * 党群id
     */
    private Long partyMassesId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 内容
     */
    private String content;

    /**
     * 服务对象 字典
     */
    private Long crowd;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 电话
     */
    private String tel;

    /**
     * 地址
     */
    private String address;

    /**
     * 服务时间
     */
    private String serviceHours;

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

}