package com.egovchina.partybuilding.partybuild.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.MD5Utils;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTeachers;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTeachersCourse;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduTeachersCourseMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduTeachersMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.TabPbEduTeachersService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author YangYingXiang on 2018/12/10
 */
@Service
@Transactional
public class TabPbEduTeachersServiceImpl extends ServiceImpl<TabPbEduTeachersMapper, TabPbEduTeachers> implements TabPbEduTeachersService {

    @Autowired
    private TabPbEduTeachersMapper teachersMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    private TabPbEduTeachersCourseMapper courseMapper;

    @Override
    public List<TabPbEduTeachers> listData(EntityWrapper<TabPbEduTeachers> teachers, Page page) {
        if (page != null) {
            PageHelper.startPage(page);
        }
        return this.teachersMapper.selectListTeachers(teachers.getEntity());
    }

    @Override
    @PaddingBaseField(recursive = true)
    public Long add(TabPbEduTeachers teachers) {
        if (teachers.getPassword() != null && !"".equals(teachers.getPassword())) {
            teachers.setPassword(MD5Utils.getMD5String(teachers.getPassword()));
        }
        this.teachersMapper.insertSelective(teachers);
        iTabPbAttachmentService.intelligentOperation(teachers.getAttachments(), teachers.getTeacherId(), AttachmentType.TEACHERS_MANAGER);
        if (teachers.getCourseList() != null) {
            for (TabPbEduTeachersCourse record : teachers.getCourseList()) {
                if (record.getTitle() != null && !"".equals(record.getTitle())) {
                    record.setTeacherId(teachers.getTeacherId());
                    insertSelective(record);
                }
            }
        }
        return teachers.getTeacherId();
    }

    @Override
    public TabPbEduTeachers findByIdData(Long teacherId, boolean flag) {
        TabPbEduTeachers tabPbEduTeachers = teachersMapper.selectByPrimaryKey(teacherId);
        tabPbEduTeachers.setAttachments(iTabPbAttachmentService.listByHostId(teacherId, AttachmentType.TEACHERS_MANAGER));
        /**
         * 课程list
         */
        if (flag) {
            tabPbEduTeachers.setCourseList(courseMapper.selectList(new TabPbEduTeachersCourse().setTeacherId(teacherId)));
            /**
             * 根据id和类型取出附件list
             */
            for (TabPbEduTeachersCourse course : tabPbEduTeachers.getCourseList()) {
                course.setAttachments(iTabPbAttachmentService.listByHostId(course.getId(), AttachmentType.TEACHERS_COURSE));
            }

        }
        return this.teachersMapper.selectByPrimaryKey(teacherId);
    }

    @Override
    @PaddingBaseField(updateOnly = true)
    public int edit(TabPbEduTeachers teachers) {
        if (teachers.getPassword() != null && !"".equals(teachers.getPassword())) {
            teachers.setPassword(MD5Utils.getMD5String(teachers.getPassword()));
        }
        int retVal = this.teachersMapper.updateByPrimaryKeySelective(teachers);

        if (retVal > 0) {
            iTabPbAttachmentService.intelligentOperation(teachers.getAttachments(), teachers.getTeacherId(), AttachmentType.TEACHERS_MANAGER);
            if (teachers.getCourseList() != null) {
                courseMapper.deleteByPrimaryKey(teachers.getTeacherId());
                for (TabPbEduTeachersCourse record : teachers.getCourseList()) {
                    record.setTeacherId(teachers.getTeacherId());
                    insertSelective(record);
                }
            }
        }
        return retVal;
    }

    @Override
    public int delete(Long teacherId) {
        TabPbEduTeachers tabPbEduTeachers = new TabPbEduTeachers();
        tabPbEduTeachers.setTeacherId(teacherId);
        tabPbEduTeachers.setUpdateTime(new Date());
        tabPbEduTeachers.setUpdateUserid(UserContextHolder.getUserIdLong());
        tabPbEduTeachers.setUpdateUsername(UserContextHolder.getUserName());
        return teachersMapper.tombstone(tabPbEduTeachers);
    }

    @Override
    public int checkAccount(String account) {
        return teachersMapper.checkAccount(account.trim());
    }

    @Override
    public List<TabPbEduTeachersCourse> selectList(TabPbEduTeachersCourse course, Page page) {
        PageHelper.startPage(page);
        return courseMapper.selectList(course);
    }

    @Override
    public TabPbEduTeachersCourse selectByPrimaryKey(Long id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    @Override
    public int tombstone(Long id) {
        TabPbEduTeachersCourse course = new TabPbEduTeachersCourse();
        course.setId(id);
        return courseMapper.tombstone(course);
    }

    @Override
    public int updateByPrimaryKeySelective(TabPbEduTeachersCourse record) {
        int count = courseMapper.updateByPrimaryKeySelective(record);
        if (count > 0) {
            iTabPbAttachmentService.intelligentOperation(record.getAttachments(), record.getId(), AttachmentType.TEACHERS_COURSE);
        }
        return count;
    }

    @Override
    public int insertSelective(TabPbEduTeachersCourse record) {
        int count = courseMapper.insertSelective(record);
        if (count > 0) {
            iTabPbAttachmentService.intelligentOperation(record.getAttachments(), record.getId(), AttachmentType.TEACHERS_COURSE);
        }
        return count;
    }
}
