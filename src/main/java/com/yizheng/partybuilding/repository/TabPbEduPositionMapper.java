package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TabPbEduIntelligentStudyDto;
import com.yizheng.partybuilding.dto.TabPbEduPositionDto;
import com.yizheng.partybuilding.entity.TabPbEduPosition;

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