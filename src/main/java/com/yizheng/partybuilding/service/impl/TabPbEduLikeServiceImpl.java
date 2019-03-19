package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduLike;
import com.yizheng.partybuilding.repository.TabPbEduLikeMapper;
import com.yizheng.partybuilding.service.inf.TabPbEduLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabPbEduLikeServiceImpl implements TabPbEduLikeService {

    @Autowired
    TabPbEduLikeMapper tabPbEduLikeMapper;


    /**
     * 点赞次数新增（请给实体附带业务id和业务类型）
     * @param tabPbEduLike
     * @return  返回点赞主键id
     */
    @Override
    public int addTabPbEduLikeService(TabPbEduLike tabPbEduLike) {
        tabPbEduLike.setUserId(UserContextHolder.getUserId().longValue());
        tabPbEduLikeMapper.insertSelective(tabPbEduLike);
        return tabPbEduLike.getLikeId().intValue();
    }


    /**
     * 根据业务id和业务类型查询
     * @param tabPbEduLike
     * @param page
     * @return
     */
    @Override
    public PageInfo<TabPbEduLike> selectTabPbEduLikeSelective(TabPbEduLike tabPbEduLike, Page page) {
        PageHelper.startPage(page);
        List<TabPbEduLike> list= tabPbEduLikeMapper.selectTabPbEduLikeSelective(tabPbEduLike);
        PageInfo<TabPbEduLike> pageInfo=new PageInfo(list);
        return pageInfo;
    }

    /**
     * 修改
     * @param tabPbEduLike
     * @return
     */
    @Override
    public int updataTabPbEduLikeSelective(TabPbEduLike tabPbEduLike) {
        return tabPbEduLikeMapper.updateByPrimaryKeySelective(tabPbEduLike);
    }


    /**
     * 删除
     * @param likeId
     * @return
     */
    @Override
    public int deleteTabPbEduLikeById(Long likeId) {
        TabPbEduLike tabPbEduLike=new TabPbEduLike();
        tabPbEduLike.setLikeId(likeId);
        tabPbEduLike.setDelFlag("1");
        return tabPbEduLikeMapper.updateByPrimaryKeySelective(tabPbEduLike);
    }


    /**
     * 根据业务类型和业务id获取点赞次数
     * @param busId
     * @param likeType
     * @return
     */
    @Override
    public Long selectLikeCount(Long busId, String likeType) {
        TabPbEduLike tabPbEduLike=new TabPbEduLike();
        tabPbEduLike.setBusId(busId);
        tabPbEduLike.setLikeType(likeType);
        return  tabPbEduLikeMapper.selectLikeCountByBusIdAndLikeType(tabPbEduLike);
    }


}
