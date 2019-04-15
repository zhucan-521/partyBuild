package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduIntelligentStudyDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduPositionDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPosition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教育基地service接口
 *
 * @Author Zhang Fan
 **/
public interface TabPbEduPositionService {

    /**
     * 根据条件查询并分页
     * @param conditions
     * @param page
     * @return
     */
    List<TabPbEduPosition> selectWithConditions(Map<String, Object> conditions, Page page);

    /**
     * 逻辑删除
     * @param pId
     * @return
     */
    int logicDeleteById(Long pId);

    /**
     * 新增基地信息
     * @param tabPbEduPosition
     */
    int insertWithAbout(TabPbEduPositionDto tabPbEduPosition);

    int updateWithAbout(TabPbEduPositionDto tabPbEduPosition);

    TabPbEduPositionDto selectWithAboutInfoById(Long id);

    /**
     * 智能选学
     * @param conditions
     * @param page
     * @return
     */
    List<TabPbEduIntelligentStudyDto> selectZLXXWithConditions(HashMap<String, Object> conditions, Page page);

    TabPbEduIntelligentStudyDto selectZLLXWithAboutInfoById(Long id);
}
