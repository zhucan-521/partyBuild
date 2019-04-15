package com.egovchina.partybuilding.partybuild.service;

import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositiveRegist;

import java.util.List;

/**
 * @author YangYingXiang on 2018/11/29
 */
public interface TabPbPositiveRegistService extends IService<TabPbPositiveRegist> {

    int add(TabPbPositiveRegist positiveRegistList);

    List<TabPbPositiveRegist> selectListPage(String userName,Byte revokeTag,String date,Long rangeDeptId,Long orgRange,Page page);

    /**
     * 修改报到状态
     * @param positiveRegistId
     */
    int editPositive(Long positiveRegistId, Byte revokeTag);

    /**
     * 逻辑删除
     * @param positiveRegistId
     */
    int delete(Long positiveRegistId);

    /**
     * 根据userId判断是否需要删除
     * @param userId
     */
    int delectRegistStatus(Long userId);

    TabPbPositiveRegist editFindById(Long positiveRegistId);

}
