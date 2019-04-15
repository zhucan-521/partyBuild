package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbLeadTeamMemberDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeamMember;

import java.util.List;
import java.util.Map;

/**
 * @Author Jiang An
 **/

public interface TabPbLeadTeamMemberService {

    int deleteByPrimaryKey(Long memberId);

    int insert(TabPbLeadTeamMember record);

    int insertSelective(TabPbLeadTeamMember tabPbLeadTeamMember);

    TabPbLeadTeamMember selectByPrimaryKey(Long memberId);

    int updateByPrimaryKeySelective(TabPbLeadTeamMember record);

    int updateByPrimaryKeySelective(TabPbLeadTeamMemberDto record);

    int updateByPrimaryKey(TabPbLeadTeamMember record);

    int deleteId(Long memberId);

    /**
     * 根据班子ID查询班子成员列表
     *
     * @param leadTeamId
     * @param page
     * @return
     */
    List<TabPbLeadTeamMember> selectTeamMemberListByTeamId(Long leadTeamId, Page page);

    int deleteMemberByMemberId(Long memberId);

    List<TabPbLeadTeamMemberDto> selectLeadTeamMemberByUser(Map<String, Object> conditions, Page page);

    //查询班子成员
    TabPbLeadTeamMemberDto selectById(Long memberId);

    //按条件查询所有班子成员
    List<TabPbLeadTeamMemberDto> selectByTabPbLeadTeamDto(TabPbLeadTeamMemberDto tabPbLeadTeamMemberDto, Page page);

    //查询上级组织领导班子成员
    List<TabPbLeadTeamMemberDto> queryTheLeaderOfTheSuperiorOrganization(Long deptId,String personName, Page page);
}
