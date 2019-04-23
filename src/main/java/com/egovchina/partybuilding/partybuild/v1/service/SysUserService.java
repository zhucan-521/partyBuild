package com.egovchina.partybuilding.partybuild.v1.service;

import com.egovchina.partybuilding.partybuild.dto.RegistryDto;
import com.egovchina.partybuilding.partybuild.dto.TransferUserDeptInfo;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.v1.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.v1.dto.RegistryDTO;

import java.util.List;

/**
 * @author create by GuanYingxin on 2019/4/22 21:42
 * @description
 */
public interface SysUserService {

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
     * 获取党籍列表
     *
     * @param userId
     * @return
     */
    SysUser getRegistryByUserId(Long userId);

    /**
     * 更新用户党籍信息
     *
     * @param sysUser
     * @return
     */
    boolean updateUser(SysUser sysUser);

    /**
     * 更新用户党籍信息
     *
     * @param membershipDTO
     * @return
     */
    boolean updateMembership(MembershipDTO membershipDTO);

    List<RegistryDto> getRegistryList(Long userId);

    /**
     * 获取用户党籍信息
     *
     * @param userId
     * @return
     */
    List<RegistryDTO> getRegistryV1List(Long userId);

    /**
     * 社区活动选人接口(通过组织主键来获取)
     *
     * @param user
     * @return
     */
    List<SysUser> selectAllRegister(SysUser user);
}