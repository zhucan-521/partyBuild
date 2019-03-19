package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduLiveDto;
import com.yizheng.partybuilding.entity.TabPbEduLive;

import java.util.List;

public interface TabPbEduLiveService {
    int insertSelective(TabPbEduLive tabPbEduLive);
    int deleteByPrimaryKey(Long liveId);

    int updateLive(TabPbEduLiveDto tabPbEduLiveDto);

    List<TabPbEduLiveDto> selectLive(TabPbEduLive tabPbEduLive, Page page);

    TabPbEduLiveDto getLiveByLiveId(Long liveId);
}
