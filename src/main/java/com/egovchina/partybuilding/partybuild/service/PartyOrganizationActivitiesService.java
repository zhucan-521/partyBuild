package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author Jiang An
 **/
public interface PartyOrganizationActivitiesService {

    //添加活动
    int insertSelective(PartyOrganizationActivitiesDto record);

    //查询单个详情
    PartyOrganizationActivitiesDto findDetails(Long activitiesId, Long activitiesType);

    //逻辑删除
    int delete(Long activitiesId);

    //条件分页查询
    List<PartyOrganizationActivitiesDto> selectWithConditions(Map<String, Object> conditions, Page page);

    int updateWithAboutInfo(PartyOrganizationActivitiesDto activitiesDto);

    //签到
    int signByQRCode(Long activitiesId, String idCardNo);

    int insertPairWithAbout(PartyOrganizationActivitiesDto record);

    //参与人
    List<Personnel> selectCandidateMemberList(Map<String, Object> conditions);

    int review(TabPbActivities tabPbActivities);

    /**
     * 修改结对共建
     *
     * @param record
     * @return
     */
    int updatePairWithAbout(PartyOrganizationActivitiesDto record);

    /**
     * 根据条件查询结对列表
     *
     * @param conditions
     * @param page
     * @return
     */
    List<TabPbActivitiesDto> selectPairingListWithConditions(HashMap<String, Object> conditions, Page page);

    /**
     * 结对共建详情
     *
     * @param activitiesId 活动ID
     * @return
     */
    PartyOrganizationActivitiesDto selectPairingDetail(Long activitiesId);

    /**
     * 根据ID查询单个
     *
     * @param activitiesId
     * @return
     */
    TabPbActivities selectOneById(Long activitiesId);

    /**
     * 查询指定组织和其结对组织初始信息
     *
     * @param orgId     组织id
     * @param pairOrgId 结对组织id
     * @return
     */
    ActivityExclusionParticipantDto selectExclusionMemberList(Long orgId, Long pairOrgId);

    /**
     * 根据条件查询联席会活动候选参会人员列表
     *
     * @param conditions
     * @param page
     * @return
     */
    List<JointMeetUsersDto> selectJoinMeetActivitiesPreUserListWithConditions(Map<String, Object> conditions, Page page);


    /**
     * 查看签到跟未签到人的姓名
     *
     * @param activitiesId
     * @return
     */
    List<PartyOrganizationActivitiesDto> selectCheckInList(Long activitiesId, Long signType, Page page);

    /**
     * 修改签到状态
     *
     * @param activitiesId
     * @param participantId
     * @return
     */
    int updateSignIn(Long activitiesId, Long participantId);

    /**
     * 根据用户id查询活动列表数据
     *
     * @param userId 用户id
     * @param page   分页对象
     * @return
     */
    List<PartyOrganizationActivitiesDto> selectListByUserId(Long userId, Page page);

    /**
     * 置顶功能
     * @param activitiesId
     * @return
     */
    int stick(Long activitiesId);

    /**
     * 取消置顶功能
     * @param activitiesId
     * @return
     */
    int deleteStick(Long activitiesId);

    /**
     *签到加人接口
     * @param activitiesId
     * @param idCardNo
     * @return
     */
    int addSignIn(Long activitiesId, String idCardNo);

    /**
     * 在职党员活动list数据
     * @param activitiesDto
     * @return
     */
    List<TabPbActivitiesDto> ActivitiesDtoList(TabPbActivitiesDto activitiesDto, Page page);
}
