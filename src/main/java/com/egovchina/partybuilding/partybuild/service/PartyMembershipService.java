package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMembership;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.egovchina.partybuilding.partybuild.entity.MembershipQueryBean;
import com.egovchina.partybuilding.partybuild.vo.MembershipVO;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/18 13:31
 * @description 党籍service
 */
public interface PartyMembershipService {

    /**
     * 插入党籍信息
     * @param membershipDTO dto
     * @return
     */
    int insertMembershipDTO(MembershipDTO membershipDTO);

    /**
     * 根据人员类别和党籍类型查询党籍列表
     *
     * @param membershipQueryBean
     * @param page
     * @return
     */
    List<MembershipVO> getMembershipVOListByCondition(MembershipQueryBean membershipQueryBean, Page page);

    /**
     * 根据用户id获取用户的党籍信息
     *
     * @return
     */
    List<MembershipVO> getMembershipVOListByUserId(Long userId);

    /**
     * 公共方法
     *
     * @param sysUser
     * @return
     */
    TabPbPartyMembership toolMethod(SysUser sysUser);

    /**
     * 获取党籍列表
     *
     * @param page
     * @return
     */
    List<MembershipVO> getMembershipVOList(Page page);
}
