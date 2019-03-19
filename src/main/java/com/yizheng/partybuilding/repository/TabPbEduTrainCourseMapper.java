package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduTrainCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduTrainCourseMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByTrainId(Long trainId);

    int insert(TabPbEduTrainCourse record);

    int insertSelective(TabPbEduTrainCourse record);

    TabPbEduTrainCourse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbEduTrainCourse record);

    int updateByPrimaryKey(TabPbEduTrainCourse record);

    void insertByBatch(List<TabPbEduTrainCourse> attachmentTables);
}