package com.egovchina.partybuilding.partybuild.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.partybuild.system.dto.RoleDTO;
import com.egovchina.partybuilding.partybuild.system.entity.SysRole;
import com.egovchina.partybuilding.partybuild.system.util.Query;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

	/**
	 * 添加角色
	 *
	 * @param roleDto 角色信息
	 * @return 成功、失败
	 */
	Boolean insertRole(RoleDTO roleDto);

	/**
	 * 分页查角色列表
	 *
	 * @param objectQuery         查询条件
	 * @param objectEntityWrapper wapper
	 * @return page
	 */
	Page selectwithDeptPage(Query<Object> objectQuery, EntityWrapper<Object> objectEntityWrapper);

	/**
	 * 分页查角色列表
	 * @param createUserid
	 * @param roleName
	 * @param page
	 * @return
	 */
	PageInfo<Object> getRolePageList(Long createUserid , String roleName, com.egovchina.partybuilding.common.entity.Page page);


	/**
	 * 更新角色
	 *
	 * @param roleDto 含有组织信息
	 * @return 成功、失败
	 */
	Boolean updateRoleById(RoleDTO roleDto);

	/**
	 * 通过组织ID查询角色列表
	 *
	 * @param deptId 组织ID
	 * @return 角色列表
	 */
	List<SysRole> selectListByDeptId(Integer deptId);

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> findRolesByUserId(Integer userId);
}
