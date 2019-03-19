package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbEduSiteDevice;

import java.util.List;

public interface TabPbEduSiteDeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbEduSiteDevice record);

    int insertSelective(TabPbEduSiteDevice record);

    TabPbEduSiteDevice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbEduSiteDevice record);

    int updateByPrimaryKey(TabPbEduSiteDevice record);

    /**
     * 根据站点id查询关联设备信息
     * @param siteId
     * @return
     */
    List<TabPbEduSiteDevice> selectAllBySiteId(Long siteId);

    /**
     * 根据数据id批量删除
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