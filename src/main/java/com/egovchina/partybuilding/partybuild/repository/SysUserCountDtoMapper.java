package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.BaseDataAnalysisDto;
import com.egovchina.partybuilding.partybuild.entity.OrganizationPeopleStatistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YangYingXiang on 2019/01/22
 */
@Repository
public interface SysUserCountDtoMapper {
    /**
     * 统计直管党员
     * @param deptId
     * @return
     */
    Long selectCountDirectPartyUser(@Param(value="deptId")Long deptId);

    /**
     * 统计流动党员
     * @param deptId
     * @return
     */
    Long selectCountFlowUser(@Param(value="deptId")Long deptId);

    /**
     * 统计报到党员
     * @param deptId
     * @return
     */
    Long selectCountPositiveUser(@Param(value="deptId")Long deptId);

    /**
     * 统计班子成员
     * @param deptId
     * @return
     */
    Long selectCountLeadTeamMemberUser(@Param(value="deptId")Long deptId);

    /**
     * 统计
     * @param deptId
     * @return
     */
    Long selectCountHardshipUser(@Param(value="deptId")Long deptId);

    /**
     * 统计退休党员
     * @param deptId
     * @return
     */
    Long selectCountRetiredUser(@Param(value="deptId")Long deptId);

    /**
     * 统计指定组织下的各类型人数
     * @param orgId 组织id
     * @return
     */
    OrganizationPeopleStatistics selectPeopleCountingByOrgId(@Param("orgId") Long orgId);

    List<BaseDataAnalysisDto<Long>> selectCountUser(@Param(value="deptId")Long deptId);
}
