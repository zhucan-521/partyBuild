package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.partybuild.repository.TabPbMessageMapper;
import com.egovchina.partybuilding.partybuild.service.MessageContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author create by GuanYingxin on 2019/6/14 16:19
 * @description 消息内容service实现类
 */
@Service("messageContentService")
public class MessageContentServiceImpl implements MessageContentService {

    @Autowired
    private TabPbMessageMapper tabPbMessageMapper;

    @Override
    public String selectMessageContent() {
        return tabPbMessageMapper.selectMessageContent();
    }
}
