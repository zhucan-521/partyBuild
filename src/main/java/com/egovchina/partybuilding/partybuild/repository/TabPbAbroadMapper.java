package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.HistoricalPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbAbroad;
import com.egovchina.partybuilding.partybuild.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by FanYanGen on 2019/4/20 15:56
 */
@Repository
public interface TabPbAbroadMapper {

    int insertSelective(TabPbAbroad record);

    TabPbAbroad selectByPrimaryKey(Long abroadId);

    int updateByPrimaryKeySelective(TabPbAbroad record);

    List<AbroadVO> selectByConditions(TabPbAbroad record);

    AbroadDetailsVO findAbroadDetailsVOByAbroadId(Long abroadId);

    List<MemberReducesVO> findAbroadDetailsByPartyId(HistoricalPartyMemberQueryBean historicalPartyMemberQueryBean);

}