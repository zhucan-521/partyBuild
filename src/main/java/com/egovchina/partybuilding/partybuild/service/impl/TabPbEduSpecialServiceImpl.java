package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduSpecialDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecial;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecialCourse;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecialTeacher;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduSpecialCourseMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduSpecialMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduSpecialTeacherMapper;
import com.egovchina.partybuilding.partybuild.service.TabPbEduSpecialService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TabPbEduSpecialServiceImpl implements TabPbEduSpecialService {
    @Autowired
    private TabPbEduSpecialMapper tabPbEduSpecialMapper;

    @Autowired
    private TabPbEduSpecialCourseMapper tabPbEduSpecialCourseMapper;

    @Autowired
    private TabPbEduSpecialTeacherMapper tabPbEduSpecialTeacherMapper;

    /**
     * 新增课程体系
     * @param tabPbEduSpecialDto
     * @return
     */
    @Override
    @Transactional
    public int add(TabPbEduSpecialDto tabPbEduSpecialDto) {
        if(tabPbEduSpecialDto.getTitle().isEmpty()){
            throw new BusinessDataIncompleteException("课程名称不能为空");
        }
        if(tabPbEduSpecialDto.getSpecialType() == null){
            throw new BusinessDataIncompleteException("请选择课程类型");
        }
        if(tabPbEduSpecialDto.getRegion() == null){
            throw new BusinessDataIncompleteException("请选择区域");
        }
        if(tabPbEduSpecialDto.getDuration() == null){
            throw new BusinessDataIncompleteException("请选择课程时长");
        }
        if(tabPbEduSpecialDto.getQuantity() == null){
            throw new BusinessDataIncompleteException("请选择接待人数");
        }
        int retVal = 0;
        retVal += tabPbEduSpecialMapper.insertSelective(tabPbEduSpecialDto);
        Long specialId = tabPbEduSpecialDto.getId();
        for(TabPbEduSpecialCourse tabPbEduSpecialCourse : tabPbEduSpecialDto.getTabPbEduSpecialCourses()){
            tabPbEduSpecialCourse.setSpecialId(specialId);
            retVal += tabPbEduSpecialCourseMapper.insertSelective(tabPbEduSpecialCourse);
        }
        for(TabPbEduSpecialTeacher tabPbEduSpecialTeacher : tabPbEduSpecialDto.getTabPbEduSpecialTeachers()){
            tabPbEduSpecialTeacher.setSpecialId(specialId);
            retVal += tabPbEduSpecialTeacherMapper.insertSelective(tabPbEduSpecialTeacher);
        }
        return retVal;
    }

    /**
     * 分页查询课程体系
     * @param conditions
     * @param page
     * @return
     */
    @Override
    public List<TabPbEduSpecialDto> pagingQueryCourseSystem(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduSpecialDto> tabPbEduSpecialDtos= tabPbEduSpecialMapper.queryCourseSystem(conditions);
        return tabPbEduSpecialDtos;
    }

    /**
     * 删除课程体系
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int deleteCourseSystem(Long id) {
        TabPbEduSpecial tabPbEduSpecial = new TabPbEduSpecial();
        tabPbEduSpecial.setId(id);
        tabPbEduSpecial.setDelFlag("1");
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("specialId", id);
        conditions.put("delFlag", "1");
        int retVal = 0;
        retVal += tabPbEduSpecialMapper.updateByPrimaryKeySelective(tabPbEduSpecial);
        retVal += tabPbEduSpecialCourseMapper.updateBySpecialId(conditions);
        retVal += tabPbEduSpecialTeacherMapper.updateBySpecialId(conditions);
        return retVal;
    }

    /**
     * 修改课程体系
     * @param tabPbEduSpecialDto
     * @return
     */
    @Override
    @Transactional
    public int modifyTheCurriculumSystem(TabPbEduSpecialDto tabPbEduSpecialDto) {
        int retVal = 0;
        retVal += tabPbEduSpecialMapper.updateByPrimaryKeySelective(tabPbEduSpecialDto);
        for(TabPbEduSpecialCourse tabPbEduSpecialCourse : tabPbEduSpecialDto.getTabPbEduSpecialCourses()){
            retVal += tabPbEduSpecialCourseMapper.updateByPrimaryKeySelective(tabPbEduSpecialCourse);
        }
        for (TabPbEduSpecialTeacher tabPbEduSpecialTeacher : tabPbEduSpecialDto.getTabPbEduSpecialTeachers()){
            retVal += tabPbEduSpecialTeacherMapper.updateByPrimaryKeySelective(tabPbEduSpecialTeacher);
        }
        return retVal;
    }

    @Override
    public TabPbEduSpecialDto queryCourseSystemById(Long id) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", id);
        conditions.put("delFlag", "0");
        return tabPbEduSpecialMapper.queryCourseSystemById(conditions);
    }
}