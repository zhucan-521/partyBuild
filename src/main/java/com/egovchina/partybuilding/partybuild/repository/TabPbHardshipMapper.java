package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.HardshipQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbHardship;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by FanYanGen on 2019/4/22 17:26
 */
@Repository
public interface TabPbHardshipMapper {

    int deleteByPrimaryKey(Long hardshipId);

    int insert(TabPbHardship record);

    int insertSelective(TabPbHardship record);

    TabPbHardship selectByPrimaryKey(Long hardshipId);

    int updateByPrimaryKeySelective(TabPbHardship record);

    int updateByPrimaryKeyWithBLOBs(TabPbHardship record);

    int updateByPrimaryKey(TabPbHardship record);

    List<HardshipPartyVO> selectWithConditions(HardshipQueryBean hardshipQueryBean);

    int updateUserNameById(TabPbHardship userId);

    HardshipPartyVO findByHardshipId(Long hardshipId);

    int deleteByHardshipId(Long hardshipId);

    HardshipPartyVO findByUserId(Long userId);

    int logicDelete(TabPbHardship record);

    int logicDeleteByUserId(TabPbHardship tabPbHardship);

    /**
     * 根据用户Id查询用户慰问情况
     * @param userId
     * @return
     */
    List<HardshipPartyVO> findHardshipPartyVOByUserId(Long userId);

}
