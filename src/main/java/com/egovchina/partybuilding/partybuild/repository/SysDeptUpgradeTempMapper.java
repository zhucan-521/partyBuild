package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.SysDeptUpgradeTemp;
import com.egovchina.partybuilding.partybuild.vo.OrgUpgradeVO;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDeptUpgradeTempMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDeptUpgradeTemp record);

    int insertSelective(SysDeptUpgradeTemp record);

    SysDeptUpgradeTemp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDeptUpgradeTemp record);

    int updateByPrimaryKeyWithBLOBs(SysDeptUpgradeTemp record);

    int updateByPrimaryKey(SysDeptUpgradeTemp record);

    OrgUpgradeVO selectOrgUpgradeVOByDeptId(Long deptId);
}