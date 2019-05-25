package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/5/23 11:11
 * @description 消息接收实体
 */
@Data
@Accessors(chain = true)
public class TabPbMessageReceive {

    /**
     * 消息接收id
     */
    private Long receiveId;

    /**
     * 消息发送id
     */
    private Long sendId;

    /**
     * 接收人id
     */
    private Long receiverId;

    /**
     * 接收人名称
     */
    private String receiverName;

    /**
     * 消息接收状态，是否已读（0 未读   1 已读）
     */
    private Short receiveStatus;

    /**
     * 消息接收时间
     */
    private Date receiveTime;

    /**
     * 接受类型者 0 个人 1 组织
     */
    private Long receiverType;

}
