package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Dictionary;
import com.egovchina.partybuilding.partybuild.entity.SysDict;

/**
 * @author: huang
 * Date: 2018/11/28
 */
public interface SysDictService {
    /**
     * 根据id查询字典
     *
     * @param id
     * @return
     */
    SysDict selectById(Integer id);

    /**
     * 根据字典类型和字典值查询字典信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return
     */
    Dictionary findOneUseForDictSerialization(String dictType, String dictValue);
}
