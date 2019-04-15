package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TabPbOrgnizeChangeMapper {
    int deleteByPrimaryKey(Long changeId);

    int insert(TabPbOrgnizeChange record);

    int insertSelective(TabPbOrgnizeChange record);

    TabPbOrgnizeChange selectByPrimaryKey(Long changeId);

    int updateByPrimaryKeySelective(TabPbOrgnizeChange record);

    int updateByPrimaryKeyWithBLOBs(TabPbOrgnizeChange record);

    int updateByPrimaryKey(TabPbOrgnizeChange record);

    /**
     * 根据组织主键查询最新的变更记录
     * @param deptId
     * @return
     */
    TabPbOrgnizeChange selectOrgChangeByDeptIdOrderTime(@Param("deptId") Long deptId,
                                                        @Param("changeType") Long changeType);

    List<TabPbOrgnizeChange> selectByChangeType(Map<String, Object> conditions);

    /**
     * 整合组织信息
     * @param deptId
     * @return
     */
    List<TabPbOrgnizeChange> selectCombination(@Param("deptId") Long deptId);
}