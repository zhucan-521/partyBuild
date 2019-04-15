package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabPbEduIntelligentStudyDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduPositionDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPosition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TabPbEduPositionMapper {
    int deleteByPrimaryKey(Long pId);

    int insert(TabPbEduPosition record);

    int insertSelective(TabPbEduPosition record);

    TabPbEduPosition selectByPrimaryKey(Long pId);

    int updateByPrimaryKeySelective(TabPbEduPosition record);

    int updateByPrimaryKey(TabPbEduPosition record);

    List<TabPbEduPosition> selectWithConditions(Map<String, Object> conditions);

    int logicDeleteById(Long pId);

    TabPbEduPositionDto selectWithAboutInfoById(Long pId);

    List<TabPbEduIntelligentStudyDto> selectZLXXWithConditions(HashMap<String, Object> conditions);

    TabPbEduIntelligentStudyDto selectZLXXWithAboutInfoById(Long id);
}