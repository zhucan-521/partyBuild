package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TransferUserDeptInfo;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.entity.TabPbMemberReduceList;
import com.egovchina.partybuilding.partybuild.repository.TabPbMemberAddListMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbMemberReduceListMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.OrganizationService;
import com.egovchina.partybuilding.partybuild.service.SysUserService;
import com.egovchina.partybuilding.partybuild.vo.SysUserVO;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/22 21:43
 * @description
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private TabSysUserMapper sysUserMapper;

    @Autowired
    private TabPbMemberReduceListMapper memberReduceListMapper;

    @Autowired
    private TabPbMemberAddListMapper memberAddListMapper;

    @Autowired
    private OrganizationService organizationService;

    /**
     * 根据组织Id 获取党务工作者信息
     *
     * @param deptId
     * @return
     */
    @Override
    public TransferUserDeptInfo getDWRoleUserInfoByDeptId(Long deptId) {
        return sysUserMapper.getDWRoleUserInfoByDeptId(deptId);
    }

    /**
     * 根据多个组织id获取，多个党务工作者
     *
     * @param manageDeptIds
     * @return
     */
    @Override
    public List<TransferUserDeptInfo> getDWRoleUserInfoByDeptIds(List<String> manageDeptIds) {
        return sysUserMapper.getDWRoleUserInfoByDeptIds(manageDeptIds);
    }

    /**
     * 组织关系接转-修改用户的deptId
     *
     * @param userId
     * @param deptId
     */
    @Override
    public void updateDeptIdByUserId(Integer userId, Integer deptId) {
        sysUserMapper.updateDeptIdByUserId(userId, deptId);
    }

    /**
     * 获取党员党籍相关信息
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser getRegistryId(Long userId) {
        if (userId == null) {
            return null;
        }
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        SysDept dept = organizationService.selectByPrimaryKey(user.getJoinOrgId());
        TabPbMemberReduceList reduce = memberReduceListMapper.selectByUserId(userId);
//        if (reduce != null) {
//            user.setOutType(reduce.getOutType());
//            user.setReduceTime(reduce.getReduceTime());
//        }
//        TabPbMemberAddList add = memberAddListMapper.selectByUserId(userId);
//        if (add != null) {
//            user.setAddTime(add.getAddTime());
//        }
        if (dept != null) {
            user.setJoinOrgName(dept.getName());
        }
        setRegularTime(user);
        return user;
    }

    /**
     * 获取用户党籍详细信息
     *
     * @param userId 用户id
     * @return
     */
    @Transactional
    @Override
    public SysUser getRegistryByUserId(Long userId) {
        //获取用户信息
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        //获取用户所在的部门信息
        SysDept dept = organizationService.selectByPrimaryKey(user.getJoinOrgId());
        TabPbMemberReduceList reduce = memberReduceListMapper.selectByUserId(userId);
//        if (reduce != null) {
//            user.setOutType(reduce.getOutType());
//            user.setReduceTime(reduce.getReduceTime());
//        }
//        TabPbMemberAddList add = memberAddListMapper.selectByUserId(userId);
//        if (add != null) {
//            user.setAddTime(add.getAddTime());
//        }
        if (dept != null) {
            user.setJoinOrgName(dept.getName());
        }
        setRegularTime(user);
        return user;
    }

    private void setRegularTime(SysUser sysUser) {
        if (sysUser.getRegularTime() != null) {
            Calendar calBegin = Calendar.getInstance();
            Calendar calEnd = Calendar.getInstance();
            calBegin.setTime(sysUser.getRegularTime());
            calEnd.setTime(new Date());
            sysUser.setPartyStanding(calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR) + 1);
        }
    }

    /**
     * 社区活动选人接口(通过组织主键来获取)
     *
     * @param user
     * @return
     */
    @Override
    public List<SysUser> selectAllRegister(SysUser user) {
        if (user.getReportOrgId() != null && !"".equals(user.getReportOrgId())) {
            var list = sysUserMapper.selectAllRegister(user);
            return list;
        } else {
            user.setDeptId(UserContextHolder.currentUser().getDeptId().longValue());
            var list = sysUserMapper.selectAllRegister(user);
            return list;
        }
    }
}
