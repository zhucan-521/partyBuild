package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbActivitiesAssessVerify;

public interface TabPbActivitiesAssessVerifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbActivitiesAssessVerify record);

    int insertSelective(TabPbActivitiesAssessVerify record);

    TabPbActivitiesAssessVerify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbActivitiesAssessVerify record);

    int updateByPrimaryKey(TabPbActivitiesAssessVerify record);

    /**
     * 根据评议id查询审核数据
     * @param assessId
     * @return
     */
    TabPbActivitiesAssessVerify selectByAssessId(Long assessId);

    /**
     * 根据评议ID逻辑删除评议审核数据
     * @param assessId
     * @return
     */
    int logicDeleteByAssessId(Long assessId);
}