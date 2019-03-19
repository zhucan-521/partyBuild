package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.dto.OrganizationUpgradeAndChangeDto;
import com.yizheng.partybuilding.dto.OrganizationUpgradeDto;
import com.yizheng.partybuilding.dto.Personnel;
import com.yizheng.partybuilding.entity.SysDeptUpgradeTemp;

import java.util.List;

/**
 * 描述:
 * 党组织升级业务层
 *
 * @author wuyunjie
 * Date 2019-01-03 14:32
 */
public interface IOrganizationUpgradeService {
    /**
     * 批量修改人员组织id
     *
     * @param organizationUpgradeDto
     * @return
     */
    int batchDeptIdByUserId(OrganizationUpgradeDto organizationUpgradeDto);

    /**
     * 查询组织下的直属党员
     *
     * @param deptId
     * @return
     */
    List<Personnel> getAllUserByDeptId(Long deptId);

    Integer insertSelective(OrganizationUpgradeAndChangeDto organizationUpgradeAndChangeDto);

    Integer updateByPrimaryKeySelective(SysDeptUpgradeTemp sysDeptUpgradeTemp);

    OrganizationUpgradeAndChangeDto selectByDeptId(Long deptId);

}
