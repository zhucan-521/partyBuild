package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.vo.PartyConsolationVO;

import java.util.List;

/**
 * @author zhucan
 */
public interface PartyConsolationService {

    /**
     * 根据用户获取慰问情况
     *
     * @param userId
     * @return
     */
    List<PartyConsolationVO> getPartyConsolationVO(Long userId);

}
