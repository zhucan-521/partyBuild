package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.JointMeetOrgQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import com.egovchina.partybuilding.partybuild.vo.JointMeetOrgVO;
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

    List<JointMeetOrgVO> selectJointMeetOrgVOList(JointMeetOrgQueryBean jointMeetOrgQueryBean);

    int batchInsert(List<TabPbJointMeetOrg> tabPbJointMeetOrgList);

    int batchUpdate(List<TabPbJointMeetOrg> tabPbJointMeetOrgList);
}