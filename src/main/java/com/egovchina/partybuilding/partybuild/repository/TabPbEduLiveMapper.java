package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabPbEduLiveDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduLive;

import java.util.List;

public interface TabPbEduLiveMapper {
    int deleteByPrimaryKey(Long liveId);

    int insert(TabPbEduLive record);

    int insertSelective(TabPbEduLive record);

    TabPbEduLive selectByPrimaryKey(Long liveId);

    int updateByPrimaryKeySelective(TabPbEduLive record);

    int updateByPrimaryKey(TabPbEduLive record);

    List<TabPbEduLiveDto> selectLiveByTabPbEduLive(TabPbEduLive live);

}