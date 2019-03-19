package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.entity.TabPbPositives;

import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * Date: 2018/11/30
 */
public interface ITabPbPositivesService {
    int insert(TabPbPositives positives);

    int deleteById(Integer id);

    int updateById(TabPbPositives positives);

    TabPbPositives selectById(Integer id);

    List<TabPbPositives> selectList(Map<String, Object> params);

}
