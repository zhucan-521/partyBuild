package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.partybuild.entity.RewardsAndPunishmentsQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPunishment;
import com.egovchina.partybuilding.partybuild.vo.PunishmentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPunishmentMapper {
    int deleteByPrimaryKey(Long punishmentId);

    int insert(TabPbPunishment record);

    int insertSelective(TabPbPunishment record);

    TabPbPunishment selectByPrimaryKey(Long punishmentId);

    int updateByPrimaryKeySelective(TabPbPunishment record);

    int updateByPrimaryKeyWithBLOBs(TabPbPunishment record);

    int updateByPrimaryKey(TabPbPunishment record);

    List<TabPbPunishment> selectList(TabPbPunishment punishment);

    PunishmentVO selectByPrimaryKeyAndFiles(Long punishmentId);

    List<PunishmentVO> selectListAndFileVO(RewardsAndPunishmentsQueryBean rewardsAndPunishmentsQueryBean);
}