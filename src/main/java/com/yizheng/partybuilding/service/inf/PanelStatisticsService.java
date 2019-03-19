package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.PanelStatistics;
import com.yizheng.partybuilding.entity.PanelStatisticsDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 面板统计service接口
 *
 * @Author Zhang Fan
 **/
public interface PanelStatisticsService {

    /**
     * 计算支部活动相关工作情况
     * @param conditions 查询条件集
     * @return
     */
    List<PanelStatistics> calculateBranchWorkInfoForActivity(Map<String, Object> conditions);

    /**
     * 计算非支部活动相关工作情况
     * @param conditions 查询条件集
     * @return
     */
    List<PanelStatistics> calculateUnBranchWorkInfoForActivity(Map<String, Object> conditions);

    /**
     * 查询活动照片墙数据
     * @param orgId 组织id
     * @param limit 数量限制
     * @return
     */
    List<HashMap<String, Object>> selectActivityPhotos(Long orgId, Integer limit);

    /**
     * 查询活动相关工作列表
     * @param conditions
     * @param page
     * @return
     */
    List<PanelStatisticsDetail> selectDetailWorkInfoForActivity(Map<String, Object> conditions, Page page);
}
