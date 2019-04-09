package com.yizheng.partybuilding.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yizheng.commons.domain.UserInfo;
import com.yizheng.partybuilding.system.dto.UserAdminDTO;
import com.yizheng.partybuilding.system.dto.UserDTO;
import com.yizheng.partybuilding.system.entity.SysAccount;
import com.yizheng.partybuilding.system.vo.UserVO;

import java.util.List;

public interface SysAccountService extends IService<SysAccount> {

    /**
     * 查询用户信息
     *
     * @param type     类型
     * @param idCardNo 身份证
     * @return userInfo
     */
    UserInfo findUserInfo(String type, String idCardNo);

    /**
     * 查询用户列表
     * @return
     */
    List<SysAccount> selectList();

    /**
     * 翻页用户列表 - ROLE_ADMIN角色查看所有用户，其他角色查看自己创建的用户
     * @return
     */
    List<UserAdminDTO> listAll(UserAdminDTO userDto);

    /**
     * 删除用户
     * @param sysAccount 用户
     * @return boolean
     */
    Boolean deleteUserById(SysAccount sysAccount);

    /**
     * 更新指定用户信息
     * @param userDto  用户信息
     * @return
     */
    int updateUser(UserDTO userDto);

    /**
     * 通过ID查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO selectUserVoById(Integer id);

    /**
     * 通过用户名查找已经删除的用户
     * @param idCardNo 身份证
     * @return
     */
    SysAccount selectDeletedUserByCardNo(String idCardNo);

    /**
     * 根据用户名删除用户（真实删除）
     *
     * @param idCardNo idCardNo
     * @param userId   userId
     * @return
     */
    Boolean deleteSysUserByCardNoAndUserId(String idCardNo, Integer userId);

    /**
     * 保存验证码
     *  @param randomStr 随机串
     * @param imageCode 验证码*/
    void saveImageCode(String randomStr, String imageCode);

    /**
     * 修改密码根据id
     * @param sysAccount
     * @return
     */
    Boolean updatePwdById(SysAccount sysAccount);

    /**
     * 新增账户及权限
     * @param sysAccount 系统账户实体
     * @param roleIds 权限ids
     * @return
     */
    int insertAccountWithRole(SysAccount sysAccount, List<Integer> roleIds);
}
