package com.yizheng.partybuilding.service.inf;

import com.yizheng.partybuilding.dto.RegistryDto;
import com.yizheng.partybuilding.dto.TransferUserDeptInfo;
import com.yizheng.partybuilding.system.entity.SysUser;

import java.util.List;

/**
 * 党员--用户信息接口
 */
public interface ITabSysUserService {

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

    boolean updataUser(SysUser sysUser);

    List<RegistryDto> getRegistryList(Long userId);

    /**
     * 社区活动选人接口(通过组织主键来获取)
     * @param user
     * @return
     */
    List<SysUser> selectAllRegister(SysUser user);
}
