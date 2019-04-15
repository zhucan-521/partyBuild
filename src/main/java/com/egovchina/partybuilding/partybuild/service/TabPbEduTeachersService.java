package com.egovchina.partybuilding.partybuild.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTeachers;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTeachersCourse;

import java.util.List;

/**
 * @author YangYingXiang on 2018/12/10
 */
public interface TabPbEduTeachersService extends IService<TabPbEduTeachers> {

    /**
     * 查询list数据
     * @param teachers
     * @param page
     * @return
     */
    List<TabPbEduTeachers> listData(EntityWrapper<TabPbEduTeachers> teachers, Page page);

    /**
     * 添加数据
     * @param teachers
     */
    Long add(TabPbEduTeachers teachers);

    /**
     * 根据主键id查询单条数据
     * @param teacherId
     * @param flag
     * @return TabPbEduTeachers
     */
    TabPbEduTeachers findByIdData(Long teacherId,boolean flag);

    /**
     * 根据主键id修改数据
     * @param teachers
     */
    int edit(TabPbEduTeachers teachers);

    /**
     * 批量删除数据
     * @param teacherId
     */
    int delete(Long teacherId);

    /**
     * 检查用户名是否重复
     * @param account
     * @return
     */
    int checkAccount(String account);

    /**
     * 课程list
     * @return
     */
    List<TabPbEduTeachersCourse> selectList(TabPbEduTeachersCourse course,Page page);

    /**
     * 根据id查询单条课程
     * @param id
     * @return
     */
    TabPbEduTeachersCourse selectByPrimaryKey(Long id);

    /**
     * 逻辑删除课程
     * @param id
     * @return
     */
    int tombstone(Long id);

    /**
     * 修改课程
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(TabPbEduTeachersCourse record);

    /**
     * 添加课程
     * @param record
     * @return
     */
    int insertSelective(TabPbEduTeachersCourse record);
}
