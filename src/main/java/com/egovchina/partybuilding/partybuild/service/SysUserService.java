package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.TransferUserDeptInfo;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/22 21:42
 * @description
 */
public interface SysUserService {

    /**
     * 根据多个组织id获取，多个党务工作者
     *
     * @param manageDeptIds
     * @return
     */
    List<TransferUserDeptInfo> getDWRoleUserInfoByDeptIds(List<String> manageDeptIds);

    /**
     * 组织关系接转-修改用户的deptId
     */
    void updateDeptIdByUserId(Integer userId, Integer deptId);

}
