package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbEduLiveDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduLive;

import java.util.List;

public interface TabPbEduLiveService {
    int insertSelective(TabPbEduLive tabPbEduLive);
    int deleteByPrimaryKey(Long liveId);

    int updateLive(TabPbEduLiveDto tabPbEduLiveDto);

    List<TabPbEduLiveDto> selectLive(TabPbEduLive tabPbEduLive, Page page);

    TabPbEduLiveDto getLiveByLiveId(Long liveId);
}
