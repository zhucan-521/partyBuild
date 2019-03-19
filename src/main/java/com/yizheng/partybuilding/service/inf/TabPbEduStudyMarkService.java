package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduStudyCenter;

import java.util.List;
import java.util.Map;

/**
 * 学习记录service接口
 *
 * @Author Zhang Fan
 **/
public interface TabPbEduStudyMarkService {
    /**
     * 记录
     * @param userId
     * @param studyId
     * @return
     */
    int mark(Integer userId, Long studyId);

    /**
     * 根据条件查询
     * @param conditions
     * @param page
     * @return
     */
    List<TabPbEduStudyCenter> selectWithConditions(Map<String, Object> conditions, Page page);
}
