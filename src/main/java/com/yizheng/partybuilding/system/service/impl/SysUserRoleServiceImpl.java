package com.yizheng.partybuilding.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yizheng.partybuilding.system.entity.SysUserRole;
import com.yizheng.partybuilding.system.mapper.SysUserRoleMapper;
import com.yizheng.partybuilding.system.service.SysUserRoleService;
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
