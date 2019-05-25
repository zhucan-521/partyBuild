package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.partybuild.repository.TabPbHardshipMapper;
import com.egovchina.partybuilding.partybuild.service.PartyConsolationService;
import com.egovchina.partybuilding.partybuild.vo.PartyConsolationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhucan
 */
@Service
public class PartyConsolationImpl implements PartyConsolationService {

    @Autowired
    private TabPbHardshipMapper tabPbHardshipMapper;

    @Override
    public List<PartyConsolationVO> getPartyConsolationVO(Long userId) {
        return tabPbHardshipMapper.selectPartyConsolationVOByUserId(userId);
    }

}
