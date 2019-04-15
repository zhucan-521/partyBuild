package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduSubject;

import java.util.List;

public interface TabPbEduSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TabPbEduSubject record);

    int insertSelective(TabPbEduSubject record);

    TabPbEduSubject selectById(Integer id);

    int updateByIdSelective(TabPbEduSubject record);

    int updateByPrimaryKey(TabPbEduSubject record);

    List<TabPbEduSubject> getSubjects(TabPbEduSubject subject);

}