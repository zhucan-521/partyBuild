package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbSubjectTag;

public interface TabPbSubjectTagMapper {
    int deleteByPrimaryKey(Long tagId);

    int insert(TabPbSubjectTag record);

    int insertSelective(TabPbSubjectTag record);

    TabPbSubjectTag selectByPrimaryKey(Long tagId);

    int updateByPrimaryKeySelective(TabPbSubjectTag record);

    int updateByPrimaryKey(TabPbSubjectTag record);
}