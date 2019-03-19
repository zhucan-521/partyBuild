package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbSurvey;

public interface TabPbSurveyMapper {
    int deleteByPrimaryKey(Long surveyId);

    int insert(TabPbSurvey record);

    int insertSelective(TabPbSurvey record);

    TabPbSurvey selectByPrimaryKey(Long surveyId);

    int updateByPrimaryKeySelective(TabPbSurvey record);

    int updateByPrimaryKeyWithBLOBs(TabPbSurvey record);

    int updateByPrimaryKey(TabPbSurvey record);
}