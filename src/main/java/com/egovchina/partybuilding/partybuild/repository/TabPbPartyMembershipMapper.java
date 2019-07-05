package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMembership;
import com.egovchina.partybuilding.partybuild.vo.MembershipVO;
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
     * @param tabPbPartyMembership 党籍信息实体
     * @return
     */
    int insertPartyMembershipDTO(TabPbPartyMembership tabPbPartyMembership);

    /**
     * 根据用户id获取党籍列表
     *
     * @return
     */
    List<MembershipVO> getMembershipVOListByCondition(Long userId);

}
