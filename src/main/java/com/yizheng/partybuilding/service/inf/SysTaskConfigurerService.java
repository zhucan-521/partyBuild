package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.SysTaskConfigurer;

import java.util.List;
import java.util.Map;

/**
 * 系统任务配置service接口
 *
 * @Author Zhang Fan
 **/
public interface SysTaskConfigurerService {

    /**
     * 根据条件查询
     *
     * @param conditions 条件查询集
     * @param page       分页参数对象
     * @return
     */
    List<SysTaskConfigurer> selectWithConditions(Map<String, Object> conditions, Page page);

    /**
     * 修改配置
     *
     * @param configurer
     * @return
     */
    int updateConfigurer(SysTaskConfigurer configurer);

    /**
     * 根据id逻辑删除
     *
     * @param id
     * @return
     */
    int logicDeleteById(Long id);

    /**
     * 新增前删除
     *
     * @param configurer
     * @return
     */
    int insertAheadDelete(SysTaskConfigurer configurer);
}
