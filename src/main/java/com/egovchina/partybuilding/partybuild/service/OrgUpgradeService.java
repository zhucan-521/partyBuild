package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.OrgUpgradeDto;
import com.egovchina.partybuilding.partybuild.dto.OrgUpgradedPersonnelTransferDTO;
import com.egovchina.partybuilding.partybuild.vo.DirectPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.OrgUpgradeVO;

import java.util.List;

/**
 * 描述:
 * 党组织升级业务层
 *
 * @author wuyunjie
 * Date 2019-01-03 14:32
 */
public interface OrgUpgradeService {
    /**
     * 批量修改人员组织id
     *
     * @param orgUpgradedPersonnelTransferDTO
     * @return
     */
    int batchDeptIdByUserId(OrgUpgradedPersonnelTransferDTO orgUpgradedPersonnelTransferDTO);

    /**
     * 查询组织下的直属党员
     *
     * @param deptId
     * @return
     */
    List<DirectPartyMemberVO> getDirectPartyMemberByDeptId(Long deptId);

    Integer addOrgUpgrade(OrgUpgradeDto orgUpgradeDto);

    Integer updateByPrimaryKeySelective(OrgUpgradeDto orgUpgradeDto);

    OrgUpgradeVO selectOrgUpgradeByDeptId(Long deptId);

}
