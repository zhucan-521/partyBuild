package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduStudyCenter;
import com.yizheng.partybuilding.entity.TabPbEduStudyMark;
import com.yizheng.partybuilding.repository.TabPbEduStudyMarkMapper;
import com.yizheng.partybuilding.service.inf.TabPbEduStudyMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学习中心记录service实现
 *
 * @Author Zhang Fan
 **/
@Service("tabPbEduStudyMarkService")
public class TabPbEduStudyMarkServiceImpl implements TabPbEduStudyMarkService {

    @Autowired
    private TabPbEduStudyMarkMapper tabPbEduStudyMarkMapper;

    @Transactional
    @Override
    public int mark(Integer userId, Long studyId) {
        int retVal = 0;
        TabPbEduStudyMark markHistory = tabPbEduStudyMarkMapper.selectMarkHistory(userId, studyId);
        if (markHistory != null) {
            markHistory.setCreateTime(new Date());
            retVal += tabPbEduStudyMarkMapper.updateByPrimaryKey(markHistory);
        } else {
            TabPbEduStudyMark newMark = new TabPbEduStudyMark();
            newMark.setUserId(userId.longValue()).setStudyId(studyId);
            retVal += tabPbEduStudyMarkMapper.insertSelective(newMark);
        }
        return retVal;
    }

    @Override
    public List<TabPbEduStudyCenter> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduStudyCenter> list = tabPbEduStudyMarkMapper.selectWithConditions(conditions);
        return list;
    }
}
