package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.SysTaskConfigurer;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

public interface SysTaskConfigurerMapper {
    int insert(SysTaskConfigurer configurer);

    int insertSelective(SysTaskConfigurer configurer);

    int updateByPrimaryKeySelective(SysTaskConfigurer configurer);

    /**
     * 根据条件查询
     *
     * @param conditions
     * @return
     */
    List<SysTaskConfigurer> selectWithConditions(Map<String, Object> conditions);

    /**
     * 根据id逻辑删除
     *
     * @param id
     * @return
     */
    int logicDeleteById(Long id);

    /**
     * 根据任务id、履行组织id和年度查询
     *
     * @param taskId    任务id
     * @param fulfillId 履行组织id
     * @param year      年度
     * @return
     */
    SysTaskConfigurer selectByTaskIdAndFulfillIdAndYear(@Param("taskId") String taskId, @Param("fulfillId") String fulfillId, @Param("year") String year);

    /**
     * 获取指定组织下待新增的配置数据
     *
     * @param orgId
     * @param year
     * @param taskId
     * @return
     */
    List<Long> selectCandidateFulfillIds(@Param("orgId") Long orgId, @Param("year") String year, @Param("taskId") String taskId);

    /**
     * 批量新增
     *
     * @param pendingInsertList
     * @return
     */
    int batchInsert(List<SysTaskConfigurer> pendingInsertList);

    /**
     * 先删除
     * @param configurer 配置对象
     * @return
     */
    int aheadDelete(SysTaskConfigurer configurer);
}