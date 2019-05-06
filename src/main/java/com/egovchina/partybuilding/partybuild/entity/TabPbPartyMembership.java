package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/4/16 16:27
 * @description 党员党籍实体类
 */
@Data
public class TabPbPartyMembership implements Serializable {

    /**
     * 党籍id
     */
    private Long membershipId;

    /**
     * 党员id
     */
    private Long userId;

    /**
     * 人员类别 码表值 RYLB
     */
    private Long identityType;

    /**
     * 党籍处理
     */
    private Long type;

    /**
     * 处理原因
     */
    private String reason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private Long createUserid;

    /**
     * 创建人姓名
     */
    private String createUsername;
}
