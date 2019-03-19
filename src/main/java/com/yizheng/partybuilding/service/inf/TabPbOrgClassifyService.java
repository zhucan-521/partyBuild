package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbOrgClassify;

import java.util.List;
import java.util.Map;

/**
 * 分类定等service接口
 *
 * @Author Zhang Fan
 **/
public interface TabPbOrgClassifyService {

    int deleteByPrimaryKey(Long orgClassifyId);

    int insert(TabPbOrgClassify record);

    int insertSelective(TabPbOrgClassify record);

    TabPbOrgClassify selectByPrimaryKey(Long orgClassifyId);

    int updateByPrimaryKeySelective(TabPbOrgClassify record);

    int updateByPrimaryKey(TabPbOrgClassify record);

    /**
     * 根据组织ID查询定等数据
     * @param deptId 组织ID
     * @return
     */
    List<TabPbOrgClassify> selectByDeptId(Long deptId);

    int logicDeleteById(Long orgClassifyId);

    List<TabPbOrgClassify> selectWithConditions(Map<String, Object> conditions, Page page);
}
