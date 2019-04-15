package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduPositionCourse;

import java.util.List;

public interface TabPbEduPositionCourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbEduPositionCourse record);

    int insertSelective(TabPbEduPositionCourse record);

    TabPbEduPositionCourse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbEduPositionCourse record);

    int updateByPrimaryKey(TabPbEduPositionCourse record);

    /**
     * 批量新增
     * @param positionCourseList
     * @return
     */
    int batchInsert(List<TabPbEduPositionCourse> positionCourseList);

    /**
     * 根据基地id查询所有基地特色课程
     * @param positionId
     * @return
     */
    List<TabPbEduPositionCourse> selectAllByPositionId(Long positionId);

    /**
     * 根据数据id批量逻辑删除
     * @param idList
     * @return
     */
    int batchLogicDeleteById(List<Long> idList);

    /**
     * 根据基地id批量逻辑删除
     * @param positionId
     * @return
     */
    int batchLogicDeleteByPositionId(Long positionId);
}