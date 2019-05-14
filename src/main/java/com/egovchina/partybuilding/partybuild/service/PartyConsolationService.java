package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.PartyConsolationDTO;
import com.egovchina.partybuilding.partybuild.vo.PartyConsolationVO;

import java.util.List;

/**
 * @author zhucan
 */
public interface PartyConsolationService {

    /**
     * 添加慰问情况
     * @param partyConsolationDTO
     * @return
     */
    int addPartyConsolationDTO(PartyConsolationDTO partyConsolationDTO);

    /**
     * 根据用户获取慰问情况
     * @param userId
     * @return
     */
    List<PartyConsolationVO> getPartyConsolationVO(Long userId);

}
