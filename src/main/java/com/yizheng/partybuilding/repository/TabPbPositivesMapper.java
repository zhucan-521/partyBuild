package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbPositives;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPositivesMapper {
    int deleteByPrimaryKey(Integer positiveId);

    int insert(TabPbPositives record);

    int insertSelective(TabPbPositives record);

    TabPbPositives selectByPrimaryKey(Integer positiveId);

    int updateByPrimaryKeySelective(TabPbPositives record);

    int updateByPrimaryKeyWithBLOBs(TabPbPositives record);

    int updateByPrimaryKey(TabPbPositives record);

    List<TabPbPositives> selectList(TabPbPositives tabPbPositives);

    /**
     * 根据userId查询党内职务，党员信息列表使用
     * @param tabPbPositives
     * @return
     */
    List<TabPbPositives> selectPositives(TabPbPositives tabPbPositives);

    List<TabPbPositives> verifyDuplicateDuties(TabPbPositives tabPbPositives);
}