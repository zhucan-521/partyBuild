package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author create by GuanYingxin on 2019/5/24 14:36
 * @description 领导班子届满提示信息实体
 */
@Data
@Accessors(chain = true)
public class LeadershipTeamRemindMessage {

    /**
     * 消息发送id
     */
    private Long sendId;

    /**
     * 发送党组织id
     */
    private List<Long> sendOrgIds;

    /**
     * 发送党组织名称
     */
    private String sendOrgName;

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
     * 消息接收id
     */
    private Long receiveId;

    /**
     * 接收人id
     */
    private Long receiveUserId;

    /**
     * 接收人名称
     */
    private String receiveUserName;

    /**
     * 消息接收状态，是否已读（0 未读   1 已读）
     */
    private Short receiveStatus;

    /**
     * 消息接收时间
     */
    private Date receiveTime;

}
