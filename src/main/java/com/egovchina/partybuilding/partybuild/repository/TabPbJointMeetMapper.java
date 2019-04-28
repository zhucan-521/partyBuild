package com.egovchina.partybuilding.partybuild.repository;


import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.vo.JointMeetVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbJointMeetMapper {

    TabPbJointMeet selectJointMeetByJointMeetId(Long jointMeetId);

    int updateByPrimaryKeySelective(TabPbJointMeet record);

    int insertSelective(TabPbJointMeet meet);

    TabPbJointMeet selectJointMeetByOrgId(Long orgId);

    /**
     * 根据条件查询
     *
     * @param meet
     * @return
     */
    List<JointMeetVO> selectJointMeetVOList(TabPbJointMeet meet);
}