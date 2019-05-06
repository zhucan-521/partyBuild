package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPositives;
import com.egovchina.partybuilding.partybuild.vo.PositivesVO;
import com.egovchina.partybuilding.partybuild.vo.SecretariesPostivesVO;
import org.apache.ibatis.annotations.Param;
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
    List<PositivesVO> selectPositives(TabPbPositives tabPbPositives);

    List<SecretariesPostivesVO> selectPositivesName(Long userId);

    List<TabPbPositives> verifyDuplicateDuties(TabPbPositives tabPbPositives);

    /**
     * 批量保存职务
     * @param pbPositivesList
     * @return
     */
    int batchAdd(List<TabPbPositives> pbPositivesList);

    /**
     * 批量保存职务
     * @param pbPositivesList
     * @return
     */
    int batchInsertPositivesList(List<TabPbPositives> pbPositivesList);

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

    PositivesVO selectByIdToPositivesVO(Integer positiveId);

    List<PositivesVO> selectByIdToAllPositivesVO(@Param("userId") Long userId, @Param("positiveType") String positiveType);

    /**
     * 根据人员id获取他的职位
     * @param userId
     * @return
     */
    List<PositivesVO> selectTabPbPositivesByUserId(Long userId);

    /**
     * 根据人员id获取他最高的职位
     * @param userId
     * @return
     */
    List<PositivesVO> selectMaxTabPbPositivesByUserId(Long userId);

    /**
     * 根据条件逻辑删除职务
     *
     * @param delete 删除实体
     */
    int logicDeletePositiveByCondition(TabPbPositives delete);


}