package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbSubjectTag;

import java.util.List;
import java.util.Map;

/**
 * @Author Jiang An
 **/
public interface TabPbSubjectTagService {

    /**
     * 删除标签
     *
     * @param tagId
     * @return
     */
    int deleteByTagId(Long tagId);

    /**
     * 添加标签
     *
     * @param
     * @return
     */
    int add(TabPbSubjectTag subjectTag);

    /**
     * 查询单个标签详情
     *
     * @param tagId
     * @return
     */
    TabPbSubjectTag selectByTagId(Long tagId);

    /**
     * 修改标签
     *
     * @param subjectTag
     * @return
     */
    int update(TabPbSubjectTag subjectTag);


    /**
     * 根据活动类型查询主题标签
     *
     * @param list
     * @return
     */
    List<TabPbSubjectTag> findByRange(List<Integer> list);

    /**
     * 分页查询标签
     * @param conditions
     * @return
     */
    List<TabPbSubjectTag> findByList(Map<String,Object> conditions, Page page);
}
