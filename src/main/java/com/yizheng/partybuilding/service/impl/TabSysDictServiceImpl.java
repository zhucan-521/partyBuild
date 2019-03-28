package com.yizheng.partybuilding.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.DictDto;
import com.yizheng.commons.util.BeanUtil;
import com.yizheng.partybuilding.repository.TabSysDictMapper;
import com.yizheng.partybuilding.service.inf.ITabSysDictService;
import com.yizheng.partybuilding.system.dto.DictTree;
import com.yizheng.partybuilding.system.entity.SysDict;
import com.yizheng.partybuilding.system.util.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * Date: 2018/11/28
 */
@Service("tabSysDictService")
public class TabSysDictServiceImpl implements ITabSysDictService {

    @Autowired
    TabSysDictMapper tabSysDictMapper;

    @Override
    public SysDict selectById(Integer id) {
        return tabSysDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public SysDict selectOneByType(String type) {
        return tabSysDictMapper.selectOneByType(type);
    }

    @Override
    public PageInfo<SysDict> selectPage(Map<String, Object> params) {
        SysDict sysDict = JSONObject.parseObject(JSONObject.toJSONString(params), SysDict.class);
        if (params.containsKey("page") && params.containsKey("limit")) {
            PageHelper.startPage(Integer.parseInt((String) params.get("page")), Integer.parseInt((String) params.get("limit")));
        }
        return new PageInfo<>(this.selectLists(sysDict));
    }

    @Override
    public List<SysDict> rootDictList() {
        SysDict sysDict = new SysDict();
        sysDict.setParentId(CommonConstant.ROOT_DICT);
        sysDict.setDelFlag("1");
        return selectLists(sysDict);
    }

    @Override
    @Cacheable(value = "DETAIL::DICT::BY_TYPE", key = "#type")
    public List<SysDict> selectListByType(String type) {
        SysDict sysDict = new SysDict();
        sysDict.setType(type);
        return selectLists(sysDict);
    }

    @Override
    public List<DictTree> selectTreeByType(String type) {
        List<DictTree> dictTrees = new ArrayList<>();
        List<SysDict> dices = selectListByType(type);
        dices.forEach(dt -> {
            DictTree newTree = new DictTree();
            BeanUtil.copyPropertiesIgnoreNull(dt, newTree);
            dictTrees.add(newTree);
        });
        return dictTrees;
    }

    @Override
    public Boolean insert(SysDict sysDict) {
        return tabSysDictMapper.insertSelective(sysDict) > 0;
    }

    @CacheEvict(value = "DETAIL::DICT_FORMAT", key = "#id")
    @Override
    public Boolean deleteById(Integer id) {
        SysDict sysDict = new SysDict();
        sysDict.setId(id);
        sysDict.setDelFlag(CommonConstant.STATUS_DEL);
        return tabSysDictMapper.updateByPrimaryKeySelective(sysDict) > 0;
    }

    @CacheEvict(value = "DETAIL::DICT_FORMAT", key = "#sysDict.id")
    @Override
    public Boolean updateById(SysDict sysDict) {
        if (sysDict != null && sysDict.getId() == 0) {
            return false;
        }
        return tabSysDictMapper.updateByPrimaryKeySelective(sysDict) > 0;
    }

    @Cacheable(value = "DETAIL::DICT_FORMAT", key = "#dictValue")
    @Override
    public DictDto findOneUseForDictSerialization(String dictType, String dictValue) {
        return tabSysDictMapper.findOneUseForDictSerialization(dictType, dictValue);
    }

    @Override
    public List<SysDict> selectLists(SysDict sysDict) {
        return tabSysDictMapper.selectLists(sysDict);
    }
}
