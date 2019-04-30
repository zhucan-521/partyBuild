package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMembership;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMembershipMapper;
import com.egovchina.partybuilding.partybuild.service.PartyMembershipService;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.egovchina.partybuilding.partybuild.vo.MembershipVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/18 13:36
 * @description 党籍Service实现类
 */
@Service
public class PartyMembershipServiceImpl implements PartyMembershipService {

    @Autowired
    private TabPbPartyMembershipMapper tabPbPartyMembershipMapper;

    @Transactional
    @PaddingBaseField
    @Override
    public int insertMembershipDTO(MembershipDTO membershipDTO) {
        TabPbPartyMembership tabPbPartyMembership = new TabPbPartyMembership();
        tabPbPartyMembership.setUserId(membershipDTO.getUserId());
        tabPbPartyMembership.setType(membershipDTO.getType());
        tabPbPartyMembership.setIdentityType(membershipDTO.getIdentityType());
        tabPbPartyMembership.setReason(membershipDTO.getReason());
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

    /**
     * 党籍添加公共方法
     *
     * @param sysUser
     * @return
     */
    @Override
    public TabPbPartyMembership toolMethod(SysUser sysUser) {
        TabPbPartyMembership tabPbPartyMembership = new TabPbPartyMembership();
        tabPbPartyMembership.setUserId(sysUser.getUserId().longValue());
        tabPbPartyMembership.setIdentityType(sysUser.getIdentityType());
        tabPbPartyMembership.setType(sysUser.getRegistryStatus());
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbPartyMembership);
        return tabPbPartyMembership;
    }
}
