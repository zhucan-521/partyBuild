package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.entity.TabPbActivitiesAssessVerify;

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
