package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMembership;
import com.egovchina.partybuilding.partybuild.v1.entity.MembershipQueryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/16 16:42
 * @description 党员党籍mapper
 */

@Repository
public interface TabPbPartyMembershipMapper {

    /**
     * 插入党籍信息
     *
     * @param tabPbPartyMembership
     * @return
     */
    int insertPartyMembership(TabPbPartyMembership tabPbPartyMembership);

    /**
     * 根据人员类别和党籍处理获取党籍信息列表
     *
     * @param membershipQueryBean
     * @return
     */
    List<TabPbPartyMembership> getTabPbMembershipByIdentityTypeAndTypeList(MembershipQueryBean membershipQueryBean);

    /**
     * 修改党籍信息
     *
     * @param tabPbPartyMembership
     * @return
     */
    int updatePartyMembership(TabPbPartyMembership tabPbPartyMembership);

    /**
     * 根据用户id获取用户党籍信息
     *
     * @param userId
     * @return
     */
    TabPbPartyMembership getTabPbPartyMembershipByUserId(Long userId);

    /**
     * 获取党籍列表
     *
     * @return
     */
    List<TabPbPartyMembership> getTabPbMembershipList();
}
