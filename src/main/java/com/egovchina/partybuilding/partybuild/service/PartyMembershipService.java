package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.vo.MembershipVO;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/18 13:31
 * @description 党籍service
 */
public interface PartyMembershipService {

    /**
     * 插入党籍信息
     *
     * @param membershipDTO dto
     * @return
     */
    int insertMembershipDTO(MembershipDTO membershipDTO);

    /**
     * 根据用户id获取党籍列表
     *
     * @param userId 用户id
     * @param page   分页对象
     * @return
     */
    List<MembershipVO> getMembershipVOListByCondition(Long userId, Page page);
}
