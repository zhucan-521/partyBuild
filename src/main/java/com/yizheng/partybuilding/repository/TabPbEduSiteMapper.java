package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TabPbEduSiteDto;
import com.yizheng.partybuilding.entity.TabPbEduSite;

import java.util.List;
import java.util.Map;

public interface TabPbEduSiteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbEduSite record);

    int insertSelective(TabPbEduSite record);

    TabPbEduSite selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbEduSite record);

    int updateByPrimaryKey(TabPbEduSite record);

    /**
     * 根据条件查询
     *
     * @param conditions
     * @return
     */
    List<TabPbEduSite> selectWithConditions(Map<String, Object> conditions);

    /**
     * 根据id查询详情包含相关信息
     *
     * @param id 数据id
     * @return
     */
    TabPbEduSiteDto selectWithAboutInfoById(Long id);

    /**
     * 根据id逻辑删除
     *
     * @param id 数据id
     * @return
     */
    int logicDeleteById(Long id);
}