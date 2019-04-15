package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecialTeacher;

import java.util.Map;

public interface TabPbEduSpecialTeacherMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbEduSpecialTeacher record);

    int insertSelective(TabPbEduSpecialTeacher record);

    TabPbEduSpecialTeacher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbEduSpecialTeacher record);

    int updateByPrimaryKey(TabPbEduSpecialTeacher record);

    int updateBySpecialId(Map<String, Object> conditions);
}