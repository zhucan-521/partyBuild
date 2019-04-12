package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.*;
import com.yizheng.partybuilding.entity.TabPbActivities;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface TabPbActivitiesMapper {
    int deleteByPrimaryKey(Long activitiesId);

    int insert(TabPbActivities record);

    int insertSelective(TabPbActivities record);

    List<TabPbActivitiesDto> selectByPrimaryKey(TabPbActivitiesDto tabPbActivitiesDto);

    int updateByPrimaryKeySelective(TabPbActivities record);

    int updateByPrimaryKeyWithBLOBs(TabPbActivities record);

    int updateByPrimaryKey(TabPbActivities record);

    void delete(Long activitiesId);

    TabPbActivities selectByActivitiesId(long activitiesId);

    //逻辑删除
    int deleteId(Long activitiesId);

    //条件分页查询
    List<PartyOrganizationActivitiesDto> selectWithConditions(Map<String, Object> conditions);

    //查看单个详情
    PartyOrganizationActivitiesDto findDetails(@Param("activitiesId") Long activitiesId, @Param("attType") Long attType);

    /**
     * 根据条件查询结对共建列表
     *
     * @param conditions
     * @return
     */
    List<TabPbActivitiesDto> selectPairingListWithConditions(HashMap<String, Object> conditions);

    TabPbActivities selectOneById(Long activitiesId);

    int review(TabPbActivities tabPbActivities);

    PartyOrganizationActivitiesDto selectPairingDetail(Long activitiesId);

    /**
     * 查询在职党员活动list
     *
     * @param activitiesDto
     * @return
     */
    List<TabPbActivitiesDto> ActivitiesDtoList(TabPbActivitiesDto activitiesDto);

    int deleteByOrgIdAndActivityType(TabPbActivities tabPbActivities);

    /**
     * 查询活动待选参与者列表
     *
     * @param conditions 查询条件
     * @return
     */
    List<Personnel> selectCandidateMemberListWithConditions(Map<String, Object> conditions);

    /**
     * 查询活动排除参与者列表
     *
     * @param orgId
     * @param pairOrgId
     * @return
     */
    ActivityExclusionParticipantDto selectExclusionMemberList(@Param("orgId") Long orgId, @Param("pairOrgId") Long pairOrgId);

    List<JointMeetUsersDto> selectJoinMeetActivitiesPreUserListWithConditions(Map<String, Object> conditions);

    /**
     * 根据用户id查询参加活动列表
     * @param userId 用户id
     * @return
     */
    List<PartyOrganizationActivitiesDto> selectListByUserId(Long userId);

    /**
     * 点击量
     * @param activitiesId
     */
    void updateOrderNum(Long activitiesId);



    /**
     * 查询最大访问量
     * @return
     */
    Long findByOrderNum();

    /**
     * 置顶功能
     * @param activitiesId
     * @param stickNum
     * @return
     */
    int stick(@Param("activitiesId")Long activitiesId,@Param("stickNum")Long stickNum);

    /**
     * 取消置顶功能
     * @param activitiesId
     * @return
     */
    int deleteStick(Long activitiesId);
}