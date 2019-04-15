package com.egovchina.partybuilding.partybuild.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.common.entity.UserInfo;
import com.egovchina.partybuilding.partybuild.system.dto.UserAdminDTO;
import com.egovchina.partybuilding.partybuild.system.dto.UserDTO;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.system.vo.UserVO;

import java.util.List;

public interface SysUserService extends IService<SysUser> {
	/**
	 * 查询用户信息
	 *
	 * @param type     类型
     * @param realname 用户名
	 * @return userInfo
	 */
    UserInfo findUserInfo(String type, String realname);

	/**
	 * 查询用户列表
	 *
	 * @return
	 */
	List<SysUser> selectList();


	/**
	 * 所有用户列表
	 * @return
	 */
    List<UserAdminDTO> listAll(UserAdminDTO userDto);

	/**
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return boolean
	 */
	Boolean deleteUserById(SysUser sysUser);

	/**
	 * 更新当前用户基本信息
	 *
	 * @param userDto  用户信息
	 * @return Boolean
	 */
    Boolean updateUserInfo(UserDTO userDto);

	/**
	 * 更新指定用户信息
	 *
	 * @param userDto  用户信息
	 * @return
	 */
    Boolean updateUser(UserDTO userDto);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	UserVO selectUserVoById(Integer id);

	/**
	 * 通过用户名查找已经删除的用户
	 *
     * @param idCardNo 身份证
	 * @return
	 */
    SysUser selectDeletedUserByCardNo(String idCardNo);

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
	 * 登录
     * @param idCardNo
	 * @param password
	 * @return
	 */
    String login(String idCardNo, String password);



	/**
	 * 修改密码根据id
	 * @param sysUser
	 * @return
	 */
    Boolean updatePwdById(SysUser sysUser);
}
