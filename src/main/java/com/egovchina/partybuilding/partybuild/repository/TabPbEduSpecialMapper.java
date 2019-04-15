package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabPbEduSpecialDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecial;

import java.util.List;
import java.util.Map;

public interface TabPbEduSpecialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbEduSpecial record);

    int insertSelective(TabPbEduSpecial record);

    TabPbEduSpecial selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbEduSpecial record);

    int updateByPrimaryKey(TabPbEduSpecial record);

    List<TabPbEduSpecialDto> queryCourseSystem(Map<String, Object> conditions);

    TabPbEduSpecialDto queryCourseSystemById(Map<String, Object> conditions);
}