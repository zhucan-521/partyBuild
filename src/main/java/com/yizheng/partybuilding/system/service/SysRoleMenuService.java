package com.yizheng.partybuilding.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.yizheng.partybuilding.system.entity.SysRoleMenu;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

	/**
	 * 更新角色菜单
	 *
	 * @param role
	 * @param roleId  角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	Boolean insertRoleMenus(String role, Integer roleId, String menuIds);
}
