package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.dto.OrgChangeDTO;
import com.egovchina.partybuilding.partybuild.vo.OrgChangeVO;

import java.util.List;

/**
 * 描述:
 * 组织变动接口
 *
 * @author wuyunjie
 * Date 2019-04-20 11:17
 */
public interface OrgChangeService {

    OrgChangeVO selectOrgChangeByDeptIdOrderTime(Long deptId, Long changeType);

    int addOrgChange(OrgChangeDTO change);

    List<OrgChangeVO> selectOrgChangeList(Long orgId, Page page);

    int insertOrgChange(TabPbOrgnizeChange tabPbOrgnizeChange);

    int insertSelective(TabPbOrgnizeChange tabPbOrgnizeChange);

}
