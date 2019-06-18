package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.HelpRecordTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbHelpRecordTeam;
import com.egovchina.partybuilding.partybuild.vo.HelpRecordTeamVO;

import java.util.List;

public interface TabPbHelpRecordTeamMapper {
    int deleteByPrimaryKey(Long recordTeamId);

    int insert(TabPbHelpRecordTeam record);

    int insertSelective(TabPbHelpRecordTeam record);

    TabPbHelpRecordTeam selectByPrimaryKey(Long recordTeamId);

    int updateByPrimaryKeySelective(TabPbHelpRecordTeam record);

    int updateByPrimaryKey(TabPbHelpRecordTeam record);

    int batchInsertTabPbHelpRecordTeam(List<HelpRecordTeamDTO> helpRecordTeamDTOS);

    int deleteByRecordId(Long recordId);

    List<HelpRecordTeamVO> selectHelpRecordTeamByRecordId(Long recordId);
}