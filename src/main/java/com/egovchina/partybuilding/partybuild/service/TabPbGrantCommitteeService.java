package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbGrantCommitteMember;
import com.egovchina.partybuilding.partybuild.entity.TabPbGrantCommittee;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/04
 */
public interface TabPbGrantCommitteeService {

    /**
     * 保存或更新街道大公委及数据
     * 如果主键在数据库中存在, 则为更新, 否则为保存
     *
     * @param committee
     * @return
     */
    int committeeSave(TabPbGrantCommittee committee);

    /**
     * 添加街道大公委及大公委成员数据
     *  @param committee
     * @param members
     */
    int committeeSave(TabPbGrantCommittee committee, List<TabPbGrantCommitteMember> members);

    /**
     * 通过街道大公委主键删除, 将同时删除用户
     *
     * @param id
     */
    int committeeDelete(Long id);

    /**
     * 通过主键id查询
     *
     * @param id
     * @return
     */
    TabPbGrantCommittee committeeFindById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    PageInfo<TabPbGrantCommittee> committeeFind(Long orgId,Long orgRange, String name, String positiveName, Page page);


    /**
     * 添加街道大公委成员数据, 其街道大公委id和userId必须存在
     *
     * @param member
     */
    int memberSave(TabPbGrantCommitteMember member);

    /**
     * 通过(街道大公委明细主键)获取数据, 即街道大公委明细表id
     *
     * @param grantCommitteeMemberId
     * @return
     */
    TabPbGrantCommitteMember memberFindById(Long grantCommitteeMemberId);

    /**
     * 通过街道大公委明细表主键删除明细, 删除成员
     *
     * @param grantCommitteeMemberId
     */
    int memberDelete(Long grantCommitteeMemberId);

    /**
     * 通过街道大公委获取其成员列表
     *
     * @param grantCommitteeId
     * @param page
     * @return
     */
    PageInfo<TabPbGrantCommitteMember> memberFind(Long grantCommitteeId,String personName, String positiveName, Page page);

    /**
     * 批量保存大公委成员数据
     *
     * @param list
     * @return
     */
    int memberSaveList(List<TabPbGrantCommitteMember> list);
}
