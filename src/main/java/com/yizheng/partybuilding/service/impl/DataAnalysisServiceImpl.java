package com.yizheng.partybuilding.service.impl;

import com.yizheng.partybuilding.dto.BaseDataAnalysisDto;
import com.yizheng.partybuilding.dto.PartyMemberNumDataAnalysisDto;
import com.yizheng.partybuilding.repository.DataAnalysisMapper;
import com.yizheng.partybuilding.service.inf.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据分析service实现
 *
 * @Author Zhang Fan
 **/
@Service("dataAnalysisService")
public class DataAnalysisServiceImpl implements DataAnalysisService {

    @Autowired
    private DataAnalysisMapper dataAnalysisMapper;

    @Override
    public List<BaseDataAnalysisDto<Long>> countLeadTeamMemberNumByAge(Long orgId) {
        return dataAnalysisMapper.countLeadTeamMemberNumByAge(orgId);
    }

    @Override
    public List<BaseDataAnalysisDto<Long>> countUnitRatioByType(Long orgId) {
        return dataAnalysisMapper.countUnitRatioByType(orgId);
    }

    @Override
    public List<BaseDataAnalysisDto<Long>> countOrgNumByType(Long orgId) {
        return dataAnalysisMapper.countOrgNumByType(orgId);
    }

    @Override
    public List<PartyMemberNumDataAnalysisDto> countPartyMemberNumByAreaCodeAndMonth(String areaCode) {
        return dataAnalysisMapper.countPartyMemberNumByAreaCodeAndMonth(areaCode);
    }

    @Override
    public List<BaseDataAnalysisDto<Long>> countPartyMemberNumByType(Long orgId) {
        return dataAnalysisMapper.countPartyMemberNumByType(orgId);
    }

    @Override
    public List<BaseDataAnalysisDto<Long>> countSpecialPartyMemberClassificationNum(Long orgId) {
        return dataAnalysisMapper.countSpecialPartyMemberClassificationNum(orgId);
    }

}
