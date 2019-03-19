package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduCourseDto;
import com.yizheng.partybuilding.entity.TabPbEduCourse;

import com.yizheng.partybuilding.repository.TabPbEduCourseMapper;
import com.yizheng.partybuilding.repository.TabPbEduLikeMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.TabPbEduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabPbEduCourseServiceImpl implements TabPbEduCourseService {

    @Autowired
    TabPbEduCourseMapper tabPbEduCourseMapper;

    @Autowired
    ITabPbAttachmentService  iTabPbAttachmentService;

    @Autowired
    TabPbEduLikeMapper tabPbEduLikeMapper;


    /**
     * 添加课程，返回主键Id
     * @param tabPbEduCourse
     * @return 课程主键Id
     */
    @Override
    public Long insertTabPbEduCourse(TabPbEduCourse tabPbEduCourse) {
        if(null == tabPbEduCourse.getName()){
            throw new BusinessDataIncompleteException("课程名称不能为空！");
        }
        tabPbEduCourseMapper.insertSelective(tabPbEduCourse);
        return tabPbEduCourse.getCourseId();
    }

    /**
     * 根据课程名字查询课程
     * @param
     * @return
     */
    @Override
    public PageInfo<TabPbEduCourseDto> selectAllTabPbEduCourse(TabPbEduCourseDto tabPbEduCourseDto, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduCourseDto> list= tabPbEduCourseMapper.selectAllTabPbEduCourse(tabPbEduCourseDto);
        for(TabPbEduCourseDto dto:list){
            dto.setTeacherName(tabPbEduCourseMapper.teacherNameByTeacherId(dto.getTeacherId()));
            dto.setContentCount(tabPbEduCourseMapper.selectPingLunNum(dto.getCourseId()));
            dto.setLikeCount(tabPbEduLikeMapper.selectLikeCountByBusId(dto.getCourseId())) ;
        }
        PageInfo<TabPbEduCourseDto> pageInfo=new PageInfo(list);
        return pageInfo;
    }

    /**
     * 逻辑删除
     * @param CourseId
     * @return
     */
    @Override
    public int deleteByCouresId(Long CourseId) {
        TabPbEduCourse course =new TabPbEduCourse();
        course.setCourseId(CourseId);
        course.setDelFlag("1");
        return tabPbEduCourseMapper.updateByPrimaryKeySelective( course);
    }

    /**
     * 修改
     * @param dto
     * @return
     */
    @Override
    public int updateTabPbEduCourseDtoByCouresId(TabPbEduCourseDto dto) {
        if(null==dto.getCourseId()){
            throw new BusinessDataIncompleteException("请填写courseId！");
        }
        TabPbEduCourse course=new TabPbEduCourse();
        BeanUtils.copyProperties(dto,course);
        return tabPbEduCourseMapper.updateByPrimaryKeySelective(course);
    }

    /**
     * 根据课程id添加观看次数
     * @param courseId
     * @return
     */
    @Override
    public int addwatchCount(Long courseId) {
        return tabPbEduCourseMapper.addWatchCount(courseId);
    }


}
