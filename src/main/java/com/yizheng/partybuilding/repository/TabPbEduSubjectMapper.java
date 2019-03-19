package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduSubject;

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