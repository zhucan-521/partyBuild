package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.partybuild.entity.TabPbWorkPlan;
import com.egovchina.partybuilding.partybuild.repository.TabPbWorkPlanMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.TabPbWorkPlanService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工作计划实现类
 *
 * @Author Zhang Fan
 **/
@Service("tabPbWorkPlanService")
public class TabPbWorkPlanServiceImpl implements TabPbWorkPlanService {

    @Autowired
    private TabPbWorkPlanMapper tabPbWorkPlanMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public int deleteByPrimaryKey(Long planId) {
        return tabPbWorkPlanMapper.deleteByPrimaryKey(planId);
    }

    @PaddingBaseField
    @Override
    public int insert(TabPbWorkPlan record) {
        return tabPbWorkPlanMapper.insert(record);
    }

    @PaddingBaseField
    @Override
    public int insertSelective(TabPbWorkPlan record) {
        return tabPbWorkPlanMapper.insertSelective(record);
    }

    @Override
    public TabPbWorkPlan selectByPrimaryKey(Long planId) {
        return tabPbWorkPlanMapper.selectByPrimaryKey(planId);
    }

    @PaddingBaseField
    @Override
    public int updateByPrimaryKeySelective(TabPbWorkPlan record) {
        return tabPbWorkPlanMapper.updateByPrimaryKeySelective(record);
    }

    @PaddingBaseField
    @Override
    public int updateByPrimaryKey(TabPbWorkPlan record) {
        return tabPbWorkPlanMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TabPbWorkPlan> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbWorkPlan> list = tabPbWorkPlanMapper.selectWithConditions(conditions);
        return list;
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int insertWithAnnexs(TabPbWorkPlan tabPbWorkPlan) {
        int retVal = tabPbWorkPlanMapper.insertSelective(tabPbWorkPlan);
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbWorkPlan.getPlanAttachments(),
                    tabPbWorkPlan.getPlanId(), AttachmentType.WORK_PLAN);
        }
        return retVal;
    }

    @Override
    public boolean existsWorkPlan(Long planYear, Long orgId) {
        return tabPbWorkPlanMapper.selectByPlanYearAndOrgId(planYear, orgId) != null;
    }

    @PaddingBaseField
    @Override
    public int logicDeleteById(Long planId) {
        return tabPbWorkPlanMapper.logicDeleteById(planId);
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int updateWithAnnexs(TabPbWorkPlan tabPbWorkPlan, Long attType) {
        tabPbWorkPlan.setCreateTime(null);
        tabPbWorkPlan.setCreateUserid(null);
        tabPbWorkPlan.setCreateUsername(null);
        int retVal = tabPbWorkPlanMapper.updateByPrimaryKeySelective(tabPbWorkPlan);
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbWorkPlan.getPlanAttachments(),
                    tabPbWorkPlan.getPlanId(), attType);
        }
        return retVal;
    }

    @PaddingBaseField
    @Override
    public int review(TabPbWorkPlan tabPbWorkPlan) {
        tabPbWorkPlan.setCreateTime(null);
        tabPbWorkPlan.setCreateUserid(null);
        tabPbWorkPlan.setCreateUsername(null);
        tabPbWorkPlan.setEblFlag(null);
        tabPbWorkPlan.setDelFlag(null);
        return tabPbWorkPlanMapper.updateByPrimaryKeySelective(tabPbWorkPlan);
    }

}
