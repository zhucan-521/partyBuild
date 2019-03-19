package com.yizheng.partybuilding.repository;


import com.yizheng.partybuilding.entity.TabPbJointMeet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbJointMeetMapper {

    TabPbJointMeet selectByPrimaryKey(Long jointMeetId);

    int updateByPrimaryKeySelective(TabPbJointMeet record);

    int insertSelective(TabPbJointMeet meet);

    TabPbJointMeet selectByOrgId(Long orgId);

    /**
     * 根据一些条件查询
     *
     * @param meet
     * @return
     */
    List<TabPbJointMeet> select(TabPbJointMeet meet);

}