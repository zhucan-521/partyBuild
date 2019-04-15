package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbEduTestMapper {
    int deleteById(Integer id);

    int insert(TabPbEduTest record);

    int insertSelective(TabPbEduTest record);

    TabPbEduTest selectById(Integer id);

    int updateByIdSelective(TabPbEduTest record);

    int updateById(TabPbEduTest record);

    List<TabPbEduTest> getTests(@Param("category") int category, @Param("title") String title, @Param("type") int type, @Param("difficulty") int difficulty);
}