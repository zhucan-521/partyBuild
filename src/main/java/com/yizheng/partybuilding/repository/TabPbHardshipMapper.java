package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbHardship;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabPbHardshipMapper {
    int deleteByPrimaryKey(Long hardshipId);

    int insert(TabPbHardship record);


    int insertSelective(TabPbHardship record);

    TabPbHardship selectByPrimaryKey(Long hardshipId);

    int updateByPrimaryKeySelective(TabPbHardship record);

    int updateByPrimaryKeyWithBLOBs(TabPbHardship record);

    int updateByPrimaryKey(TabPbHardship record);

    List<TabPbHardship>selectWithConditions(Map<String,Object>conditions);

    int updateUsernameById(TabPbHardship userId);

    TabPbHardship  selectAndDeleteId(Long hardshipId);

    int deleteId(Long hardshipId);

    TabPbHardship selectByUserId(Long userId);
}