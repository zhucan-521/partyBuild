package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgClassify;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgClassifyMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.TabPbOrgClassifyService;
import com.egovchina.partybuilding.partybuild.system.entity.SysDept;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 分类定等service实现类
 *
 * @Author Zhang Fan
 **/
@Service("tabPbOrgClassifyService")
public class TabPbOrgClassifyServiceImpl implements TabPbOrgClassifyService {

    @Autowired
    private TabPbOrgClassifyMapper tabPbOrgClassifyMapper;
    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Override
    public int deleteByPrimaryKey(Long orgClassifyId) {
        return tabPbOrgClassifyMapper.deleteByPrimaryKey(orgClassifyId);
    }

    @PaddingBaseField
    @Override
    public int insert(TabPbOrgClassify record) {
        return tabPbOrgClassifyMapper.insert(record);
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int insertSelective(TabPbOrgClassify record) {
        int retVal = tabPbOrgClassifyMapper.insertSelective(record);
        if (retVal > 0) {
            retVal += pushModification(record.getDeptId().intValue(), record.getOrgLevel(), record.getLevelDate());
        }
        return retVal;
    }

    /**
     * 推送修改
     * @param deptId 组织ID
     * @param orgLevel 定等级别
     * @param levelDate 定等日期
     * @return
     */
    private int pushModification(Integer deptId, Long orgLevel, Date levelDate) {
        SysDept sysDept = tabSysDeptMapper.selectByPrimaryKey(deptId.longValue());
        sysDept.setDeptId(deptId);
        sysDept.setOrgLevel(orgLevel);
        sysDept.setLevelDate(levelDate);
        return tabSysDeptMapper.updateByPrimaryKey(sysDept);
    }

    @Override
    public TabPbOrgClassify selectByPrimaryKey(Long orgClassifyId) {
        return tabPbOrgClassifyMapper.selectByPrimaryKey(orgClassifyId);
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int updateByPrimaryKeySelective(TabPbOrgClassify record) {
        int retVal = tabPbOrgClassifyMapper.updateByPrimaryKeySelective(record);
        retVal += pushModification(record.getDeptId().intValue(), record.getOrgLevel(), record.getLevelDate());
        return retVal;
    }

    @PaddingBaseField
    @Override
    public int updateByPrimaryKey(TabPbOrgClassify record) {
        return tabPbOrgClassifyMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TabPbOrgClassify> selectByDeptId(Long deptId) {
        return tabPbOrgClassifyMapper.selectByDeptId(deptId);
    }

    @PaddingBaseField
    @Override
    public int logicDeleteById(Long orgClassifyId) {
        return tabPbOrgClassifyMapper.logicDeleteById(orgClassifyId);
    }

    @Override
    public List<TabPbOrgClassify> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbOrgClassify> list =  tabPbOrgClassifyMapper.selectWithConditions(conditions);
        return list;
    }
}
