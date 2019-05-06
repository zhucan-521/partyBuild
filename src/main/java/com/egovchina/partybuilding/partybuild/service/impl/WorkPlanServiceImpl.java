package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.WorkPlanDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkPlanReviewDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkSumamryReviewDTO;
import com.egovchina.partybuilding.partybuild.dto.WorkSummaryDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbWorkPlan;
import com.egovchina.partybuilding.partybuild.entity.WorkPlanQueryBean;
import com.egovchina.partybuilding.partybuild.repository.TabPbWorkPlanMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.WorkPlanService;
import com.egovchina.partybuilding.partybuild.vo.WorkPlanVO;
import com.egovchina.partybuilding.partybuild.vo.WorkSummaryVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * 工作计划实现类
 *
 * @Author Zhang Fan
 **/
@Service("workPlanService")
public class WorkPlanServiceImpl implements WorkPlanService {

    @Autowired
    private TabPbWorkPlanMapper tabPbWorkPlanMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public List<WorkPlanVO> selectWorkPlanVOByCondition(WorkPlanQueryBean queryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbWorkPlanMapper.selectWorkPlanVOByCondition(queryBean);
    }

    @Transactional
    @Override
    public int insertWorkPlan(WorkPlanDTO workPlanDTO) {
        //判断添加的工作计划是否已经在数据库中存在
        String planYear = workPlanDTO.getPlanYear();
        Long orgId = workPlanDTO.getOrgId();
        if (this.existsWorkPlan(planYear, orgId)) {
            throw new BusinessDataCheckFailException(String.format("该组织%s年度工作计划已存在", planYear));
        }
        workPlanDTO.setReportDate(new Date());

        TabPbWorkPlan tabPbWorkPlan =
                generateTargetCopyPropertiesAndPaddingBaseField(workPlanDTO, TabPbWorkPlan.class, false);
        int judgment = tabPbWorkPlanMapper.insertSelective(tabPbWorkPlan);
        if (judgment > 0) {
            judgment += iTabPbAttachmentService.intelligentOperation(workPlanDTO.getAttachments(),
                    tabPbWorkPlan.getPlanId(), AttachmentType.WORK_PLAN);
        }
        return judgment;
    }

    @Transactional
    @Override
    public int updateWorkPlan(WorkPlanDTO workPlanDTO) {
        WorkPlanVO workPlanVO = tabPbWorkPlanMapper.selectWorkPlanVOById(workPlanDTO.getPlanId());
        if (workPlanVO == null) {
            throw new BusinessDataNotFoundException("您要修改的工作计划不存在");
        }
        TabPbWorkPlan tabPbWorkPlan = generateTargetCopyPropertiesAndPaddingBaseField(workPlanDTO, TabPbWorkPlan.class, true);
        int judgment = tabPbWorkPlanMapper.updateByPrimaryKeySelective(tabPbWorkPlan);
        if (judgment > 0) {
            judgment += iTabPbAttachmentService.intelligentOperation(workPlanDTO.getAttachments(),
                    workPlanDTO.getPlanId(), AttachmentType.WORK_PLAN);
        }
        return judgment;
    }

    @Override
    public WorkPlanVO selectWorkPlanVOById(Long planId) {
        WorkPlanVO workPlanVO = tabPbWorkPlanMapper.selectWorkPlanVOById(planId);
        if (workPlanVO == null) {
            throw new BusinessDataCheckFailException("您要查询的工作计划不存在");
        }
        return tabPbWorkPlanMapper.selectWorkPlanVOById(planId);
    }

    @Override
    public boolean existsWorkPlan(String planYear, Long orgId) {
        return tabPbWorkPlanMapper.selectByPlanYearAndOrgId(planYear, orgId) != null;
    }

    @Override
    public int logicDeleteById(Long planId) {
        WorkPlanVO workPlanVO = tabPbWorkPlanMapper.selectWorkPlanVOById(planId);
        if (workPlanVO == null) {
            throw new BusinessDataCheckFailException("该工作计划不存在或已经被删除");
        }
        TabPbWorkPlan delete = new TabPbWorkPlan();
        delete.setPlanId(planId);
        delete.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(delete);
        return tabPbWorkPlanMapper.updateByPrimaryKeySelective(delete);
    }

    @Override
    public List<WorkSummaryVO> selectWorkSummaryVOByCondition(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return tabPbWorkPlanMapper.selectWorkSummaryVOByCondition(conditions);
    }

    @Transactional
    @Override
    public int updateWorkSummary(WorkSummaryDTO workSummaryDTO) {
        WorkSummaryVO workSummaryVO = tabPbWorkPlanMapper.selectWorkSummaryVOById(workSummaryDTO.getPlanId());
        if (workSummaryVO == null) {
            throw new BusinessDataNotFoundException("该工作总结不存在");
        }
        TabPbWorkPlan tabPbWorkPlan =
                generateTargetCopyPropertiesAndPaddingBaseField(workSummaryDTO, TabPbWorkPlan.class, true);
        int judgment = tabPbWorkPlanMapper.updateByPrimaryKeySelective(tabPbWorkPlan);
        if (judgment > 0) {
            judgment += iTabPbAttachmentService.intelligentOperation(workSummaryDTO.getAttachments(),
                    workSummaryDTO.getPlanId(), AttachmentType.WORK_SUMMARY);
        }
        return judgment;
    }

    @Override
    public WorkSummaryVO selectWorkSummaryVOById(Long planId) {
        WorkSummaryVO workSummaryVO = tabPbWorkPlanMapper.selectWorkSummaryVOById(planId);
        if (workSummaryVO == null) {
            throw new BusinessDataNotFoundException("该工作总结不存在");
        }
        return tabPbWorkPlanMapper.selectWorkSummaryVOById(planId);
    }

    @Override
    public int reviewWorkSummary(WorkSumamryReviewDTO reviewDTO) {
        TabPbWorkPlan dbWorkSummary = tabPbWorkPlanMapper.selectByPrimaryKey(reviewDTO.getPlanId());
        if (dbWorkSummary == null) {
            throw new BusinessDataNotFoundException("总结数据异常，已被删除或不存在");
        }
        TabPbWorkPlan tabPbWorkPlan =
                generateTargetCopyPropertiesAndPaddingBaseField(reviewDTO, TabPbWorkPlan.class, true);
        return tabPbWorkPlanMapper.updateByPrimaryKeySelective(tabPbWorkPlan);
    }


    @Override
    public int reviewWorkPlan(WorkPlanReviewDTO reviewDTO) {
        TabPbWorkPlan dbWorkPlan = tabPbWorkPlanMapper.selectByPrimaryKey(reviewDTO.getPlanId());
        if (dbWorkPlan == null) {
            throw new BusinessDataNotFoundException("计划数据异常，已被删除或不存在");
        }
        TabPbWorkPlan tabPbWorkPlan =
                generateTargetCopyPropertiesAndPaddingBaseField(reviewDTO, TabPbWorkPlan.class, true);
        return tabPbWorkPlanMapper.updateByPrimaryKeySelective(tabPbWorkPlan);
    }

}
