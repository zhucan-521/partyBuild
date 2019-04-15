package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.DictDto;
import com.egovchina.partybuilding.partybuild.system.dto.DictTree;
import com.egovchina.partybuilding.partybuild.system.entity.SysDict;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * Date: 2018/11/28
 */
public interface ITabSysDictService {
    /**
     * 根据id查询字典
     * @param id
     * @return
     */
    SysDict selectById(Integer id);

    /**
     * 根据Type查询字典
     * @param type
     * @return
     */
    SysDict selectOneByType(String type);

    /**
     * 分页查询字典
     * @param params
     * @return
     */
    PageInfo<SysDict> selectPage(Map<String, Object> params);

    /**
     * 无分页查询
     * @param sysDict
     * @return
     */
    List<SysDict> selectLists(SysDict sysDict);

    /**
     * 获取根目录字典
     * @return
     */
    List<SysDict> rootDictList();

    /**
     * 通过字典类型查找字典
     * @param type
     * @return
     */
    List<SysDict> selectListByType(String type);


    List<DictTree> selectTreeByType(String type);

    /**
     * 添加字典
     * @param sysDict
     * @return
     */
    Boolean insert(SysDict sysDict);

    /**
     *  删除字典
     * @param id
     * @return
     */
    Boolean deleteById(Integer id);

    /**
     * 修改字典
     * @param sysDict
     * @return
     */
    Boolean updateById(SysDict sysDict);

    /**
     * 根据字典类型和字典值查询字典信息
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @return
     */
    DictDto findOneUseForDictSerialization(String dictType, String dictValue);
}
