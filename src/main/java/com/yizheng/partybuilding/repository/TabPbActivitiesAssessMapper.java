package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.AssessUserDto;
import com.yizheng.partybuilding.dto.TabPbActivitiesAssessDto;
import com.yizheng.partybuilding.entity.TabPbActivitiesAssess;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TabPbActivitiesAssessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TabPbActivitiesAssess record);

    int insertSelective(TabPbActivitiesAssess record);

    TabPbActivitiesAssess selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbActivitiesAssess record);

    int updateByPrimaryKey(TabPbActivitiesAssess record);

    /**
     * 根据条件查询
     *
     * @param conditions
     * @return
     */
    List<TabPbActivitiesAssessDto> selectWithConditions(Map<String, Object> conditions);

    /**
     * 根据用户id和评议年度查询信息
     *
     * @param userId 被评议党员
     * @param year   年度
     * @return
     */
    TabPbActivitiesAssess selectByUserIdAndYear(@Param("userId") Long userId, @Param("year") Integer year);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    int logicDeleteById(Long id);

    /**
     * 详情包含相关信息
     *
     * @param id
     * @return
     */
    TabPbActivitiesAssessDto detailWithAbout(Long id);

    List<AssessUserDto> selectPreAssessMemberWithConditions(Map<String, Object> conditions);

    /**
     * 批量新增
     *
     * @param pendingInsertList
     * @return
     */
    int batchInsert(List<TabPbActivitiesAssess> pendingInsertList);

    int signInById(Long userId);
}