package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduStudyCenter;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduStudyMark;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TabPbEduStudyMarkMapper {
    int deleteByPrimaryKey(Long markId);

    int insert(TabPbEduStudyMark record);

    int insertSelective(TabPbEduStudyMark record);

    TabPbEduStudyMark selectByPrimaryKey(Long markId);

    int updateByPrimaryKeySelective(TabPbEduStudyMark record);

    int updateByPrimaryKey(TabPbEduStudyMark record);

    TabPbEduStudyMark selectMarkHistory(@Param("userId") Integer userId, @Param("studyId") Long studyId);

    List<TabPbEduStudyCenter> selectWithConditions(Map<String, Object> conditions);
}