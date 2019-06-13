package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TabPbIdentityVerificationFeedback {

    /**
     * 主键
     */
    private Long id;

    /**
     * 党员id
     */
    private Long userId;

    /**
     * 反馈类型 dict SFHCFKLX
     */
    private Long type;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 创建用户id
     */
    private Long createUserid;

    /**
     * 创建用户姓名
     */
    private String createUsername;

    /**
     * 创建时间 yyyy-MM-dd
     */
    private Date createTime;
}