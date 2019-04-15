package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduStudyCenter;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduStudyCenterMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.TabPbEduStudyCenterService;
import com.egovchina.partybuilding.partybuild.service.TabPbEduStudyMarkService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 学习中心service实现
 *
 * @Author Zhang Fan
 **/
@Service("tabPbEduStudyService")
public class TabPbEduStudyCenterServiceImpl implements TabPbEduStudyCenterService {

    @Autowired
    private TabPbEduStudyCenterMapper tabPbEduStudyCenterMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;
    @Autowired
    private TabPbEduStudyMarkService tabPbEduStudyMarkService;

    @Override
    public List<TabPbEduStudyCenter> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduStudyCenter> list = tabPbEduStudyCenterMapper.selectWithConditions(conditions);
        return list;
    }

    @Transactional
    @Override
    public int insertWithAbout(TabPbEduStudyCenter tabPbEduStudyCenter) {
        int retVal = tabPbEduStudyCenterMapper.insertSelective(tabPbEduStudyCenter);
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbEduStudyCenter.getAttachments(),
                    tabPbEduStudyCenter.getStudyId(), AttachmentType.STUDY_CENTER);
        }
        return retVal;
    }

    @Transactional
    @Override
    public int updateWithAbout(TabPbEduStudyCenter tabPbEduStudyCenter) {
        int retVal = tabPbEduStudyCenterMapper.updateByPrimaryKeySelective(tabPbEduStudyCenter);
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbEduStudyCenter.getAttachments(),
                    tabPbEduStudyCenter.getStudyId(), AttachmentType.STUDY_CENTER);
        }
        return retVal;
    }

    @Override
    public TabPbEduStudyCenter selectWithAboutInfoById(Long studyId) {
        return tabPbEduStudyCenterMapper.selectWithAboutInfoById(studyId);
    }

    @Override
    public int logicDeleteById(Long studyId) {
        return tabPbEduStudyCenterMapper.logicDeleteById(studyId);
    }

    @Override
    public int like(Long studyId) {
        return tabPbEduStudyCenterMapper.like(studyId);
    }

    @Override
    public int watch(Long studyId) {
        return tabPbEduStudyCenterMapper.watch(studyId);
    }

    @Transactional
    @Override
    public TabPbEduStudyCenter details(Long studyId, Long lookFrom) {
        TabPbEduStudyCenter dbTabPbEduStudyCenter = selectWithAboutInfoById(studyId);
        if (dbTabPbEduStudyCenter != null &&
                lookFrom != null && lookFrom == 1L) { //记录浏览记录
            tabPbEduStudyMarkService.mark(UserContextHolder.getUserId(), studyId);
            watch(studyId);
            dbTabPbEduStudyCenter.setWatchCount(dbTabPbEduStudyCenter.getWatchCount() + 1);
        }
        return dbTabPbEduStudyCenter;
    }
}
