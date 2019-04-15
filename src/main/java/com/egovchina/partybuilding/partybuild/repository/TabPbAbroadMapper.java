package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbAbroad;

import java.util.List;

/**
 * @author chenshanlu
 */
public interface TabPbAbroadMapper {

    int insertSelective(TabPbAbroad record);

    TabPbAbroad selectByPrimaryKey(Long abroadId);

    int updateByPrimaryKeySelective(TabPbAbroad record);

    List<TabPbAbroad> selectBySomeInfo(TabPbAbroad record);
}