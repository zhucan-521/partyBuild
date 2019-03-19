package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbDeptSecretary;
import com.yizheng.partybuilding.repository.TabPbDeptSecretaryMapper;
import com.yizheng.partybuilding.repository.TabPbFamilyMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.ITabPbDeptSecretaryService;
import com.yizheng.partybuilding.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author YangYingXiang on 2019/03/01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TabPbDeptSecretaryServiceImpl implements ITabPbDeptSecretaryService {

    @Autowired
    TabPbDeptSecretaryMapper deptSecretaryMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TabSysUserMapper userMapper;

    @Autowired
    private TabPbFamilyMapper pbFamilyMapper;

    @Override
    @PaddingBaseField(recursive = true)
    public int insertSelective(TabPbDeptSecretary record) {
        int retVal = userMapper.saveEntity(record.getUser());
        if(retVal>0){
            record.setUserId(record.getUser().getUserId().longValue());
            deptSecretaryMapper.insertSelective(record);
        }else{
            throw new BusinessDataIncompleteException("保存失败");
        }
        return retVal;
    }

    @Override
    public TabPbDeptSecretary selectByPrimaryKey(Long secretaryId) {
        TabPbDeptSecretary secretary = deptSecretaryMapper.selectByPrimaryKey(secretaryId);
        if(secretary != null){
            secretary.setRewardsDtoList(deptSecretaryMapper.punishmentRewards(secretary.getUser().getUserId().longValue()));
            secretary.setFamilyList(pbFamilyMapper.selectListPrimary(secretary.getUserId()));
        }
        return secretary;
    }

    @Override
    @PaddingBaseField(recursive = true)
    public int updateByPrimaryKeySelective(TabPbDeptSecretary record) {
        TabPbDeptSecretary secretary = deptSecretaryMapper.selectByPrimaryKey(record.getSecretaryId());
        if(secretary != null){
            int retVal = deptSecretaryMapper.updateByPrimaryKeySelective(record);
            if(record.getUser()!=null){
                record.getUser().setUserId(secretary.getUserId().intValue());
                retVal += sysUserMapper.updateById(record.getUser());
            }
            return retVal;
        }else{
            throw new BusinessDataIncompleteException("该书记不存在");
        }
    }

    @Override
    public int tombstone(Long secretaryId) {
        TabPbDeptSecretary record = new TabPbDeptSecretary();
        record.setUpdateTime(new Date());
        record.setUpdateUserid(UserContextHolder.getUserIdLong());
        record.setUpdateUsername(UserContextHolder.getUserName());
        record.setSecretaryId(secretaryId);
        return deptSecretaryMapper.tombstone(record);
    }

    @Override
    public List<TabPbDeptSecretary> selectList(TabPbDeptSecretary record, Page page) {
        PageHelper.startPage(page);
        return deptSecretaryMapper.selectList(record);
    }
}
