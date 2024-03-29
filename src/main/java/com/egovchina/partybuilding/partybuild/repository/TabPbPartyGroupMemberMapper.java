package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbPartyGroupMember;
import com.egovchina.partybuilding.partybuild.vo.PartyGroupMemberVO;
import com.egovchina.partybuilding.partybuild.vo.PartyMemberBaseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbPartyGroupMemberMapper {

    int deleteByPrimaryKey(Long memberId);

    int insert(TabPbPartyGroupMember record);

    int insertSelective(TabPbPartyGroupMember record);

    TabPbPartyGroupMember selectByPrimaryKey(Long memberId);

    int updateByPrimaryKeySelective(TabPbPartyGroupMember record);

    int updateByPrimaryKey(TabPbPartyGroupMember record);

    /**
     * desc: 根据党小组ID获取该小组下所有成员详细信息
     *
     * @param groupId 党小组ID
     * @return List<PartyGroupMemberVO>
     * @author FanYanGen
     * @date 2019/4/29 16:03
     **/
    List<PartyGroupMemberVO> selectPartyGroupMembersDetailsInfoByGroupId(Long groupId);

    /**
     * desc: 根据党小组ID批量删除成员
     *
     * @param groupId 党组ID
     * @return int
     * @author FanYanGen
     * @date 2019/5/6 9:23
     **/
    int batchDeleteByGroupId(@Param(value = "groupId") Long groupId);

    /**
     * desc: 批量新增成员到党小组
     *
     * @param partyGroupMembers 党小组成员信息集合
     * @return int
     * @author FanYanGen
     * @date 2019/5/5 15:03
     **/
    int batchInsert(List<TabPbPartyGroupMember> partyGroupMembers);

    /**
     * desc: 筛选指定组织中未在任何党小组存在的党员
     *
     * @param orgId   组织ID
     * @param groupId 党小组ID
     * @return PartyMemberBaseVO
     * @author FanYanGen
     * @date 2019/5/6 15:57
     **/
    List<PartyMemberBaseVO> screenPartyGroupMembers(@Param("orgId") Long orgId, @Param("groupId") Long groupId);

    /**
     * desc: 查询党小组总人数
     *
     * @param groupId 党组ID
     * @return int
     * @auther FanYanGen
     * @date 2019-05-29 23:11
     */
    Long totalMembers(Long groupId);

    /**
     * desc: 根据用户Id查询是否是党小组组长
     *
     * @param userId 用户ID
     * @return true or false
     * @auther FanYanGen
     * @date 2019-06-13 09:26
     */
    Boolean isLeaderByUserId(Long userId);

}