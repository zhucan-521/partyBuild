package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSubscribe;
import com.egovchina.partybuilding.partybuild.repository.TabPbEduSubscribeMapper;
import com.egovchina.partybuilding.partybuild.service.TabPbEduSubscribeSerivce;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabPbEduSubscribeSerivceImpl implements TabPbEduSubscribeSerivce {

    @Autowired
    TabPbEduSubscribeMapper tabPbEduSubscribeMapper;


    /**
     * @param tabPbEduSubscribe
     * @return 返回预约人数
     */
    @Override
    public int insert(TabPbEduSubscribe tabPbEduSubscribe) {
        Long userId= UserContextHolder.getUserIdLong();
        tabPbEduSubscribe.setUserId(userId);
        TabPbEduSubscribe flag=tabPbEduSubscribeMapper.selectTabPbEduSubscribeByUserId(tabPbEduSubscribe);
        if(null!=flag){
            throw new BusinessDataCheckFailException("你已经预约了,不需要在次预约！");
        }
        tabPbEduSubscribeMapper.insertSelective(tabPbEduSubscribe);
        return  tabPbEduSubscribeMapper.selectSubCount(tabPbEduSubscribe).intValue();
    }

    /**
     * 条件查询预约列表
     * @param tabPbEduSubscribe
     * @param page
     * @return
     */
    @Override
    public PageInfo<TabPbEduSubscribe> select(TabPbEduSubscribe tabPbEduSubscribe, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduSubscribe> list=tabPbEduSubscribeMapper.selectSelective(tabPbEduSubscribe);
        PageInfo<TabPbEduSubscribe> pageInfo=new PageInfo(list);
        return pageInfo;
    }

    /**
     * 根据id预约修改
     * @param tabPbEduSubscribe
     * @return
     */
    @Override
    public int update(TabPbEduSubscribe tabPbEduSubscribe) {
        return tabPbEduSubscribeMapper.updateByPrimaryKeySelective(tabPbEduSubscribe);
    }

    /**
     * 删除根据id
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {
        TabPbEduSubscribe tabPbEduSubscribe=new TabPbEduSubscribe();
        tabPbEduSubscribe.setSubscribeId(id);
        tabPbEduSubscribe.setDelFlag("1");
        return tabPbEduSubscribeMapper.updateByPrimaryKeySelective(tabPbEduSubscribe);
    }

    /**
     *查询预约次数
     * @param busId
     * @param likeType
     * @return
     */
    @Override
    public Long selectSubscribeCount(Long busId, String likeType) {
        TabPbEduSubscribe tabPbEduSubscribe = new TabPbEduSubscribe();
        tabPbEduSubscribe.setBusId(busId);
        tabPbEduSubscribe.setLikeType(likeType);
        return tabPbEduSubscribeMapper.selectSubScribeCount(tabPbEduSubscribe);
    }


    /**
     * 查看预约详情
     * @param id
     * @return
     */
    @Override
    public TabPbEduSubscribe selectOneById(Long id) {
        return tabPbEduSubscribeMapper.selectByPrimaryKey(id);
    }


}
