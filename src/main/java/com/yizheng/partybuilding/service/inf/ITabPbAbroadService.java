package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbAbroad;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/11/26
 */
public interface ITabPbAbroadService {

    int add(TabPbAbroad abroad);

    PageInfo<TabPbAbroad> selectList(Long abroadId, Long orgId,Long orgRange, Long userId, String userName, String idCardNo, Page page);

    int delete(Long abroadId);

    int update(TabPbAbroad abroad);
}
