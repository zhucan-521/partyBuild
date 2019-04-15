package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabPbEduSubjectAnswerDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbSubjectForTestDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSubjectfortest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbEduSubjectfortestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TabPbEduSubjectfortest record);

    int insertSelective(TabPbEduSubjectfortest record);

    TabPbEduSubjectfortest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TabPbEduSubjectfortest record);

    int updateByPrimaryKey(TabPbEduSubjectfortest record);

    //根据试卷id获取题目信息
    List<TabPbSubjectForTestDto> getSubjectForTest(@Param("testId") int testId);

    //根据试卷id获取题目答案与分数信息
    List<TabPbEduSubjectAnswerDto> getSubjectAnswerByTestId(@Param("testId") int testId);
}