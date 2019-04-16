package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.partybuild.entity.TabPbHardship;
import com.egovchina.partybuilding.partybuild.repository.TabPbHardshipMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.TabPbHardshipService;
import com.egovchina.partybuilding.partybuild.service.TabPbUserTagService;
import com.egovchina.partybuilding.partybuild.system.util.UserTagType;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author Jiang An
 **/
@Service("difficultyService")
@Transactional
public class TabPbHardshipServiceImpl implements TabPbHardshipService {

    @Autowired
    private TabPbHardshipMapper tabPbHardshipMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbUserTagService tabPbUserTagService;


    @Override
    public int deleteByPrimaryKey(Long hardshipId) {
        return tabPbHardshipMapper.deleteByPrimaryKey(hardshipId);
    }

    @Override
    public int deleteId(Long hardshipId) {
        int i = tabPbHardshipMapper.deleteId(hardshipId);
        if (i>0){
            TabPbHardship tabPbHardship = tabPbHardshipMapper.selectByPrimaryKey(hardshipId);
            Long userId = tabPbHardship.getUserId();
            tabSysUserMapper.updateNoPoor(userId);
            tabPbUserTagService.delete(userId, UserTagType.DIFFICULT);
        }
        return i;
    }

    @Override
    public int insert(TabPbHardship record) {
        return tabPbHardshipMapper.insert(record);
    }

    @PaddingBaseField
    @Override
    public int insertSelective(TabPbHardship record) {
        TabPbHardship tabPbHardship  =tabPbHardshipMapper.selectByUserId(record.getUserId());
        if (tabPbHardship == null){
           int i = tabPbHardshipMapper.insertSelective(record);
          if (i>0){
                TabPbHardship Hardship = tabPbHardshipMapper.selectByPrimaryKey(record.getHardshipId());
                tabSysUserMapper.updateByisPoor(Hardship.getUserId());
                tabPbUserTagService.addUserTag(Hardship.getUserId(), UserTagType.DIFFICULT);
         }
            return i;
        }else {
            throw new BusinessDataIncompleteException("该用户只能有一条困难情况");
        }

    }

    @Override
    public TabPbHardship selectByPrimaryKey(Long hardshipId) {
        return tabPbHardshipMapper.selectByPrimaryKey(hardshipId);
    }

    @Override
    public int updateByPrimaryKeySelective(TabPbHardship record) {
        tabPbHardshipMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(TabPbHardship record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(TabPbHardship record) {
        return tabPbHardshipMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据条件查询
     * @param conditions
     * @param page
     * @return
     */
    @Override
    public List<TabPbHardship> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return tabPbHardshipMapper.selectWithConditions(conditions);
    }

    @Override
    public int updateUsernaneById(TabPbHardship tabPbHardship) {
        return tabPbHardshipMapper.updateUsernameById(tabPbHardship);
    }

    @Override
    public TabPbHardship selectAndDeleteId(Long hardshipId) {
        return tabPbHardshipMapper.selectAndDeleteId(hardshipId);
    }

    @Override
    public TabPbHardship selectByUserId(Long userId) {
        TabPbHardship tabPbHardship = tabPbHardshipMapper.selectByUserId(userId);
        return tabPbHardship;

    }

}
