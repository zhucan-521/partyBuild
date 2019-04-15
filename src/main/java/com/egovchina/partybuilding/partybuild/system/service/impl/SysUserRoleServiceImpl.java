package com.egovchina.partybuilding.partybuild.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egovchina.partybuilding.partybuild.system.entity.SysUserRole;
import com.egovchina.partybuilding.partybuild.system.mapper.SysUserRoleMapper;
import com.egovchina.partybuilding.partybuild.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	/**
	 * 根据用户Id删除该用户的角色关系
	 *
	 */
	@Override
	public Boolean deleteByUserId(Integer userId) {
		return baseMapper.deleteByUserId(userId);
	}
}
