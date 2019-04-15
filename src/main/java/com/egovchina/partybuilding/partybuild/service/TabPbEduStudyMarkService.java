package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduStudyCenter;

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
