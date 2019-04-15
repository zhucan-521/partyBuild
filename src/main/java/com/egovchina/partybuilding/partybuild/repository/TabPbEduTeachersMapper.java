package com.egovchina.partybuilding.partybuild.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduTeachers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduTeachersMapper extends BaseMapper<TabPbEduTeachers> {
    int deleteByPrimaryKey(Long teacherId);

    int insertAll(TabPbEduTeachers record);

    int insertSelective(TabPbEduTeachers record);

    TabPbEduTeachers selectByPrimaryKey(Long teacherId);

    int updateByPrimaryKeySelective(TabPbEduTeachers record);

    int updateByPrimaryKey(TabPbEduTeachers record);

    int tombstone(TabPbEduTeachers teacherId);

    /**
     * 检查登录名是否重复
     * @param account
     * @return
     */
    int checkAccount(String account);

    /**
     * 查询list数据(带附件信息)
     * @param record
     * @return
     */
    List<TabPbEduTeachers> selectListTeachers(TabPbEduTeachers record);
}