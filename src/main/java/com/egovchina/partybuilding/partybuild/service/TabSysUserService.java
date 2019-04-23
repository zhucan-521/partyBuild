package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.RegistryDto;
import com.egovchina.partybuilding.partybuild.dto.TransferUserDeptInfo;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.v1.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.v1.dto.RegistryDTO;

import java.util.List;

/**
 * 党员--用户信息接口
 */
public interface TabSysUserService {

    /**
     * 根据组织Id 获取党务工作者信息
     *
     * @param deptId
     * @return
     */
    TransferUserDeptInfo getDWRoleUserInfoByDeptId(Long deptId);

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

    SysUser getRegistryId(Long userId);


    /**
     * 更新用户党籍信息
     *
     * @param sysUser
     * @return
     */
    boolean updateUser(SysUser sysUser);

    List<RegistryDto> getRegistryList(Long userId);

    /**
     * 社区活动选人接口(通过组织主键来获取)
     *
     * @param user
     * @return
     */
    List<SysUser> selectAllRegister(SysUser user);
}
