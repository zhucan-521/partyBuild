package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;

import java.util.List;

public interface TabPbUnitInfoService {
    List<TabPbUnitInfo> selectByUnitName(String unitName, Page page);

    TabPbUnitInfo selectByUnitId(Long unitId);

    int insertUnitInfo(TabPbUnitInfo tabPbUnitInfo);

    int deleteUnitInfo(Long unitId);

    int updateUnitInfo(TabPbUnitInfo tabPbUnitInfo);

    List<TabPbUnitInfo> selectByOrgId(Long orgId);

    int batchUpdateUnitInfo(List<TabPbUnitInfo> tabPbUnitInfoList);
}
