package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.entity.TabPbActivitiesAssessVerify;

/**
 * 民主评议审核service
 *
 * @Author Zhang Fan
 **/
public interface TabPbActivitiesAssessVerifyService {
    /**
     * 覆盖式新增
     * @param tabPbActivitiesAssessVerify
     * @return
     */
    int overwriteInsertSelective(TabPbActivitiesAssessVerify tabPbActivitiesAssessVerify);
}
