package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.SysTaskConfigurer;
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
    SysTaskConfigurer selectByTaskIdAndFulfillIdAndYear(@Param("taskId") @NotNull(message = "任务ID不能为空") String taskId, @Param("fulfillId") @NotNull(message = "履行组织ID不能为空") String fulfillId, @Param("year") @NotNull(message = "年度不能为空") @Pattern(regexp = "\\d{4}", message = "年度格式不正确") String year);

    /**
     * 获取指定组织下待新增的配置数据
     *
     * @param orgId
     * @param year
     * @param taskId
     * @return
     */
    List<Long> selectCandidateFulfillIds(@Param("orgId") Long orgId, @Param("year") @NotNull(message = "年度不能为空") @Pattern(regexp = "\\d{4}", message = "年度格式不正确") String year, @Param("taskId") @NotNull(message = "任务ID不能为空") String taskId);

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