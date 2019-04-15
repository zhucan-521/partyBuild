package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSubscribe;
import com.github.pagehelper.PageInfo;

public interface TabPbEduSubscribeSerivce {

    int insert(TabPbEduSubscribe tabPbEduSubscribe);

    PageInfo<TabPbEduSubscribe> select(TabPbEduSubscribe tabPbEduSubscribe, Page page);

    int update(TabPbEduSubscribe tabPbEduSubscribe);

    int delete(Long id);

    Long selectSubscribeCount(Long busId,String likeType);

    TabPbEduSubscribe selectOneById(Long id);
}
