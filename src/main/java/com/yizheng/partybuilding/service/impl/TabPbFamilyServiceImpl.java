package com.yizheng.partybuilding.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.exception.BusinessException;
import com.yizheng.commons.util.BeanUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.entity.TabPbFamily;

import com.yizheng.partybuilding.repository.TabPbFamilyMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.TabPbFamilyService;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.partybuilding.system.mapper.SysUserMapper;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author YangYingXiang on 2018/11/26
 */
@Service("TabPbFamilyService")
@Transactional(rollbackFor = BusinessException.class)
public class TabPbFamilyServiceImpl extends ServiceImpl<TabPbFamilyMapper, TabPbFamily> implements TabPbFamilyService {

    @Autowired
    private TabPbFamilyMapper pbFamilyMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TabSysUserMapper userMapper;

    /**
     * 根据党员id加载list数据
     *
     * @param partyMemberId
     * @return
     */
    @Override
    public List<TabPbFamily> getListPage(Long partyMemberId) {
        if (!"".equals(partyMemberId)) {
            List<TabPbFamily> list = pbFamilyMapper.selectListPrimary(partyMemberId);
            return list;
        } else {
            throw new BusinessDataIncompleteException("人员id为空！");
        }

    }

    /**
     * 根据主键ID查询单条记录
     *
     * @param relationId
     * @return
     */
    @Override
    public TabPbFamily selectByPrimaryKey(Long relationId) {
        if (!"".equals(relationId)) {
            TabPbFamily tabPbFamily = pbFamilyMapper.findById(relationId);
            return tabPbFamily;
        } else {
            throw new BusinessDataIncompleteException("家庭成员Id为空");
        }
    }

    /**
     * 根据主键ID删除单条记录
     *
     * @param relationId
     */
    @Override
    public int deleteByPrimaryKey(Long relationId) {
        TabPbFamily tabPbFamily = pbFamilyMapper.findById(relationId);
        tabPbFamily.setUpdateTime(new Date());
        tabPbFamily.setUpdateUserid(UserContextHolder.getUserIdLong());
        tabPbFamily.setUpdateUsername(UserContextHolder.getUserName());
        int retVal = pbFamilyMapper.deleteByPrimaryKey(tabPbFamily);
        if (retVal > 0){
            userMapper.flagDelete(tabPbFamily.getRelationUserId());
        }
        return retVal;
    }

    /**
     * 保存实体
     *
     * @param tabPbFamily
     */
    @Override
    @PaddingBaseField(recursive = true)
    public int add(TabPbFamily tabPbFamily) {
        var user = this.userMapper.selectUserByIdCardNo(tabPbFamily.getIdCardNo());
        if(user != null) {
            tabPbFamily.setRelationUserId(user.getUserId().longValue());
            int retVal = pbFamilyMapper.insertSelective(tabPbFamily);
            return retVal;
        }else {
            SysUser sysUser = new SysUser();
            BeanUtil.copyPropertiesIgnoreNull(tabPbFamily, sysUser);
            sysUser.setRealname(sysUser.getUsername());
            int retVal = userMapper.saveEntity(sysUser);
            if (retVal > 0) {
                tabPbFamily.setRelationUserId(sysUser.getUserId().longValue());
                pbFamilyMapper.insertSelective(tabPbFamily);
            }
            return retVal;
        }
    }

    @Override
    @PaddingBaseField(updateOnly = true)
    public int updateByPrimaryKeySelective(TabPbFamily tabPbFamily) {
        pbFamilyMapper.updateByPrimaryKeySelective(tabPbFamily);
        SysUser sysUser = new SysUser();
        TabPbFamily family = pbFamilyMapper.findById(tabPbFamily.getRelationId());
        BeanUtil.copyPropertiesIgnoreNull(tabPbFamily, sysUser);
        sysUser.setUserId(family.getRelationUserId().intValue());
        if (sysUser.getUsername() != null && !"".equals(sysUser.getUsername())) {
            sysUser.setRealname(sysUser.getUsername());
        }
        return sysUserMapper.updateById(sysUser);
    }
}
