package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.partybuild.entity.TabPbActivitiesAssessVerify;
import com.egovchina.partybuilding.partybuild.repository.TabPbActivitiesAssessVerifyMapper;
import com.egovchina.partybuilding.partybuild.service.TabPbActivitiesAssessVerifyService;
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
