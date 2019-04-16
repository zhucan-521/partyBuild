package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbAbroad;
import com.github.pagehelper.PageInfo;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/11/26
 */
public interface TabPbAbroadService {

    int add(TabPbAbroad abroad);

    PageInfo<TabPbAbroad> selectList(Long abroadId, Long orgId,Long orgRange, Long userId, String userName, String idCardNo, Page page);

    int delete(Long abroadId);

    int update(TabPbAbroad abroad);
}