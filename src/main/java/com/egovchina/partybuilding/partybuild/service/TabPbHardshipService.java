package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbHardship;

import java.util.List;
import java.util.Map;

/**
 * @Author Jiang An
 **/
public interface TabPbHardshipService {

    int deleteByPrimaryKey(Long hardshipId);

     int deleteId(Long hardshipId);

    int insert(TabPbHardship record);

    int insertSelective(TabPbHardship record);

    TabPbHardship selectByPrimaryKey(Long hardshipId);

    int updateByPrimaryKeySelective(TabPbHardship record );

    int updateByPrimaryKeyWithBLOBs(TabPbHardship record);

    int updateByPrimaryKey(TabPbHardship record);

    /**
     *  根据条件查询
     * @param conditions
     * @param page
     * @return
     */
    List<TabPbHardship> selectWithConditions(Map<String, Object> conditions, Page page);

    int updateUsernaneById(TabPbHardship userId);

    //单个查询详情
    TabPbHardship selectAndDeleteId(Long hardshipId);

    /**
     *根据userId查询
     * @param userId
     * @return
     */
    TabPbHardship selectByUserId(Long userId);
}
