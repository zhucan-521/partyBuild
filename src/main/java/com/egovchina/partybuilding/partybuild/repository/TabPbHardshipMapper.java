package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.HardshipQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbHardship;
import com.egovchina.partybuilding.partybuild.vo.HardshipPartyVO;
import com.egovchina.partybuilding.partybuild.vo.PartyConsolationVO;
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

    int updateUserNameById(TabPbHardship userId);

    int deleteByHardshipId(Long hardshipId);

    int logicDelete(TabPbHardship record);

    int logicDeleteByUserId(TabPbHardship tabPbHardship);

    /**
     * 根据用户Id查询用户慰问情况
     *
     * @param userId
     * @return
     */
    List<PartyConsolationVO> selectPartyConsolationVOByUserId(Long userId);

    /**
     * desc: 查询单条困难记录
     *
     * @param hardshipId 主键id
     * @return HardshipPartyVO
     * @auther FanYanGen
     * @date 2019-05-14 14:32
     */
    HardshipPartyVO findByHardshipId(Long hardshipId);

    /**
     * desc: 根据用户id查询党员的困难记录
     *
     * @param userId 用户id
     * @return List<HardshipPartyVO>
     * @auther FanYanGen
     * @date 2019-05-14 14:30
     */
    List<HardshipPartyVO> findByUserId(Long userId);

    /**
     * desc: 根据条件查询困难记录列表
     *
     * @param hardshipQueryBean querybean
     * @return List<HardshipPartyVO>
     * @auther FanYanGen
     * @date 2019-05-14 14:34
     */
    List<HardshipPartyVO> selectWithConditions(HardshipQueryBean hardshipQueryBean);

    /**
     *根据主键查询表里是否含有困难党员
     *
     * @param hardShipId 困难党员主键
     * @return
     */
    Integer checkHardshipPartyByHardShipId(Long hardShipId);
}
