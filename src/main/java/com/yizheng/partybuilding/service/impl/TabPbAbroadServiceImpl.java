package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbAbroad;
import com.yizheng.partybuilding.repository.TabPbAbroadMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAbroadService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/11/26
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class TabPbAbroadServiceImpl implements ITabPbAbroadService {

    @Autowired
    private TabPbAbroadMapper abroadMapper;

    /**
     * 添加党员信息
     *
     * @param abroad 党员出国出境的信息
     * @return 返回的结果包装类
     */
    @Override
    public int add(TabPbAbroad abroad) {
        return abroadMapper.insertSelective(abroad);
    }

    /**
     * 获取党员出入境列表信息
     *
     * @param abroadId
     * @param page     分页信息
     * @return 包装返回结果
     */
    @Override
    public PageInfo<TabPbAbroad> selectList(Long abroadId, Long orgId, Long orgRange, Long userId, String userName, String idCardNo, Page page) {
        PageHelper.startPage(page);
        var temp = new TabPbAbroad()
                .setAbroadId(abroadId)
                .setOrgId(orgId)
                .setUserId(userId)
                .setUserName(userName)
                .setIdCardNo(idCardNo);
        var list = this.abroadMapper.selectBySomeInfo(temp);
        return new PageInfo<>(list);
    }


    @Override
    public int delete(Long abroadId) {
        var temp = new TabPbAbroad()
                .setDelFlag("1")
                .setAbroadId(abroadId)
                .setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong());
        return abroadMapper.updateByPrimaryKeySelective(temp);

    }

    @Override
    public int update(TabPbAbroad abroad) {
        abroad.setDelFlag(null)
                .setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong())
                .setEblFlag(null);
        return abroadMapper.updateByPrimaryKeySelective(abroad);
    }

}

