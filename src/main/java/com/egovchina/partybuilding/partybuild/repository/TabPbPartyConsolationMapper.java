package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyConsolation;
import com.egovchina.partybuilding.partybuild.vo.PartyConsolationVO;

import java.util.List;

public interface TabPbPartyConsolationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TabPbPartyConsolation record);

    int insertSelective(TabPbPartyConsolation record);

    TabPbPartyConsolation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TabPbPartyConsolation record);

    int updateByPrimaryKey(TabPbPartyConsolation record);

    List<PartyConsolationVO> selectPartyConsolationVOByUserId(Long userId);

    PartyConsolationVO selectPartyConsolationVOById(Long id);
}