package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPositives;
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

    /**
     * 批量保存职务
     * @param pbPositivesList
     * @return
     */
    int batchAdd(List<TabPbPositives> pbPositivesList);

    /**
     * 批量逻辑删除
     * @param pbPositivesList
     * @return
     */
    int tombstone(List<TabPbPositives> pbPositivesList);

    /**
     * 根据userId删除所有职务
     * @param userId
     * @return
     */
    int tombstoneUser(Long userId);

    /**
     * 根据用户id查找他的职务
     * @param userId
     * @return
     */
    List<TabPbPositives> selectTabPbPositivesByUserId(Long userId);



    /**
     * 批量保存职务
     * @param pbPositivesList
     * @return
     */
    int batchInsertPositivesList(List<TabPbPositives> pbPositivesList);
}