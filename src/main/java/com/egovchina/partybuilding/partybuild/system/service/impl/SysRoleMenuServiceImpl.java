package com.egovchina.partybuilding.partybuild.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egovchina.partybuilding.partybuild.system.entity.SysRoleMenu;
import com.egovchina.partybuilding.partybuild.system.mapper.SysRoleMenuMapper;
import com.egovchina.partybuilding.partybuild.system.service.SysRoleMenuService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sysRoleMenuService")
@AllArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

	/**
	 * @param role
	 * @param roleId  角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	@Override
	@Caching(evict = {
			@CacheEvict(value = "DETAIL::MENU", key = "#role + '_menu'"),
			@CacheEvict(value = "DETAIL::ACCOUNT", allEntries = true)
	})
	public Boolean insertRoleMenus(String role, Integer roleId, String menuIds) {
		SysRoleMenu condition = new SysRoleMenu();
		condition.setRoleId(roleId);
		//先删除
		this.delete(new EntityWrapper<>(condition));

		if (StringUtils.isBlank(menuIds)) {
			return Boolean.TRUE;
		}

		List<SysRoleMenu> roleMenuList = new ArrayList<>();
		String[] menuIdList = menuIds.split(",");

		for (String menuId : menuIdList) {
			SysRoleMenu roleMenu = new SysRoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(Integer.valueOf(menuId));
			roleMenuList.add(roleMenu);
		}
		return this.insertBatch(roleMenuList);
	}
}
