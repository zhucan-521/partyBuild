package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.HelpTeamMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbHelpTeamMember;
import com.egovchina.partybuilding.partybuild.vo.HelpTeamMemberVO;

import java.util.List;

public interface TabPbHelpTeamMemberMapper {
    int deleteByPrimaryKey(Long memberId);

    int deleteByteamId(Long teamId);

    int insert(TabPbHelpTeamMember record);

    int insertSelective(TabPbHelpTeamMember record);

    TabPbHelpTeamMember selectByPrimaryKey(Long memberId);

    int updateByPrimaryKeySelective(TabPbHelpTeamMember record);

    int updateByPrimaryKey(TabPbHelpTeamMember record);

    /**
     * 批量新增
     *
     * @param tabPbHelpTeamMembers
     * @return
     */
    int batchInsert(List<HelpTeamMemberDTO> tabPbHelpTeamMembers);

    /**
     * 根据帮扶队伍主键查询队伍成员
     *
     * @param TeamId
     * @return
     */
    List<HelpTeamMemberVO> selectHelpTeamMemberVOByTeamId(Long TeamId);

}