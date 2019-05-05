package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbLinkLeader;
import com.egovchina.partybuilding.partybuild.vo.LinkLeaderVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbLinkLeaderMapper {
    int deleteByPrimaryKey(Long linkLedaerId);

    int insert(TabPbLinkLeader record);

    int insertSelective(TabPbLinkLeader record);

    TabPbLinkLeader selectByPrimaryKey(Long linkLedaerId);

    int updateByPrimaryKeySelective(TabPbLinkLeader record);

    int updateByPrimaryKeyWithBLOBs(TabPbLinkLeader record);

    int updateByPrimaryKey(TabPbLinkLeader record);

    List<TabPbLinkLeader> selectByUserIdAndDeptId(TabPbLinkLeader record);

    List<LinkLeaderVO> selectLinkLeaderVoByDeptId(Long detpId);
}