package com.yizheng.partybuilding.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduComParticipate;
import com.yizheng.partybuilding.repository.TabPbEduComParticipateMapper;
import com.yizheng.partybuilding.service.inf.TabPbEduComParticipateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YangYingXiang on 2018/12/14
 */
@Service("TabPbEduComParticipateService")
@Transactional
public class TabPbEduComParticipateServiceImpl extends ServiceImpl<TabPbEduComParticipateMapper, TabPbEduComParticipate> implements TabPbEduComParticipateService {

    @Autowired
    private TabPbEduComParticipateMapper participateMapper;

    @Override
    public int add(TabPbEduComParticipate participate) {
        return participateMapper.insertSelective(participate);
    }

    @Override
    public int edit(TabPbEduComParticipate participate) {
        return participateMapper.updateByPrimaryKeySelective(participate);
    }

    @Override
    public int deleteFindById(Long id) {
        return participateMapper.flagDelete(id);
    }

    @Override
    public List<TabPbEduComParticipate> listParticipate(TabPbEduComParticipate participate, Page page) {
        if (page != null) {
            PageHelper.startPage(page);
        }
        return participateMapper.selectData(participate);
    }

    @Override
    public TabPbEduComParticipate findByIdData(Long id) {
        return this.selectById(id);
    }

    @Override
    public int changeState(Long id, String state, String shResult) {
        return participateMapper.changeState(new TabPbEduComParticipate().setId(id).setState(state).setShResult(shResult));
    }
}
