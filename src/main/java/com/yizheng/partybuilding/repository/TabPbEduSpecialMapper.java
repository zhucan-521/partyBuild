package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TabPbEduSpecialDto;
import com.yizheng.partybuilding.entity.TabPbEduSpecial;

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