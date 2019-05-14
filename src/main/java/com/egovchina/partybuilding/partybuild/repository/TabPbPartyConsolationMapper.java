package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyConsolation;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyConsolationExample;
import com.egovchina.partybuilding.partybuild.vo.PartyConsolationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TabPbPartyConsolationMapper {
    int countByExample(TabPbPartyConsolationExample example);

    int deleteByExample(TabPbPartyConsolationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TabPbPartyConsolation record);

    int insertSelective(TabPbPartyConsolation record);

    List<PartyConsolationVO> selectByExample(TabPbPartyConsolationExample example);

    TabPbPartyConsolation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TabPbPartyConsolation record, @Param("example") TabPbPartyConsolationExample example);

    int updateByExample(@Param("record") TabPbPartyConsolation record, @Param("example") TabPbPartyConsolationExample example);

    int updateByPrimaryKeySelective(TabPbPartyConsolation record);

    int updateByPrimaryKey(TabPbPartyConsolation record);

    List<PartyConsolationVO> selectPartyConsolationVOByUserId(Long userId);
}