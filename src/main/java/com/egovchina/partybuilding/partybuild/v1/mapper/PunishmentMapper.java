package com.egovchina.partybuilding.partybuild.v1.mapper;

import com.egovchina.partybuilding.partybuild.entity.TabPbPunishment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PunishmentMapper {
    int deleteByPrimaryKey(Long punishmentId);

    int insert(TabPbPunishment record);

    int insertSelective(TabPbPunishment record);

    TabPbPunishment selectByPrimaryKey(Long punishmentId);

    int updateByPrimaryKeySelective(TabPbPunishment record);

    int updateByPrimaryKeyWithBLOBs(TabPbPunishment record);

    int updateByPrimaryKey(TabPbPunishment record);

    List<TabPbPunishment> selectList(TabPbPunishment punishment);

}