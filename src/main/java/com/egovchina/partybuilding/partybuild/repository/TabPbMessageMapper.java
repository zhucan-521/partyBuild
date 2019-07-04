package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.vo.*;
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
     * 定时发送消息
     *
     * @param tabPbMessageReceive 定时发送消息实体
     * @return
     */
    int insertTabPbMessageReceive(TabPbMessageReceive tabPbMessageReceive);

    /**
     * 将消息批量插入消息接收表
     *
     * @param tabPbMessageReceiveList 消息实体集合
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
     * 账号获取自己所管理组织的消息列表
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
     * @param receiverId    接收者id
     * @param rangeDeptId   消息触发组织id
     * @param orgRange      组织范围
     * @return
     */
    List<MessageSendVO> selectRemindedMessageVOById(@Param("receiverId") Long receiverId, @Param("rangeDeptId") Long rangeDeptId, @Param("orgRange") Long orgRange);

    /**
     * 更新接受状态未0
     *
     * @param sendIds    消息ids
     * @param receiverId 接收者id
     * @return
     */
    int updateMessageTipStatusBySendIdsAndReceiverId(@Param("sendIds") List<Long> sendIds, @Param("receiverId") Long receiverId);

    /**
     * 获取党员生日列表
     *
     * @return
     */
    List<PartyMemberBirthDayVO> selectPartyMemberBirthDay();

    /**
     * 获取消息内容
     *
     * @param id 字典id
     * @return
     */
    String selectMessageContent(Long id);

    /**
     * 查询前一天是否有活动存在
     *
     * @param
     * @return
     */
    int selectIsThereAnActivity();

    /**
     * 查询前一天的活动id跟orgid
     *
     * @return
     */
    List<ActivityVO> selectByActivityVO();

    /**
     * 查询前一天活动的快照
     *
     * @param activitiesId
     * @return
     */
    PartyOrganizeActivitiesVO selectOrganizationActivityVOById(Long activitiesId);

    /**
     * 获取活动已经存在的人
     *
     * @param activitiesId
     * @return
     */
    List<PersonnelEntityVO> selectParticipantList(Long activitiesId);

    /**
     * 添加人员
     *
     * @param tabPbParticipant
     */
    int addPersonnel(TabPbParticipant tabPbParticipant);

    /**
     * 提醒党员参加月份活动
     *
     * @return
     */
    List<ParticipateInTheActivityVO> selectParticipateInTheActivityVOByMonth();

    /**
     * 定时提醒党员参加季度活动
     *
     * @return
     */
    List<ParticipateInTheActivityVO> selectParticipateInTheActivityVOByQuarter();

    /**
     * 提醒党员参加年度活动
     *
     * @return
     */
    List<ParticipateInTheActivityVO> selectParticipateInTheActivityVOByYear();

    /**
     * 获取消息提醒时间
     *
     * @return
     */
    String selectActivityRemindDateIfEqualNow(Long id);

    /**
     * 查询前天已完成的党群活动
     *
     * @param excludeJoined 是否排除参加的人
     * @return
     */
    List<PartyMassesActivitySnapshotVO> selectFinishedPartyMassesActivity(@Param("excludeJoined") boolean excludeJoined);

    /**
     * 批量修改党群活动快照
     *
     * @param list
     * @return
     */
    int batchUpdateSnapshot(List<TabPbPartyMassesActivity> list);

    /**
     * 批量新增党群活动未参与人
     *
     * @param tabPbPartyMassesParticipantList 党群活动未参加人
     * @return
     */
    int batchInsertPartyMassesActivityParticipant(List<TabPbPartyMassesParticipant> tabPbPartyMassesParticipantList);

}
