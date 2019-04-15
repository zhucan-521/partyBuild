package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabPbLeadTeamMemberDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeamMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabPbLeadTeamMemberMapper {
    int deleteByPrimaryKey(Long memberId);

    int insert(TabPbLeadTeamMember record);

    int insertSelective(TabPbLeadTeamMember record);

    TabPbLeadTeamMember selectByPrimaryKey(Long memberId);

    int updateByPrimaryKeySelective(TabPbLeadTeamMember record);

    int updateByPrimaryKey(TabPbLeadTeamMember record);

    int deleteId(Long memberId);

    //查询所有领导班子成员
    List<TabPbLeadTeamMember> selectTeamMemberListByTeamId(Long leadTeamId);

    int deleteMemberByMemberId(Long memberId);

    /**
     * 通过姓名,职务查询社区兼职委员列表
     *
     * @param tabPbLeadTeamMember
     * @return
     */
    List<TabPbLeadTeamMemberDto> selectLeadTeamMemberByUser(Map<String, Object> tabPbLeadTeamMember);

    TabPbLeadTeamMemberDto selectById(Long memberId);

    //按条件查询班子成员
    List<TabPbLeadTeamMemberDto> selectByTabPbLeadTeamDto(TabPbLeadTeamMemberDto tabPbLeadTeamMemberDto);

    void updateByTabPbLeadTeam(Long leadTeamId);

    //查询上级组织领导班子成员
    List<TabPbLeadTeamMemberDto> queryTheLeaderOfTheSuperiorOrganization(@Param("deptId") Long deptId,@Param("personName") String personName);

    Long selectBycount(Long leadTeamId);


    Long selectByLeadTeamId(Long memberId);

    /**
     * 查询成员是否重复添加
     * @param leadTeamId
     * @param userId
     * @return
     */
    List<TabPbLeadTeamMember> selectByUserId(@Param("leadTeamId") Long leadTeamId, @Param("userId")Long userId);

    List<TabPbLeadTeamMember> queryTeamMembers(Long leadTeamId);
}