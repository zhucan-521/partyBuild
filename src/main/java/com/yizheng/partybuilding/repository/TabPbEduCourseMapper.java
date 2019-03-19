package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TabPbEduCourseDto;
import com.yizheng.partybuilding.entity.TabPbEduCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduCourseMapper {
    int deleteByPrimaryKey(Long courseId);

    int insert(TabPbEduCourse record);

    int insertSelective(TabPbEduCourse record);

    TabPbEduCourse selectByPrimaryKey(Long courseId);

    int updateByPrimaryKeySelective(TabPbEduCourse record);

    int updateByPrimaryKey(TabPbEduCourse record);

    List<TabPbEduCourseDto> selectAllTabPbEduCourse(TabPbEduCourseDto tabPbEduCourseDto);

    int batchInsertFromEduPositionBusiness(List<TabPbEduCourse> eduPositionCourseList);

    /**
     * 根据数据id批量逻辑删除
     * @param idList
     * @return
     */
    int batchLogicDeleteById(List<Long> idList);

    /**
     * 根据基地id批量逻辑删除
     * @param pId
     * @return
     */
    int batchLogicDeleteByPid(Long pId);

    /**
     * 查询老师名字根据老师id
     * @param teacherId
     * @return
     */
    String teacherNameByTeacherId(Long teacherId);


    /**
     * 根据老师名字查询老师id
     * @param teacherName
     * @return
     */
    Long selectTeacherIdByteacherName(String teacherName);



    /**
     * 评论次数根据课程id获取
     * @param CourseId
     * @return
     */
    int selectPingLunNum(Long CourseId);


    /**
     * 根据课程id添加观看次数
     * @param courseId
     * @return
     */
    int addWatchCount(Long courseId);

}