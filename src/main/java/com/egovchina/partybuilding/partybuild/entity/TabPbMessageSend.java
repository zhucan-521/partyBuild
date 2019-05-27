package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/5/23 11:04
 * @description 消息发送表
 */
@Data
@Accessors(chain = true)
public class TabPbMessageSend {

    /**
     * 消息发送id
     */
    private Long sendId;

    /**
     * 发送党组织id
     */
    private Long senderId;

    /**
     * 发送党组织名称
     */
    private String senderName;

    /**
     * 消息类别
     */
    private Long type;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 删除标记
     */
    private Short delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户id
     */
    private Long createUserId;

    /**
     * 创建用户姓名
     */
    private String createUsername;

}
