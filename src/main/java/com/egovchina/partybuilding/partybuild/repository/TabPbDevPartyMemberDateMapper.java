package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbDevPartyMemberDate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbDevPartyMemberDateMapper {
    int deleteByPrimaryKey(Long timeId);

    int insert(TabPbDevPartyMemberDate record);

    int insertSelective(TabPbDevPartyMemberDate record);

    TabPbDevPartyMemberDate selectByPrimaryKey(Long timeId);

    int updateByPrimaryKeySelective(TabPbDevPartyMemberDate record);

    int updateByPrimaryKey(TabPbDevPartyMemberDate record);

    List<TabPbDevPartyMemberDate> selectByHostId(Long hostId);
}