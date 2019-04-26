package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbAbroad;
import com.egovchina.partybuilding.partybuild.vo.AbroadDetailsVO;
import com.egovchina.partybuilding.partybuild.vo.AbroadVO;
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

    AbroadDetailsVO findByAbroadId(Long abroadId);

}