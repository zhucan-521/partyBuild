package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduAnswerforsubject;

public interface TabPbEduAnswerforsubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TabPbEduAnswerforsubject record);

    int insertSelective(TabPbEduAnswerforsubject record);

    TabPbEduAnswerforsubject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TabPbEduAnswerforsubject record);

    int updateByPrimaryKey(TabPbEduAnswerforsubject record);
}