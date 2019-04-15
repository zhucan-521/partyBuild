package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduTestarrange;

public interface TabPbEduTestarrangeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TabPbEduTestarrange record);

    int insertSelective(TabPbEduTestarrange record);

    TabPbEduTestarrange selectById(Integer id);

    int updateByPrimaryKeySelective(TabPbEduTestarrange record);

    int updateByPrimaryKey(TabPbEduTestarrange record);
}