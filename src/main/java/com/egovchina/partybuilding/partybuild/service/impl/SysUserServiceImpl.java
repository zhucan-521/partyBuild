package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.partybuild.dto.TransferUserDeptInfo;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/22 21:43
 * @description
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private TabSysUserMapper sysUserMapper;

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

}
