package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduSubscribe;

import java.util.List;

public interface TabPbEduSubscribeMapper {
    int deleteByPrimaryKey(Long subscribeId);

    int insert(TabPbEduSubscribe record);

    int insertSelective(TabPbEduSubscribe record);

    TabPbEduSubscribe selectByPrimaryKey(Long subscribeId);

    int updateByPrimaryKeySelective(TabPbEduSubscribe record);

    int updateByPrimaryKey(TabPbEduSubscribe record);


    List<TabPbEduSubscribe> selectSelective(TabPbEduSubscribe tabPbEduSubscribe);

    Long selectSubScribeCount(TabPbEduSubscribe tabPbEduSubscribe);

    /**
     * 用来判断一个人是否预约过同一个业务，如果没有预约则返回空
     * @param
     * @return
     */
    TabPbEduSubscribe selectTabPbEduSubscribeByUserId(TabPbEduSubscribe tabPbEduSubscribe);

    /**
     * 根据业务类型返回预约人数
     * @param
     * @return
     */
    Long selectSubCount(TabPbEduSubscribe tabPbEduSubscribe);
}