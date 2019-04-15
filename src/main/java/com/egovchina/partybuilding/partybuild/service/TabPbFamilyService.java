package com.egovchina.partybuilding.partybuild.service;

import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.partybuild.entity.TabPbFamily;

import java.util.List;

/**
 * @author YangYingXiang on 2018/11/26
 */
public interface TabPbFamilyService extends IService<TabPbFamily> {
    /**
     * 根据党员id加载list数据
     * @param partyMemberId
     * @return
     */
    List<TabPbFamily> getListPage(Long partyMemberId);

    /**
     * 根据主键ID查询单条记录
     * @param relationId
     * @return
     */
    TabPbFamily selectByPrimaryKey(Long relationId);

    /**
     * 根据主键ID删除单条记录
     * @param relationId
     */
    int deleteByPrimaryKey(Long relationId);

    /**
     * 保存实体
     * @param tabPbFamily
     */
    int add(TabPbFamily tabPbFamily);

    /**
     * 更新不为null的数据
     * @param tabPbFamily
     */
    int updateByPrimaryKeySelective(TabPbFamily tabPbFamily);


}
