package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbUnitInfo;

import java.util.List;

public interface ITabPbUnitInfoService {
    List<TabPbUnitInfo> selectByUnitName(String unitName, Page page);

    TabPbUnitInfo selectByUnitId(Long unitId);

    int insertUnitInfo(TabPbUnitInfo tabPbUnitInfo);

    int deleteUnitInfo(Long unitId);

    int updateUnitInfo(TabPbUnitInfo tabPbUnitInfo);

    List<TabPbUnitInfo> selectByOrgId(Long orgId);

    int batchUpdateUnitInfo(List<TabPbUnitInfo> tabPbUnitInfoList);
}
