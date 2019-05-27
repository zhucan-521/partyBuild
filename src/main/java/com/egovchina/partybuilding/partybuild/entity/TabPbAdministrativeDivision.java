package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/25 14:46:06
 */
@Data
public class TabPbAdministrativeDivision {

    /**
     * 行政区划id
     */
    private Long administrativeDivisionId;

    /**
     * 行政区划名称
     */
    private String administrativeDivisionName;

    /**
     * 行政区划编码
     */
    private String administrativeDivisionCode;

    /**
     * 上级行政区划id
     */
    private Long parentId;

    /**
     * 层级
     */
    private Long level;

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
