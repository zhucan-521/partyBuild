package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbSubjectTag;

import java.util.List;
import java.util.Map;

public interface TabPbSubjectTagMapper {
    int deleteByPrimaryKey(Long tagId);

    int insert(TabPbSubjectTag tabPbSubjectTag);

    int insertSelective(TabPbSubjectTag tabPbSubjectTag);

    TabPbSubjectTag selectByPrimaryKey(Long tagId);

    int updateByPrimaryKeySelective(TabPbSubjectTag record);

    int updateByPrimaryKey(TabPbSubjectTag record);

    /**
     * 逻辑删除
     *
     * @param tagId
     * @return
     */
    int deleteByTagId(Long tagId);

    /**
     * 根据活动类型查询主题标签
     *
     * @param
     * @return
     */
    List<TabPbSubjectTag> findByRange(List<Integer> list);

    /**
     * 查询单个主题标签详情
     *
     * @param tagId
     * @return
     */
    TabPbSubjectTag selectByTagId(Long tagId);

    /**
     * 分页查询标签
     * @param conditions
     * @return
     */
    List<TabPbSubjectTag> findByList(Map<String, Object> conditions);
}