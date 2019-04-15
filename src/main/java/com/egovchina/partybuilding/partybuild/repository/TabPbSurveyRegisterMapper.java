package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbSurveyRegister;

public interface TabPbSurveyRegisterMapper {
    int deleteByPrimaryKey(Long surveyRgisterId);

    int insert(TabPbSurveyRegister record);

    int insertSelective(TabPbSurveyRegister record);

    TabPbSurveyRegister selectByPrimaryKey(Long surveyRgisterId);

    int updateByPrimaryKeySelective(TabPbSurveyRegister record);

    int updateByPrimaryKeyWithBLOBs(TabPbSurveyRegister record);

    int updateByPrimaryKey(TabPbSurveyRegister record);
}