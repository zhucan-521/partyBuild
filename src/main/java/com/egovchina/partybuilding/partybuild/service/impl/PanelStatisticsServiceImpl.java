package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.PanelStatistics;
import com.egovchina.partybuilding.partybuild.entity.PanelStatisticsDetail;
import com.egovchina.partybuilding.partybuild.repository.PanelStatisticsMapper;
import com.egovchina.partybuilding.partybuild.service.PanelStatisticsService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 面板统计service实现
 *
 * @Author Zhang Fan
 **/
@Service("panelStatisticsService")
public class PanelStatisticsServiceImpl implements PanelStatisticsService {

    @Autowired
    private PanelStatisticsMapper panelStatisticsMapper;

    @Override
    public List<PanelStatistics> calculateBranchWorkInfoForActivity(Map<String, Object> conditions) {
        return panelStatisticsMapper.calculateBranchWorkInfoForActivity(conditions);
    }

    @Override
    public List<PanelStatistics> calculateUnBranchWorkInfoForActivity(Map<String, Object> conditions) {
        return panelStatisticsMapper.calculateUnBranchWorkInfoForActivity(conditions);
    }

    @Override
    public List<HashMap<String, Object>> selectActivityPhotos(Long orgId, Integer limit) {
        return panelStatisticsMapper.selectActivityPhotos(orgId, limit);
    }

    @Override
    public List<PanelStatisticsDetail> selectDetailWorkInfoForActivity(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return panelStatisticsMapper.selectDetailWorkInfoForActivityWithConditions(conditions);
    }
}
