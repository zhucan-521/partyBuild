package com.yizheng.partybuilding.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.entity.TabPbPositives;
import com.yizheng.partybuilding.repository.TabPbPositivesMapper;
import com.yizheng.partybuilding.service.inf.ITabPbPositivesService;
import com.yizheng.partybuilding.system.util.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * Date: 2018/11/30
 */
@Service("tabPbPositivesService")
public class TabPbPositivesServiceImpl implements ITabPbPositivesService {

    @Autowired
    TabPbPositivesMapper tabPbPositivesMapper;

    @Override
    public int insert(TabPbPositives positives) {
        positives.setDelFlag(CommonConstant.STATUS_NORMAL);
        positives.setEblFlag("1");
        return tabPbPositivesMapper.insertSelective(positives);
    }

    @Override
    public int deleteById(Integer id) {
        if (ObjectUtils.isEmpty(id)){
            return 0;
        }
        TabPbPositives positives = new TabPbPositives();
        positives.setPositiveId(id);
        positives.setDelFlag(CommonConstant.STATUS_DEL);
        return updateById(positives);
    }

    @Override
    public int updateById(TabPbPositives positives) {
        if (positives.getPositiveId() == null || positives.getPositiveId() == 0){
            return 0;
        }
        positives.setUpdateTime(new Date());
        positives.setCreateUserid(Integer.toUnsignedLong(UserContextHolder.getUserId()));
        positives.setCreateUsername(UserContextHolder.getUserName());
        return tabPbPositivesMapper.updateByPrimaryKeySelective(positives);
    }

    @Override
    public TabPbPositives selectById(Integer id) {
        return tabPbPositivesMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TabPbPositives> selectList(Map<String, Object> params) {
        TabPbPositives positives = JSONObject.parseObject(JSONObject.toJSONString(params), TabPbPositives.class);
        return tabPbPositivesMapper.selectList(positives);
    }
}
