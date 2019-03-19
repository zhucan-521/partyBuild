package com.yizheng.partybuilding.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yizheng.partybuilding.system.entity.SysMenu;
import com.yizheng.partybuilding.system.vo.MenuVO;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 *
	 * @param role 角色编号
	 * @return
	 */
	List<MenuVO> findMenuByRoleCode(String role);

	/**
	 * 通过角色ID查询权限
	 *
	 * @param roleIds Ids
	 * @return
	 */
	List<String> findPermissionsByRoleIds(String roleIds);
}
