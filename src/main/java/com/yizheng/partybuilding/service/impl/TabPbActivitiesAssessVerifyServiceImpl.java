package com.yizheng.partybuilding.service.impl;

import com.yizheng.partybuilding.entity.TabPbActivitiesAssessVerify;
import com.yizheng.partybuilding.repository.TabPbActivitiesAssessVerifyMapper;
import com.yizheng.partybuilding.service.inf.TabPbActivitiesAssessVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 民主评议service实现
 *
 * @Author Zhang Fan
 **/
@Service("tabPbActivitiesAssessVerifyService")
public class TabPbActivitiesAssessVerifyServiceImpl implements TabPbActivitiesAssessVerifyService {

    @Autowired
    private TabPbActivitiesAssessVerifyMapper tabPbActivitiesAssessVerifyMapper;

    @Transactional
    @Override
    public int overwriteInsertSelective(TabPbActivitiesAssessVerify tabPbActivitiesAssessVerify) {
        //先删除
        int retVal = tabPbActivitiesAssessVerifyMapper.logicDeleteByAssessId(
                tabPbActivitiesAssessVerify.getAssessId());
        retVal += tabPbActivitiesAssessVerifyMapper.insertSelective(tabPbActivitiesAssessVerify);
        return retVal;
    }
}
