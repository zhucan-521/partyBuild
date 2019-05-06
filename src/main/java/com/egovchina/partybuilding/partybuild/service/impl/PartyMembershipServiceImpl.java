package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMembership;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMembershipMapper;
import com.egovchina.partybuilding.partybuild.service.PartyMembershipService;
import com.egovchina.partybuilding.partybuild.vo.MembershipVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @author create by GuanYingxin on 2019/4/18 13:36
 * @description 党籍Service实现类
 */
@Service
public class PartyMembershipServiceImpl implements PartyMembershipService {

    @Autowired
    private TabPbPartyMembershipMapper tabPbPartyMembershipMapper;

    @Transactional
    @Override
    public int insertMembershipDTO(MembershipDTO membershipDTO) {
        TabPbPartyMembership tabPbPartyMembership = generateTargetCopyPropertiesAndPaddingBaseField(membershipDTO, TabPbPartyMembership.class, false);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbPartyMembership);
        return tabPbPartyMembershipMapper.insertPartyMembershipDTO(tabPbPartyMembership);
    }

    /**
     * 查询党籍信息列表实现方法
     *
     * @param page
     * @return
     */
    @Override
    public List<MembershipVO> getMembershipVOListByCondition(Page page) {
        PageHelper.startPage(page);
        List<MembershipVO> list = tabPbPartyMembershipMapper.getMembershipVOListByCondition();
        return list;
    }
}
