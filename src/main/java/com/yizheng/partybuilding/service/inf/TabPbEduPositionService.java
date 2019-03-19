package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduIntelligentStudyDto;
import com.yizheng.partybuilding.dto.TabPbEduPositionDto;
import com.yizheng.partybuilding.entity.TabPbEduPosition;

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
