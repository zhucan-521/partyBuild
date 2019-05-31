package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/31 11:50:14
 */
@Data
public class TabPbPartyMassesActivityJoin {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 党群活动ID
     */
    private Long partyMassesActivityId;

    /**
     * 参加的行政区划ID
     */
    private Long administrativeDivisionId;

    /**
     * 参加的行政区划名称
     */
    private String administrativeDivisionName;

    /**
     * 删除标示 默认0未删除 1 删除
     */
    private String delFlag;

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

}
