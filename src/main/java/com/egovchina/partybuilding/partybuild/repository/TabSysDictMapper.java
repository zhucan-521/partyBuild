package com.egovchina.partybuilding.partybuild.repository;


import com.egovchina.partybuilding.common.entity.Dictionary;
import com.egovchina.partybuilding.partybuild.entity.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TabSysDictMapper {

    SysDict selectByPrimaryKey(Long id);

    Dictionary findOneUseForDictSerialization(@Param("dictType") String dictType, @Param("dictValue") String dictValue);
}