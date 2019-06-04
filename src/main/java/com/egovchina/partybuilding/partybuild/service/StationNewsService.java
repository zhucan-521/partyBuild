package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.MessageAddDTO;
import com.egovchina.partybuilding.partybuild.dto.MessageUpdateDTO;
import com.egovchina.partybuilding.partybuild.entity.StationNewsQueryBean;
import com.egovchina.partybuilding.partybuild.vo.MessageSendVO;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/5/23 9:48
 * @description 站内消息service
 */
public interface StationNewsService {

    /**
     * 新增站内消息
     *
     * @param messageAddDTO 新增消息dto
     * @return
     */
    int batchInsertStationNews(MessageAddDTO messageAddDTO);

    /**
     * 查看站内消息
     *
     * @param sendId 消息发送id
     * @param receiverId 消息接收者id
     * @return
     */
    MessageSendVO getMessageSendVO(Long sendId, Long receiverId);

    /**
     * 党员获取自己能够看到的消息列表
     *
     * @param page          分页参数
     * @param receiverId 接收人id
     * @return
     */
    List<MessageSendVO> getMessageSendVOList(Page page, Long receiverId);

    /**
     * (批量)修改站内消息
     *
     * @param messageUpdateDTO 更新消息dto
     * @return
     */
    int batchUpdateStationNews(MessageUpdateDTO messageUpdateDTO);

    /**
     * 获取某个党员在某个组织下的消息列表
     * @param page  分页参数
     * @param stationNewsQueryBean 查询参数
     * @return
     */
    List<MessageSendVO> getOrgMessageSendList(Page page, StationNewsQueryBean stationNewsQueryBean);

    /**
     * 显示未提醒的信息
     *
     * @param receiverId 接收者id
     * @return
     */
    List<MessageSendVO> getNotRemindedMessageVO(Long receiverId);
}
