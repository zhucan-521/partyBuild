package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduTeachersCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduTeachersCourseMapper {
    /**
     * 根据老师id删除明细
     * @param teachersId
     * @return
     */
    int deleteByPrimaryKey(Long teachersId);

    int insert(TabPbEduTeachersCourse record);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(TabPbEduTeachersCourse record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    TabPbEduTeachersCourse selectByPrimaryKey(Long id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(TabPbEduTeachersCourse record);

    int updateByPrimaryKeyWithBLOBs(TabPbEduTeachersCourse record);

    int updateByPrimaryKey(TabPbEduTeachersCourse record);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int tombstone(TabPbEduTeachersCourse id);

    List<TabPbEduTeachersCourse> selectList(TabPbEduTeachersCourse record);
}