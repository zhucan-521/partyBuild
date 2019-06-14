package com.egovchina.partybuilding.partybuild.service;

/**
 * @author create by GuanYingxin on 2019/6/14 16:16
 * @description 消息内容service
 */
public interface MessageContentService {

    /**
     * 获取消息内容
     * @param id 字典id
     * @return
     */
    String selectMessageContent(Long id);

}
