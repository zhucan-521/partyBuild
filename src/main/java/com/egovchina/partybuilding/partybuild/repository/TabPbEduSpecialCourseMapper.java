package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecialCourse;

import java.util.Map;

public interface TabPbEduSpecialCourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbEduSpecialCourse record);

    int insertSelective(TabPbEduSpecialCourse record);

    TabPbEduSpecialCourse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbEduSpecialCourse record);

    int updateByPrimaryKey(TabPbEduSpecialCourse record);

    int updateBySpecialId(Map<String, Object> conditions);
}