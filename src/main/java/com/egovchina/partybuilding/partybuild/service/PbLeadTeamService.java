package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbLeadTeamDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeam;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author Jiang An
 **/
public interface PbLeadTeamService {

    int deleteByPrimaryKey(Long leadTeamId);

    int insert(TabPbLeadTeam record) throws ParseException;

    int insertSelective(TabPbLeadTeam record);

    TabPbLeadTeamDto selectByPrimaryKey(Long leadTeamId);

    int updateByPrimaryKeySelective(TabPbLeadTeam record);

    int updateByPrimaryKeyWithBLOBs(TabPbLeadTeam record);

    int updateByPrimaryKey(TabPbLeadTeam record);

    //带条件查询分页
    List<TabPbLeadTeamDto> select(Map<String, Object> conditions, Page page);

    int deleteId(Long leadTeamId);

    int updateWithAboutInfo(TabPbLeadTeam tabPbLeadTeam);
}
