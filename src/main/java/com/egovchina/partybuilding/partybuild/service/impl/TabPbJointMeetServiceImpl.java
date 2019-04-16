package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TabPbJointMeetDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivities;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import com.egovchina.partybuilding.partybuild.repository.TabPbActivitiesMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbJointMeetMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbJointMeetOrgMapper;
import com.egovchina.partybuilding.partybuild.service.TabPbJointMeetService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/29
 */
@Service
public class TabPbJointMeetServiceImpl implements TabPbJointMeetService {

    public final static String DEL_FLAG = "1";

    @Autowired
    private TabPbJointMeetMapper dao;

    @Autowired
    private TabPbJointMeetOrgMapper orgDao;

    @Autowired
    private TabPbActivitiesMapper activitiesDao;

    /**
     * @param meet 联席会组织信息及成员信息, 如果没有可不传。 联席会组织成员列表为必传
     * @return
     */
    @Override
    @Transactional
    public TabPbJointMeet save(TabPbJointMeetDto meet) {
        if (isEmpty(meet) || isEmpty(meet.getMeetOrg())) {
            throw new BusinessDataIncompleteException("联席会成员不可为null");
        }

        if (isEmpty(meet.getOrgId())) {
            meet.setOrgId(UserContextHolder.currentUser().getDeptId().longValue());
        }

        // 默认是当前时间
        meet.setFoundedDate(isEmpty(meet.getFoundedDate()) ? new Date() : meet.getFoundedDate());

        var org = this.dao.selectByOrgId(meet.getOrgId());
        if (isEmpty(org)) {
            this.dao.insertSelective(meet);
        } else {
            meet.setJointMeetId(org.getJointMeetId());
        }
        meet.getMeetOrg().forEach(v -> {
            v.setJointMeetId(meet.getJointMeetId());
            v.setJoinDate(isEmpty(v.getJoinDate()) ? new Date() : v.getJoinDate());
            Long check=this.orgDao.selectCheck(meet.getOrgId(),v.getOrgId());
            if (check==0L) {
                this.orgDao.insertSelective(v);
            } else {
            }
        });
        return new TabPbJointMeet().setJointMeetId(meet.getJointMeetId());
    }

    /**
     * @param list 党建联席会主键id, 成员列表
     * @return
     */
    @Override
    @Transactional
    public ReturnEntity save(List<TabPbJointMeetOrg> list) {
        list.forEach(v -> {
            if (isEmpty(v.getJointMeetId())) {
                throw new BusinessDataIncompleteException("jointMeetId不可为null");
            }
        });
        list.forEach(v -> this.orgDao.insertSelective(v));
        return ReturnUtil.success();
    }

    /**
     * @param meet 主键
     * @return
     */
    @Override
    @Transactional
    public void delete(TabPbJointMeet meet) {
        if (isEmpty(meet.getJointMeetId())) {
            throw new BusinessDataIncompleteException("jointMeetId不可为null");
        }
        this.dao.updateByPrimaryKeySelective(meet.setDelFlag(DEL_FLAG));
        var tmp = new TabPbJointMeetOrg()
                .setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong())
                .setJointMeetId(meet.getJointMeetId());
        this.orgDao.select(tmp).forEach(this::delete);
    }

    /**
     * 根据主键删除
     *
     * @param org
     */
    @Override
    @Transactional
    public void delete(TabPbJointMeetOrg org) {
        if (isEmpty(org.getMemberOrgId())) {
            throw new BusinessDataIncompleteException("memberOrgId不可为null");
        }
        var tmp = this.orgDao.selectByPrimaryKey(org.getMemberOrgId());
        if (isEmpty(tmp)) {
            throw new BusinessDataNotFoundException("成员不存在");
        }
        var activity = new TabPbActivities()
                .setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong())
                .setDelFlag(DEL_FLAG)
                .setOrgId(tmp.getOrgId())
                .setActivitiesType(AttachmentType.JOINT_MEET);
        this.activitiesDao.deleteByOrgIdAndActivityType(activity);
        this.orgDao.updateByPrimaryKeySelective(org.setDelFlag(DEL_FLAG));
    }

    @Override
    @Transactional
    public int update(TabPbJointMeet meet) {
        if (isEmpty(meet.getJointMeetId())) {
            throw new BusinessDataIncompleteException("jointMeetId不可为null");
        }
        meet.setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong());
        return this.dao.updateByPrimaryKeySelective(meet);
    }

    @Override
    @Transactional
    public int update(TabPbJointMeetOrg org) {
        if (isEmpty(org.getMemberOrgId())) {
            throw new BusinessDataIncompleteException("memberOrg不可为null");
        }
        org.setUpdateTime(new Date())
                .setUpdateUsername(UserContextHolder.getUserName())
                .setUpdateUserid(UserContextHolder.getUserIdLong());
        return this.orgDao.updateByPrimaryKeySelective(org);
    }

    @Override
    public PageInfo<TabPbJointMeet> select(TabPbJointMeet meet, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(this.dao.select(meet));
    }

    @Override
    public PageInfo<TabPbJointMeetOrg> select(TabPbJointMeetOrg org, Page page) {
        if (isEmpty(org.getOrgId())) {
            throw new BusinessDataIncompleteException("orgId 不可为null");
        }
        PageHelper.startPage(page);
        return new PageInfo<>(this.orgDao.select(org));
    }
}
