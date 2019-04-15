package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbJointMeetOrgMapper {

    int insertSelective(TabPbJointMeetOrg record);

    TabPbJointMeetOrg selectByPrimaryKey(Long memberOrgId);

    int updateByPrimaryKeySelective(TabPbJointMeetOrg record);

    int update(TabPbJointMeetOrg record);

    List<TabPbJointMeetOrg> select(TabPbJointMeetOrg org);

    Long selectCheck(@Param("orgId") Long orgId, @Param("orgOrgId") Long orgOrgId);
}