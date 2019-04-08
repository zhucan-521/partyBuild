package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.PartyOrganizationActivitiesDto;
import com.yizheng.partybuilding.entity.TabPbActivities;
import com.yizheng.partybuilding.entity.TabPbParticipant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbParticipantMapper {
    /**
     * 删除
     *
     * @param participantId
     * @return
     */
    int deleteByPrimaryKey(Long participantId);

    int insert(TabPbParticipant record);

    int insertSelective(TabPbParticipant record);

    TabPbParticipant selectByPrimaryKey(Long participantId);

    int updateByPrimaryKeySelective(TabPbParticipant record);

    int updateByPrimaryKeyWithBLOBs(TabPbParticipant record);

    int updateByPrimaryKey(TabPbParticipant record);

    void delect(Long activitiesId);

    /**
     * 根据活动id查询活动所有参与人员
     *
     * @param activitiesId
     * @return
     */
    List<TabPbParticipant> selectDetailTableByActivitiesId(Long activitiesId);

    int batchLogicDelete(List<Long> particiipantIds);

    int batchInsert(List<TabPbParticipant> pendingAddList);

    //查看人数
    PartyOrganizationActivitiesDto findById(long activitiesid);

    int selectCountByParticipant(TabPbParticipant pbParticipant);

    int review(TabPbActivities tabPbActivities);


    /**
     * 查看活动已签到人员跟未签到人的姓名
     *
     * @param activitiesId
     * @return
     */
    List<PartyOrganizationActivitiesDto> selectCheckInList(@Param("activitiesId") Long activitiesId, @Param("signType") Long signType);

    /**
     * 修改签到状态
     *
     * @param activitiesId
     * @param participantId
     * @return
     */
    int updateSignIn(@Param("activitiesId") Long activitiesId, @Param("participantId") Long participantId);

    /**
     * 验证用户是否存在活动
     *
     * @param activitiesId
     * @param idCardNo
     * @return
     */
    TabPbParticipant validateUserExists(@Param("activitiesId") Long activitiesId, @Param("idCardNo") String idCardNo);

}