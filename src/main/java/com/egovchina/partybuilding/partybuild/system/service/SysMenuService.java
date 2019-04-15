package com.egovchina.partybuilding.partybuild.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.partybuild.system.entity.SysMenu;
import com.egovchina.partybuilding.partybuild.system.vo.MenuVO;

import java.util.List;


/**
 * 菜单权限服务类
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 通过角色编号查询URL 权限
     *
     * @param role 角色编号
     * @return 菜单列表
     */
    List<MenuVO> findMenuByRoleCode(String role);

    /**
     * 级联删除菜单
     *
     * @param id 菜单ID
     * @return 成功、失败
     */
    Boolean deleteMenu(Integer id);

    /**
     * 根据ID更新菜单信息
     *
     * @param sysMenu 菜单信息
     * @return 成功、失败
     */
    Boolean updateMenuById(SysMenu sysMenu);
}
