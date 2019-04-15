package com.egovchina.partybuilding.partybuild.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.partybuild.system.dto.RoleDTO;
import com.egovchina.partybuilding.partybuild.system.entity.SysRole;
import com.egovchina.partybuilding.partybuild.system.entity.SysRoleDept;
import com.egovchina.partybuilding.partybuild.system.mapper.SysRoleDeptMapper;
import com.egovchina.partybuilding.partybuild.system.mapper.SysRoleMapper;
import com.egovchina.partybuilding.partybuild.system.service.SysRoleService;
import com.egovchina.partybuilding.partybuild.system.util.Query;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
	@Autowired
	private SysRoleDeptMapper sysRoleDeptMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;

	/**
	 * 添加角色
	 *
	 * @param roleDto 角色信息
	 * @return 成功、失败
	 */
	@Override
	public Boolean insertRole(RoleDTO roleDto) {
		SysRole oldRole = sysRoleMapper.selectByRoleCode(roleDto.getRoleCode());
		if (oldRole != null) {
			throw new BusinessDataInvalidException("角色标识已存在");
		}
		SysRole sysRole = new SysRole();
		BeanUtils.copyProperties(roleDto, sysRole);
		sysRoleMapper.insert(sysRole);
		SysRoleDept roleDept = new SysRoleDept();
		roleDept.setRoleId(sysRole.getRoleId());
		roleDept.setDeptId(roleDto.getDeptId());
		sysRoleDeptMapper.insert(roleDept);
		return true;
	}

	/**
	 * 分页查角色列表
	 *
	 * @param query   查询条件
	 * @param wrapper wapper
	 * @return page
	 */
	@Override
	public Page selectwithDeptPage(Query<Object> query, EntityWrapper<Object> wrapper) {
		query.setRecords(sysRoleMapper.selectRolePage(query, query.getCondition()));
		return query;
	}

	/**
	 * 分页查角色列表
	 * @param createUserid
	 * @param roleName
	 * @param page
	 * @return
	 */
	@Override
	public PageInfo<Object> getRolePageList(Long createUserid , String roleName, com.egovchina.partybuilding.common.entity.Page page) {
		PageHelper.startPage(page);
		List<Object> list = sysRoleMapper.listPage(createUserid ,roleName);
		return new PageInfo<>(list);
	}

	/**
	 * 更新角色
	 *
	 * @param roleDto 含有组织信息
	 * @return 成功、失败
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean updateRoleById(RoleDTO roleDto) {
		//删除原有的角色组织关系
		SysRoleDept condition = new SysRoleDept();
		condition.setRoleId(roleDto.getRoleId());
		sysRoleDeptMapper.delete(new EntityWrapper<>(condition));

		//更新角色信息
		SysRole sysRole = new SysRole();
		BeanUtils.copyProperties(roleDto, sysRole);
		sysRoleMapper.updateById(sysRole);

		//维护角色组织关系
		SysRoleDept roleDept = new SysRoleDept();
		roleDept.setRoleId(sysRole.getRoleId());
		roleDept.setDeptId(roleDto.getDeptId());
		sysRoleDeptMapper.insert(roleDept);
		return true;
	}

	/**
	 * 通过组织ID查询角色列表
	 *
	 * @param deptId 组织ID
	 * @return 角色列表
	 */
	@Override
	public List<SysRole> selectListByDeptId(Integer deptId) {
		return sysRoleMapper.selectListByDeptId(deptId);
	}

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysRole> findRolesByUserId(Integer userId) {
		return sysRoleMapper.findRolesByUserId(userId);
	}
}
