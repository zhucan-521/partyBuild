package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbLinkLeaderDto;
import com.egovchina.partybuilding.partybuild.dto.UserDeptPositiveDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TabPbOrgnizeChangeService {

    void insertOrgChange(TabPbOrgnizeChange tabPbOrgnizeChange);

    int insertSelective(TabPbOrgnizeChange tabPbOrgnizeChange);

    TabPbOrgnizeChange selectOrgChangeByDeptIdOrderTime(Long deptId,Long changeType);

    /**
     * 组织变动
     * @param orgnizeChange
     * @return
     */
    String changeOrg(TabPbOrgnizeChange orgnizeChange);

    UserDeptPositiveDto selectJointByUserId(Long userId);

    List<TabPbLinkLeaderDto> selectUserDeptByDeptId(Long deptId);


    int saveJoint(TabPbLinkLeaderDto tabPbLinkLeaderDto);

    /**
     * 通过changeType查询分页
     * @param changeType
     * @param page
     * @return
     */
    PageInfo<TabPbOrgnizeChange> selectByChangeType(String changeType,Long orgId, Page page);

    int delLeaderByLinkLedaerId(Long linkLedaerId);

    /**
     * 整合组织信息
     * @param deptId
     * @return
     */
    List<TabPbOrgnizeChange> selectCombination(Long deptId,Page page);
}
