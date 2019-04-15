package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbGrantCommittee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbGrantCommitteeMapper {

    TabPbGrantCommittee selectByPrimaryKey(Long grantCommitteeId);

    int updateByPrimaryKeySelective(TabPbGrantCommittee record);

    /**
     * 新增并返回主键
     *
     * @return
     */
    int insertSelectiveAndReturnPrimaryKey(TabPbGrantCommittee record);

    /**
     * 通过组织id查询
     *
     * @param orgId
     * @return
     */
    TabPbGrantCommittee selectByOrgId(Long orgId);

    /**
     * 分页查询
     *
     * @return
     */
    List<TabPbGrantCommittee> select(@Param("orgId") Long orgId,
                                     @Param("orgRange") Long orgRange,
                                     @Param("name") String name,
                                     @Param("positiveName") String positiveName);
}