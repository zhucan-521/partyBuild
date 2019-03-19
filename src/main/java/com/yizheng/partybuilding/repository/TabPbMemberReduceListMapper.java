package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.entity.TabPbMemberReduceList;
import com.yizheng.partybuilding.system.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbMemberReduceListMapper {
    int deleteByPrimaryKey(Long memberReduceId);

    int insert(TabPbMemberReduceList record);

    int insertSelective(TabPbMemberReduceList record);

    TabPbMemberReduceList selectByPrimaryKey(Long memberReduceId);

    int updateByPrimaryKeySelective(TabPbMemberReduceList record);

    int updateByPrimaryKeyWithBLOBs(TabPbMemberReduceList record);

    int updateByPrimaryKey(TabPbMemberReduceList record);

    TabPbMemberReduceList selectByUserId(Long userId);

    List<TabPbMemberReduceList> selectListByUserId(Long userId);

    List<SysUser> historyPartyPage(TabPbMemberReduceList record);
}