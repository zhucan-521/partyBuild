package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.dto.MessageAddDTO;
import com.egovchina.partybuilding.common.dto.MessageReceiveDTO;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.enums.ReceiverTypeEnum;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.MessageUpdateDTO;
import com.egovchina.partybuilding.partybuild.entity.StationNewsQueryBean;
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
import java.util.Date;
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
        int result = 0;

        //往消息发送表插入数据
        TabPbMessageSend tabPbMessageSend = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(messageAddDTO, TabPbMessageSend.class, false);
        tabPbMessageSend.setSenderId(UserContextHolder.getUserId())
                .setSenderName(UserContextHolder.getUserName())
                .setSendTime(new Date());
        result += tabPbMessageMapper.insertTabPbMessageSend(tabPbMessageSend);

        //获取消息接收者信息集合
        List<MessageReceiveDTO> messageReceiveDTOList = messageAddDTO.getReceivers();

        //如果传入的是组织id，则查出该组织下的所有账号信息添加到消息接收表
        if (ReceiverTypeEnum.ACCOUNT.getReceiverType().equals(messageAddDTO.getReceiverType())) {
            //将接收者id添加到receiverIds集合当中
            List<Long> receiverIds = new ArrayList<>();
            for (MessageReceiveDTO messageReceiveDTO : messageReceiveDTOList) {
                receiverIds.add(messageReceiveDTO.getReceiverId());
            }
            messageReceiveDTOList = tabSysUserMapper.selectAccountByReceiverIdWithAccount(receiverIds);
        }

        List<TabPbMessageReceive> tabPbMessageReceives = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(messageReceiveDTOList, TabPbMessageReceive.class,
                receive -> receive.setSendId(tabPbMessageSend.getSendId())
                        .setReceiverType(messageAddDTO.getReceiverType()), false);

        //将消息批量插入消息接收表
        result += tabPbMessageMapper.batchInsertTabPbMessageReceive(tabPbMessageReceives);
        return result;
    }

    @Transactional
    @Override
    public MessageSendVO getMessageSendVO(Long sendId, Long receiverId) {
        TabPbMessageReceive tabPbMessageReceive = tabPbMessageMapper.selectTabPbMessageReceiveBySendIdAndReceiverId(sendId, receiverId);
        if (tabPbMessageReceive != null) {
            tabPbMessageMapper.updateTabPbMessageReceiveBySendIdAndReceiverId(sendId, receiverId);
        }
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
    public List<MessageSendVO> getOrgMessageSendList(Page page, StationNewsQueryBean stationNewsQueryBean) {
        PageHelper.startPage(page);
        stationNewsQueryBean.setReceiverId(UserContextHolder.getUserId());
        return tabPbMessageMapper.selectOrgMessageSendVOList(stationNewsQueryBean);
    }

    @Transactional
    @Override
    public List<MessageSendVO> getNotRemindedMessageVO(Long rangeDeptId, Long orgRange) {
        List<MessageSendVO> messageSendVOS = tabPbMessageMapper.selectRemindedMessageVOById(UserContextHolder.getUserId(), rangeDeptId, orgRange);
        List<Long> sendIds = messageSendVOS.stream().map(MessageSendVO::getSendId).collect(Collectors.toList());
        //更新接受状态为0
        if (CollectionUtil.isNotEmpty(sendIds)) {
            tabPbMessageMapper.updateMessageTipStatusBySendIdsAndReceiverId(sendIds, UserContextHolder.getUserId());
        }
        return messageSendVOS;
    }

}
