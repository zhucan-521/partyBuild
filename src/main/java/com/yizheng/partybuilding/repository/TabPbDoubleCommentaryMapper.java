package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbDoubleCommentary;

import java.util.List;
import java.util.Map;

public interface TabPbDoubleCommentaryMapper {
    int deleteByPrimaryKey(Long commentaryId);

    int insert(TabPbDoubleCommentary record);

    int insertSelective(TabPbDoubleCommentary record);

    TabPbDoubleCommentary selectByPrimaryKey(Long commentaryId);

    int updateByPrimaryKeySelective(TabPbDoubleCommentary record);

    int updateByPrimaryKeyWithBLOBs(TabPbDoubleCommentary record);

    int updateByPrimaryKey(TabPbDoubleCommentary record);

    int logicDeleteById(Long commentaryId);

    List<TabPbDoubleCommentary> selectWithConditions(Map<String, Object> conditions);
}