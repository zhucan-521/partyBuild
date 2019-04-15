package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduSiteDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSite;

import java.util.List;
import java.util.Map;

/**
 * 远教站点service层
 *
 * @Author Zhang Fan
 **/
public interface TabPbEduSiteService {
    /**
     * 根据条件查询
     *
     * @param conditions 条件集
     * @param page
     * @return
     */
    List<TabPbEduSite> selectWithConditions(Map<String, Object> conditions, Page page);

    TabPbEduSiteDto selectWithAboutInfoById(Long id);

    int logicDeleteById(Long id);

    int insertWithAbout(TabPbEduSiteDto tabPbEduSite);

    int updateWithAbout(TabPbEduSiteDto tabPbEduSite);
}
