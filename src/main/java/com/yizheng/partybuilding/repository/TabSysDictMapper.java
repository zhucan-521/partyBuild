package com.yizheng.partybuilding.repository;


import com.yizheng.commons.domain.DictDto;
import com.yizheng.partybuilding.system.dto.DictTree;
import com.yizheng.partybuilding.system.entity.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabSysDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Integer id);

    SysDict selectOneByType(String type);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    List<SysDict> selectLists(SysDict sysDict);

    List<DictTree> selectTree(SysDict sysDict);

    DictDto findOneUseForDictSerialization(@Param("dictType") String dictType, @Param("dictValue") String dictValue);
}