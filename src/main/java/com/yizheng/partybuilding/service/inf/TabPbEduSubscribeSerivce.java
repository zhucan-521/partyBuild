package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbEduSubscribe;

public interface TabPbEduSubscribeSerivce {

    int insert(TabPbEduSubscribe tabPbEduSubscribe);

    PageInfo<TabPbEduSubscribe> select(TabPbEduSubscribe tabPbEduSubscribe, Page page);

    int update(TabPbEduSubscribe tabPbEduSubscribe);

    int delete(Long id);

    Long selectSubscribeCount(Long busId,String likeType);

    TabPbEduSubscribe selectOneById(Long id);
}
