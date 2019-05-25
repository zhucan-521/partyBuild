package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.MessageAddDTO;
import com.egovchina.partybuilding.partybuild.dto.MessageReceiveDTO;
import com.egovchina.partybuilding.partybuild.dto.MessageUpdateDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageReceive;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageSend;
import com.egovchina.partybuilding.partybuild.repository.TabPbMessageMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.StationNewsService;
import com.egovchina.partybuilding.partybuild.vo.MessageSendVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author create by GuanYingxin on 2019/5/23 9:51
 * @description 站内消息service实现
 */
@Service("stationNewsService")
public class StationNewsServiceImpl implements StationNewsService {

    @Autowired
    private TabPbMessageMapper tabPbMessageMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Transactional
    @Override
    public int batchInsertStationNews(MessageAddDTO messageAddDTO) {
        //给消息发送表插入数据
        int result = 0;
        TabPbMessageSend tabPbMessageSend = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(messageAddDTO, TabPbMessageSend.class, false);
        tabPbMessageSend.setSenderId(UserContextHolder.getUserId()).setSenderName(UserContextHolder.getUserName());
        result += tabPbMessageMapper.insertTabPbMessageSend(tabPbMessageSend);

        //获取消息接收者信息集合
        List<MessageReceiveDTO> messageReceiveDTOList = messageAddDTO.getReceivers();

        //判断接收者id在数据库中是否存在
        List<Long> list = messageReceiveDTOList.stream().map(MessageReceiveDTO::getReceiverId).collect(Collectors.toList());
        boolean judgement = tabPbMessageMapper.checkReceiverIdIfExist(list);
        if (judgement) {
            throw new BusinessDataCheckFailException("数据已存在");
        }

        if (CollectionUtil.isNotEmpty(messageReceiveDTOList)) {
            //给接收对象赋值
            List<TabPbMessageReceive> tabPbMessageReceiveList = new ArrayList<>();

            messageReceiveDTOList.forEach(messageReceiveDTO -> {
                TabPbMessageReceive tabPbMessageReceive = new TabPbMessageReceive();
                if (messageReceiveDTO.getReceiverName() == null) {
                    SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(messageReceiveDTO.getReceiverId());
                    if (sysUser == null) {
                        throw new BusinessDataCheckFailException("接收对象不存在");
                    }
                    tabPbMessageReceive.setReceiverId(messageReceiveDTO.getReceiverId())
                            .setSendId(tabPbMessageSend.getSendId())
                            .setReceiverName(sysUser.getRealname())
                            .setReceiverType(messageReceiveDTO.getReceiverType());
                } else {
                    tabPbMessageReceive.setReceiverId(messageReceiveDTO.getReceiverId())
                            .setSendId(tabPbMessageSend.getSendId())
                            .setReceiverName(messageReceiveDTO.getReceiverName())
                            .setReceiverType(messageReceiveDTO.getReceiverType());
                }
                tabPbMessageReceiveList.add(tabPbMessageReceive);
            });
            //批量插入消息接收表
            result += tabPbMessageMapper.batchInsertTabPbMessageReceive(tabPbMessageReceiveList);
        }
        return result;
    }

    @Override
    public MessageSendVO getMessageSendVO(Long sendId) {
        return tabPbMessageMapper.selectMessageSendVO(sendId);
    }

    @Override
    public List<MessageSendVO> getMessageSendVOList(Page page, Long receiverId) {
        PageHelper.startPage(page);
        return tabPbMessageMapper.selectMessageSendVOList(receiverId);
    }

    @Transactional
    @Override
    public int batchUpdateStationNews(MessageUpdateDTO messageUpdateDTO) {
        if (!tabPbMessageMapper.checkMessageSendIfExists(messageUpdateDTO.getSendId())) {
            throw new BusinessDataNotFoundException("要修改的消息不存在");
        }
        //修改发送的消息
        int result = 0;
        TabPbMessageSend tabPbMessageSend = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(messageUpdateDTO, TabPbMessageSend.class, true);
        result += tabPbMessageMapper.updateTabPbMessageSend(tabPbMessageSend);

        //获取消息接收者id集合
        List<Long> receiverIds = messageUpdateDTO.getReceiverIds();
        if (CollectionUtil.isNotEmpty(receiverIds)) {
            //给接收对象赋值
            List<TabPbMessageReceive> tabPbMessageReceiveList = new ArrayList<>();

            receiverIds.forEach(r -> {
                TabPbMessageReceive tabPbMessageReceive = new TabPbMessageReceive();
                if (messageUpdateDTO.getReceiverName() == null) {
                    SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(r);
                    if (sysUser == null) {
                        throw new BusinessDataCheckFailException("接收对象不存在");
                    }
                    tabPbMessageReceive.setReceiverId(r)
                            .setSendId(messageUpdateDTO.getSendId())
                            .setReceiverName(sysUser.getRealname())
                            .setReceiverType(messageUpdateDTO.getReceiverType());
                } else {
                    tabPbMessageReceive.setReceiverId(r)
                            .setSendId(messageUpdateDTO.getSendId())
                            .setReceiverName(messageUpdateDTO.getReceiverName())
                            .setReceiverType(messageUpdateDTO.getReceiverType());
                }
                tabPbMessageReceiveList.add(tabPbMessageReceive);
            });
            result += tabPbMessageMapper.batchUpdateTabPbMessageReceive(tabPbMessageReceiveList);
        }
        return result;
    }

    @Override
    public List<MessageSendVO> getOrgMessageSendList(Page page, Long receiverOrgId, Long receiverType) {
        PageHelper.startPage(page);
        return tabPbMessageMapper.selectOrgMessageSendVOList(receiverOrgId, receiverType);
    }

}
