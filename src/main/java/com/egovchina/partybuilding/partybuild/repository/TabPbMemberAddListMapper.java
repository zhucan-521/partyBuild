package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbMemberAddList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbMemberAddListMapper {
    int deleteByPrimaryKey(Long memberAddId);

    int insert(TabPbMemberAddList record);

    int insertSelective(TabPbMemberAddList record);

    TabPbMemberAddList selectByPrimaryKey(Long memberAddId);

    int updateByPrimaryKeySelective(TabPbMemberAddList record);

    int updateByPrimaryKeyWithBLOBs(TabPbMemberAddList record);

    int updateByPrimaryKey(TabPbMemberAddList record);

    TabPbMemberAddList selectByUserId(Long userId);

    List<TabPbMemberAddList> selectListByUserId(Long userId);
}