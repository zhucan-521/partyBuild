package com.egovchina.partybuilding.partybuild.v1.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMembership;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMembershipMapper;
import com.egovchina.partybuilding.partybuild.v1.entity.MembershipQueryBean;
import com.egovchina.partybuilding.partybuild.v1.service.PartyMembershipService;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.v1.vo.MembershipVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/18 13:36
 * @description 党籍Service实现类
 */
@Service
public class PartyMembershipServiceImpl implements PartyMembershipService {

    @Autowired
    private TabPbPartyMembershipMapper tabPbPartyMembershipMapper;

    /**
     * 查询党籍信息列表实现方法
     *
     * @param membershipQueryBean
     * @param page
     * @return
     */
    @Override
    public List<MembershipVO> getMembershipVOListByCondition(MembershipQueryBean membershipQueryBean, Page page) {
        PageHelper.startPage(page);
        List<MembershipVO> list = tabPbPartyMembershipMapper.getMembershipVOListByCondition(membershipQueryBean);
        return list;
    }

    /**
     * 根据用户id查询党籍信息实现方法
     *
     * @param userId
     * @return
     */
    @Override
    public List<MembershipVO> getMembershipVOListByUserId(Long userId) {
        return tabPbPartyMembershipMapper.getMembershipVOByUserIdList(userId);
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

    /**
     * 获取党籍列表实现方法
     *
     * @param page
     * @return
     */
    @Override
    public List<MembershipVO> getMembershipVOList(Page page) {
        PageHelper.startPage(page);
        List<MembershipVO> list = tabPbPartyMembershipMapper.getMembershipVOList();
        return list;
    }
}
