package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PanelStatistics;
import com.egovchina.partybuilding.partybuild.entity.PanelStatisticsDetail;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 面板统计mapper
 *
 * @Author Zhang Fan
 **/
public interface PanelStatisticsMapper {

    /**
     * 计算支部活动相关的工作情况
     * @param conditions 查询参数集
     * @return
     */
    List<PanelStatistics> calculateBranchWorkInfoForActivity(Map<String, Object> conditions);

    /**
     * 计算非支部活动相关的工作情况
     * @param conditions 查询参数集
     * @return
     */
    List<PanelStatistics> calculateUnBranchWorkInfoForActivity(Map<String, Object> conditions);

    /**
     * 查询活动图片墙数据
     * @param orgId 组织id
     * @param limit 条数限制
     * @return
     */
    List<HashMap<String, Object>> selectActivityPhotos(@Param("orgId") Long orgId, @Param("limit") Integer limit);

    /**
     * 查询活动相关工作列表
     * @param conditions
     * @return
     */
    List<PanelStatisticsDetail> selectDetailWorkInfoForActivityWithConditions(Map<String, Object> conditions);

}
