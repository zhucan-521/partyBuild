package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.dto.AssessUserDto;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbActivitiesAssessDto;
import com.yizheng.partybuilding.entity.TabPbActivitiesAssess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 民主评议service
 *
 * @Author Zhang Fan
 **/
public interface TabPbActivitiesAssessService {

    int deleteByPrimaryKey(Long id);

    int insert(TabPbActivitiesAssess record);

    int insertSelective(TabPbActivitiesAssess record);

    TabPbActivitiesAssess selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbActivitiesAssess record);

    int updateByPrimaryKey(TabPbActivitiesAssess record);

    /**
     * 根据条件查询
     * @param conditions
     * @param page
     * @return
     */
    List<TabPbActivitiesAssessDto> selectWithConditions(Map<String, Object> conditions, Page page);

    TabPbActivitiesAssess selectByUserIdAndYear(Long userId, Integer year);

    int logicDeleteById(Long id);

    TabPbActivitiesAssessDto detailWithAbout(Long id);

    int insertWithAbout(TabPbActivitiesAssessDto tabPbActivitiesAssess);

    int updateWithAbout(TabPbActivitiesAssess tabPbActivitiesAssess);

    /**
     * 待评议党员列表
     * @param conditions
     * @return
     */
    HashMap<String, List<AssessUserDto>> preAssessMemberList(Map<String, Object> conditions);

    /**
     * 评议活动签到
     * @param id 数据ID
     * @return
     */
    int signInById(Long id);
}
