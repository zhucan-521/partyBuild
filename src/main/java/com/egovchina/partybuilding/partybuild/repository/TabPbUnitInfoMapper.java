package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbUnitInfoMapper {
    int deleteByPrimaryKey(Long unitId);

    int insert(TabPbUnitInfo record);

    int insertSelective(TabPbUnitInfo record);

    TabPbUnitInfo selectByPrimaryKey(Long unitId);

    int updateByPrimaryKeySelective(TabPbUnitInfo record);

    int updateByPrimaryKey(TabPbUnitInfo record);

    List<TabPbUnitInfo> selectByOrgId(Long orgId);

    int batchLogicDeleteById(List<Long> pendingRemoveUnitIdList);

    List<TabPbUnitInfo> selectByUnitName(@Param("unitName") String unitName);
}