package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbLinkLeaderDto;
import com.yizheng.partybuilding.dto.UserDeptPositiveDto;
import com.yizheng.partybuilding.entity.TabPbOrgnizeChange;

import java.util.List;

public interface TabPbOrgnizeChangeService {

    void insertOrgChange(TabPbOrgnizeChange tabPbOrgnizeChange);

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
