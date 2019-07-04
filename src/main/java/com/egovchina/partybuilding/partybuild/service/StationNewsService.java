package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.dto.MessageAddDTO;
import com.egovchina.partybuilding.common.entity.Page;
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
     * 查看某一个消息详情(同时更新消息接收状态)
     *
     * @param receiveId 消息接收id
     * @return
     */
    MessageSendVO getMessageSendVO(Long receiveId);

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
     * 账号获取自己所管理组织的消息列表
     * @param page  分页参数
     * @param stationNewsQueryBean 查询参数
     * @return
     */
    List<MessageSendVO> getOrgMessageSendList(Page page, StationNewsQueryBean stationNewsQueryBean);

    /**
     * 显示未提醒的信息
     *
     * @param rangeDeptId   消息触发组织id
     * @param orgRange      组织范围
     * @return
     */
    List<MessageSendVO> getNotRemindedMessageVO(Long rangeDeptId, Long orgRange);

}
