package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduSiteActivity;

import java.util.List;

public interface TabPbEduSiteActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbEduSiteActivity record);

    int insertSelective(TabPbEduSiteActivity record);

    TabPbEduSiteActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbEduSiteActivity record);

    int updateByPrimaryKey(TabPbEduSiteActivity record);

    /**
     * 根据站点id查询所有数据
     * @param siteId 站点id
     * @return
     */
    List<TabPbEduSiteActivity> selectAllBySiteId(Long siteId);

    /**
     * 根据id批量删除
     * @param pendingRemoveIdList
     * @return
     */
    int batchLogicDeleteById(List<Long> pendingRemoveIdList);

    /**
     * 根据站点id批量删除
     * @param siteId
     * @return
     */
    int batchLogicDeleteBySiteId(Long siteId);
}