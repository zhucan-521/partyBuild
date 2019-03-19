package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbGrantCommitteMember;
import com.yizheng.partybuilding.entity.TabPbGrantCommittee;

import com.yizheng.partybuilding.repository.TabPbGrantCommitteMemberMapper;
import com.yizheng.partybuilding.repository.TabPbGrantCommitteeMapper;
import com.yizheng.partybuilding.repository.TabSysDeptMapper;
import com.yizheng.partybuilding.service.inf.ITabPbGrantCommitteeService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/04
 */
@Service
public class TabPbGrantCommitteeService implements ITabPbGrantCommitteeService {


    @Autowired
    private TabPbGrantCommitteMemberMapper detailDao;

    @Autowired
    private TabPbGrantCommitteeMapper mainDao;

    @Autowired
    private TabSysDeptMapper deptMapper;

    @Override
    @Transactional
    public int committeeSave(TabPbGrantCommittee newData) {
        newData.setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong());

        if (newData.getOrgId() == null) {
            throw new BusinessDataIncompleteException("orgId不可为null");
        }

        if (deptMapper.selectByPrimaryKey(newData.getOrgId()) == null) {
            throw new BusinessDataIncompleteException("orgId不存在");
        }

        var old = this.mainDao.selectByOrgId(newData.getOrgId());
        if (old == null) {
            newData.setCurrent((byte) 1);
            return this.mainDao.insertSelectiveAndReturnPrimaryKey(newData);
        } else {
            newData.setGrantCommitteeId(old.getGrantCommitteeId());
            return this.mainDao.updateByPrimaryKeySelective(newData);
        }
    }

    /**
     * 保存大公委及大公委成员数据
     *  @param committee
     * @param members
     */
    @Override
    @Transactional
    public int committeeSave(TabPbGrantCommittee committee, List<TabPbGrantCommitteMember> members) {
        this.committeeSave(committee);
        members.forEach(v -> v.setGrantCommitteeId(committee.getGrantCommitteeId()));
        return this.memberSaveList(members);
    }

    @Override
    public int committeeDelete(Long id) {
        var main = new TabPbGrantCommittee()
                .setGrantCommitteeId(id)
                .setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong())
                .setDelFlag("1");
        this.mainDao.updateByPrimaryKeySelective(main);

        // 删除关联表
        var detail = new TabPbGrantCommitteMember()
                .setGrantCommitteeId(id)
                .setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong())
                .setDelFlag("1");
        return this.detailDao.updateByGrantCommitteeSelective(detail);
    }

    /**
     * 通过id查询街道大公委数据
     *
     * @param id
     * @return
     */
    @Override
    public TabPbGrantCommittee committeeFindById(Long id) {
        return this.mainDao.selectByPrimaryKey(id);
    }

    /**
     * 分页查询街道大公委数据
     *
     * @param orgId        组织id
     * @param name         街道大公委成员
     * @param positiveName 职务名称
     * @param page         分页
     * @return 街道大公委数据
     */
    @Override
    public PageInfo<TabPbGrantCommittee> committeeFind(Long orgId, Long orgRange, String name, String positiveName, Page page) {
        PageHelper.startPage(page);
        List<TabPbGrantCommittee> list = this.mainDao.select(orgId, orgRange, name, positiveName);
        var temp = new PageInfo<TabPbGrantCommittee>(list);
        return temp;
    }


    @Override
    public int memberSave(TabPbGrantCommitteMember member) {
        if (member.getGrantCommitteeId() == null) {
            throw new BusinessDataIncompleteException("grantCommitteeId 不存在");
        }
        return this.detailDao.insertSelective(member);
    }

    @Override
    public TabPbGrantCommitteMember memberFindById(Long grantCommitteeMemberId) {
        return this.detailDao.selectByPrimaryKey(grantCommitteeMemberId);
    }

    @Override
    public int memberDelete(Long grantCommitteeMemberId) {
        var temp = new TabPbGrantCommitteMember()
                .setGrantCommitteeMemberId(grantCommitteeMemberId)
                .setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong())
                .setDelFlag("1");
        return this.detailDao.updateByPrimaryKeySelective(temp);
    }

    @Override
    public PageInfo<TabPbGrantCommitteMember> memberFind(Long grantCommitteeId, String personName, String positiveName, Page page) {
        PageHelper.startPage(page);
        var temp = new TabPbGrantCommitteMember()
                .setGrantCommitteeId(grantCommitteeId)
                .setPersonName(personName)
                .setPositiveName(positiveName);
        List<TabPbGrantCommitteMember> list = this.detailDao.selectByGrantCommitteeId(temp);
        return new PageInfo<>(list);
    }

    @Override
    public int memberSaveList(List<TabPbGrantCommitteMember> list) {
        list.stream()
                .filter(v -> v.getGrantCommitteeId() != null)
                .collect(Collectors.toList())
                .forEach(v -> this.detailDao.insertSelective(v));
        return 1;
    }

}
