package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Dictionary;
import com.egovchina.partybuilding.common.util.RedisKeyConstant;
import com.egovchina.partybuilding.partybuild.entity.SysDict;
import com.egovchina.partybuilding.partybuild.repository.TabSysDictMapper;
import com.egovchina.partybuilding.partybuild.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.egovchina.partybuilding.common.util.RedisKeyConstant.DICT_DETAIL_FOR_FORMAT;

@Service("sysDictService")
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    TabSysDictMapper tabSysDictMapper;

    @Override
    public SysDict selectById(Integer id) {
        return tabSysDictMapper.selectByPrimaryKey(id);
    }


    @Cacheable(value = DICT_DETAIL_FOR_FORMAT, key = "#dictValue")
    @Override
    public Dictionary findOneUseForDictSerialization(String dictType, String dictValue) {
        return tabSysDictMapper.findOneUseForDictSerialization(dictType, dictValue);
    }

}
