package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduStudyCenter;

import java.util.List;
import java.util.Map;

public interface TabPbEduStudyCenterMapper {
    int deleteByPrimaryKey(Long studyId);

    int insert(TabPbEduStudyCenter record);

    int insertSelective(TabPbEduStudyCenter record);

    TabPbEduStudyCenter selectByPrimaryKey(Long studyId);

    int updateByPrimaryKeySelective(TabPbEduStudyCenter record);

    int updateByPrimaryKeyWithBLOBs(TabPbEduStudyCenter record);

    int updateByPrimaryKey(TabPbEduStudyCenter record);

    List<TabPbEduStudyCenter> selectWithConditions(Map<String, Object> conditions);

    int logicDeleteById(Long studyId);

    TabPbEduStudyCenter selectWithAboutInfoById(Long studyId);

    int like(Long studyId);

    int watch(Long studyId);
}