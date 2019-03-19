package com.yizheng.partybuilding.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduCommunication;
import com.yizheng.partybuilding.repository.TabPbEduCommunicationMapper;
import com.yizheng.partybuilding.service.inf.TabPbEduCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author YangYingXiang on 2018/12/13
 */
@Service
@Transactional
public class TabPbEduCommunicationServiceImpl extends ServiceImpl<TabPbEduCommunicationMapper, TabPbEduCommunication> implements TabPbEduCommunicationService {

    @Autowired
    private TabPbEduCommunicationMapper communicationMapper;

    @Override
    @PaddingBaseField()
    public int add(TabPbEduCommunication communication) {
        return communicationMapper.insertSelective(communication);
    }

    @Override
    @PaddingBaseField(updateOnly = true)
    public int edit(TabPbEduCommunication communication) {
        return communicationMapper.updateByPrimaryKeySelective(communication);
    }

    @Override
    public int deleteFindById(Long id) {
        TabPbEduCommunication communication = new TabPbEduCommunication();
        communication.setId(id);
        communication.setUpdateTime(new Date());
        communication.setUpdateUserid(UserContextHolder.getUserIdLong());
        communication.setUpdateUsername(UserContextHolder.getUserName());
        return communicationMapper.flagDelete(communication);
    }

    @Override
    public List<TabPbEduCommunication> communicationList(Long type, Page page) {
        if (page != null) {
            PageHelper.startPage(page);
        }
        return communicationMapper.selectKey(new TabPbEduCommunication().setType(type));
    }

    @Override
    public TabPbEduCommunication findByIdData(Long id) {
        return communicationMapper.selectById(id);
    }
}
