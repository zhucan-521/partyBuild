package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.entity.SysTaskConfigurer;
import com.egovchina.partybuilding.partybuild.repository.SysTaskConfigurerMapper;
import com.egovchina.partybuilding.partybuild.service.SysTaskConfigurerService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统任务配置service实现
 *
 * @Author Zhang Fan
 **/
@Service("sysTaskConfigurerService")
public class SysTaskConfigurerServiceImpl implements SysTaskConfigurerService {

    @Autowired
    private SysTaskConfigurerMapper sysTaskConfigurerMapper;

    @Override
    public List<SysTaskConfigurer> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return sysTaskConfigurerMapper.selectWithConditions(conditions);
    }

    @Transactional
    @Override
    public int insertAheadDelete(SysTaskConfigurer configurer) {
        sysTaskConfigurerMapper.aheadDelete(configurer);
        List<SysTaskConfigurer> pendingInsertList = new ArrayList<>();
        String[] fulfillIds = configurer.getFulfillId().split(",");
        String[] taskIds = configurer.getTaskId().split(",");
        for (String fulfillId : fulfillIds) {
            for (String taskId : taskIds) {
                SysTaskConfigurer taskConfigurer = new SysTaskConfigurer();
                BeanUtil.copyPropertiesIgnoreNull(configurer, taskConfigurer);
                taskConfigurer.setFulfillId(fulfillId);
                taskConfigurer.setTaskId(taskId);
                pendingInsertList.add(taskConfigurer);
            }
        }
        return sysTaskConfigurerMapper.batchInsert(pendingInsertList);
    }

    @Override
    public int updateConfigurer(SysTaskConfigurer configurer) {
        return sysTaskConfigurerMapper.updateByPrimaryKeySelective(configurer);
    }

    @Override
    public int logicDeleteById(Long id) {
        SysTaskConfigurer configurer = new SysTaskConfigurer();
        configurer.setId(id);
        configurer.setDelFlag((byte) 1);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(configurer);
        return sysTaskConfigurerMapper.updateByPrimaryKeySelective(configurer);
    }
}
