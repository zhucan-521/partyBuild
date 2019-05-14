package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyConsolationDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyConsolation;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyConsolationMapper;
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
    private TabPbPartyConsolationMapper tabPbPartyConsolationMapper;

    @Override
    public int addPartyConsolationDTO(PartyConsolationDTO partyConsolationDTO) {
        TabPbPartyConsolation tabPbPartyConsolation=  BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyConsolationDTO, TabPbPartyConsolation.class, false);
        return tabPbPartyConsolationMapper.insertSelective(tabPbPartyConsolation);
    }

    @Override
    public List<PartyConsolationVO> getPartyConsolationVO(Long userId) {
        return tabPbPartyConsolationMapper.selectPartyConsolationVOByUserId(userId);
    }

}
