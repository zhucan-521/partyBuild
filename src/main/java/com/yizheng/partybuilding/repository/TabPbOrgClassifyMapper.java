package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbOrgClassify;

import java.util.List;
import java.util.Map;

public interface TabPbOrgClassifyMapper {
    int deleteByPrimaryKey(Long orgClassifyId);

    int insert(TabPbOrgClassify record);

    int insertSelective(TabPbOrgClassify record);

    TabPbOrgClassify selectByPrimaryKey(Long orgClassifyId);

    int updateByPrimaryKeySelective(TabPbOrgClassify record);

    int updateByPrimaryKey(TabPbOrgClassify record);

    List<TabPbOrgClassify> selectByDeptId(Long deptId);

    int logicDeleteById(Long orgClassifyId);

    List<TabPbOrgClassify> selectWithConditions(Map<String, Object> conditions);
}