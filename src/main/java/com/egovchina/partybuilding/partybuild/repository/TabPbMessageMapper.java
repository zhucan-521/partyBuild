package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.StationNewsQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageReceive;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageSend;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamExpireVO;
import com.egovchina.partybuilding.partybuild.vo.MessageSendVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/5/23 10:26
 * @description 站内消息DAO层
 */
@Repository
public interface TabPbMessageMapper {

    /**
     * 新增站内消息
     *
     * @param tabPbMessageSendList 消息实体列表
     * @return
     */
    int batchInsertTabPbMessageSend(List<TabPbMessageSend> tabPbMessageSendList);

    /**
     * 新增站内消息
     *
     * @param tabPbMessageSend 消息实体列表
     * @return
     */
    int insertTabPbMessageSend(TabPbMessageSend tabPbMessageSend);

    /**
     * 查询站内消息VO
     *
     * @param sendId
     * @return
     */
    MessageSendVO selectMessageSendVO(@Param("sendId") Long sendId);

    /**
     * 批量新增接收消息
     *
     * @param tabPbMessageReceiveList 消息实体
     * @return
     */
    int batchInsertTabPbMessageReceive(List<TabPbMessageReceive> tabPbMessageReceiveList);

    /**
     * 党员获取自己能够看到的消息列表
     *
     * @param receiverId 接收人id
     * @return
     */
    List<MessageSendVO> selectMessageSendVOList(Long receiverId);

    /**
     * 批量更新站内消息
     *
     * @param tabPbMessageSendList 更新站内消息实体
     * @return
     */
    int batchUpdateStationNews(List<TabPbMessageSend> tabPbMessageSendList);

    /**
     * 检测消息是否存在
     *
     * @param sendId 消息发现id
     * @return
     */
    boolean checkMessageSendIfExists(Long sendId);

    /**
     * 更新消息接收表
     *
     * @param tabPbMessageReceive 更新消息实体
     * @return
     */
    int updateTabPbMessageReceive(TabPbMessageReceive tabPbMessageReceive);

    /**
     * 查询要到期的领导班子
     */
    List<LeadTeamExpireVO> queryTheLeadershipTeamThatIsDueToExpire();

    /**
     * 定时发送消息
     *
     * @param tabPbMessageReceive 定时发送消息实体
     * @return
     */
    int insertTabPbMessageReceive(TabPbMessageReceive tabPbMessageReceive);

    /**
     * 批量修改消息接收表
     *
     * @param tabPbMessageReceiveList 消息接收实体
     * @return
     */
    int batchUpdateTabPbMessageReceive(List<TabPbMessageReceive> tabPbMessageReceiveList);

    /**
     * 修改消息发送表
     *
     * @param tabPbMessageSend 消息发送表实体
     * @return
     */
    int updateTabPbMessageSend(TabPbMessageSend tabPbMessageSend);

    /**
     * 获取某个党员在某个组织下的消息列表
     *
     * @param stationNewsQueryBean 查询参数
     * @return
     */
    List<MessageSendVO> selectOrgMessageSendVOList(StationNewsQueryBean stationNewsQueryBean);

    /**
     * 判断接收者id在数据库中是否存在
     *
     * @param list receiverId集合
     */
    boolean checkReceiverUserIdIfExist(List<Long> list);

    /**
     * 判断接收组织id在数据库中是否存在
     *
     * @param list receiverId集合
     */
    boolean checkReceiverOrgIdIfExist(List<Long> list);

    /**
     * 根据发送id和接收者id查询消息接收表实体
     *
     * @param sendId     发送id
     * @param receiverId 接收者id
     * @return
     */
    TabPbMessageReceive selectTabPbMessageReceiveBySendIdAndReceiverId(@Param("sendId") Long sendId, @Param("receiverId") Long receiverId);

    /**
     * 更新消息接收状态
     *
     * @param sendId     发送id
     * @param receiverId 接收者id
     */
    void updateTabPbMessageReceiveBySendIdAndReceiverId(@Param("sendId") Long sendId, @Param("receiverId") Long receiverId);

    /**
     * 显示未提醒的信息
     *
     * @param receiverId 接收者id
     * @return
     */
    List<MessageSendVO> selectRemindedMessageVOById(Long receiverId);

    /**
     * 更新接受状态未0
     *
     *
     * @param sendIds 消息ids
     * @param receiverId 接收者id
     * @return
     */
    int updateMessageTipStatusBySendIdsAndReceiverId(@Param("sendIds") List<Long> sendIds, @Param("receiverId") Long receiverId);
}
