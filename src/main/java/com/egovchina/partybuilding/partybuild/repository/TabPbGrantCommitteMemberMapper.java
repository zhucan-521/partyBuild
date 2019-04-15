package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbGrantCommitteMember;

import java.util.List;

public interface TabPbGrantCommitteMemberMapper {

    int insertSelective(TabPbGrantCommitteMember record);

    TabPbGrantCommitteMember selectByPrimaryKey(Long grantCommitteeMemberId);

    int updateByPrimaryKeySelective(TabPbGrantCommitteMember record);

    /**
     * 通过关联的主表id 和 userId 查询一条数据, 在数据一致的情况下, 该sql至多返回一条数据
     *
     * @param record
     * @return
     */
    TabPbGrantCommitteMember selectByCommitteeIdAndUserId(TabPbGrantCommitteMember record);

    /**
     * 通过主表的id更新数据
     *
     * @param member
     * @return
     */
    int updateByGrantCommitteeSelective(TabPbGrantCommitteMember member);

    /**
     * 通过关联的主表id查询
     *
     * @param member
     * @return
     */
    List<TabPbGrantCommitteMember> selectByGrantCommitteeId(TabPbGrantCommitteMember member);
}