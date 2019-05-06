package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.partybuild.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMembership;
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
     * 获取党籍实体类列表
     *
     * @param page
     * @return
     */
    List<MembershipVO> getMembershipVOListByCondition(Page page);

}
