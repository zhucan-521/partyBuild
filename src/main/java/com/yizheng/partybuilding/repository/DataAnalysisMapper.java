package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.BaseDataAnalysisDto;
import com.yizheng.partybuilding.dto.PartyMemberNumDataAnalysisDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据分析mapper
 *
 * @Author Zhang Fan
 **/
public interface DataAnalysisMapper {

    /**
     * 根据年龄统计领导班子成员
     * @param orgId
     * @return
     */
    List<BaseDataAnalysisDto<Long>> countLeadTeamMemberNumByAge(Long orgId);

    /**
     * 根据类型统计单位占比
     * @param orgId
     * @return
     */
    List<BaseDataAnalysisDto<Long>> countUnitRatioByType(Long orgId);

    /**
     * 根据类型统计组织数
     * @param orgId
     * @return
     */
    List<BaseDataAnalysisDto<Long>> countOrgNumByType(Long orgId);

    /**
     * 根据地区及月份统计当年党员数量及相较往年发展趋势
     * @param areaCode
     * @return
     */
    List<PartyMemberNumDataAnalysisDto> countPartyMemberNumByAreaCodeAndMonth(String areaCode);

    /**
     * 根据类型统计党员数
     * @param orgId
     * @return
     */
    List<BaseDataAnalysisDto<Long>> countPartyMemberNumByType(@Param("orgId") Long orgId);

    /**
     * 统计特殊党员分类数量
     * @param orgId
     * @return
     */
    List<BaseDataAnalysisDto<Long>> countSpecialPartyMemberClassificationNum(@Param("orgId") Long orgId);
}
