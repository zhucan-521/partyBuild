package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.OrganizationUpgradeAndChangeDto;
import com.yizheng.partybuilding.entity.SysDeptUpgradeTemp;
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

    OrganizationUpgradeAndChangeDto selectByDeptId(Long deptId);
}