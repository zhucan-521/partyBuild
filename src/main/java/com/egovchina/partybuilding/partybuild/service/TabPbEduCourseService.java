package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduCourseDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduCourse;
import com.github.pagehelper.PageInfo;



/**
 * @author zhucan
 */
public interface TabPbEduCourseService {

    /**
     * 添加课程，返回主键Id
     * @param tabPbEduCourse
     * @return 课程主键Id
     */
    Long insertTabPbEduCourse(TabPbEduCourse tabPbEduCourse);


    /**
     * 根据课程名字查询课程
     * @param
     * @return
     */
    PageInfo<TabPbEduCourseDto> selectAllTabPbEduCourse(TabPbEduCourseDto tabPbEduCourseDto , Page page);

    /**
     * 逻辑删除
     * @param CourseId
     * @return
     */
   int deleteByCouresId(Long CourseId);

    /**
     * 修改
     * @param dto
     * @return
     */
   int updateTabPbEduCourseDtoByCouresId(TabPbEduCourseDto dto);


    /**
     * 根据课程id添加观看次数
     * @param courseId
     * @return
     */
   int addwatchCount(Long courseId);






}