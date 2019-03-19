package com.yizheng.partybuilding.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yizheng.partybuilding.system.entity.SysMenu;
import com.yizheng.partybuilding.system.mapper.SysMenuMapper;
import com.yizheng.partybuilding.system.service.SysMenuService;
import com.yizheng.partybuilding.system.util.CommonConstant;
import com.yizheng.partybuilding.system.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    @Cacheable(value = "DETAIL::MENU", key = "#role  + '_menu'")
    public List<MenuVO> findMenuByRoleCode(String role) {
        return sysMenuMapper.findMenuByRoleCode(role);
    }

    @Override
    @CacheEvict(value = "DETAIL::MENU", allEntries = true)
    public Boolean deleteMenu(Integer id) {
        // 删除当前节点
        SysMenu condition1 = new SysMenu();
        condition1.setMenuId(id);
        condition1.setDelFlag(CommonConstant.STATUS_DEL);
        this.updateById(condition1);

        // 删除父节点为当前节点的节点
        SysMenu condition2 = new SysMenu();
        condition2.setParentId(id);
        SysMenu sysMenu = new SysMenu();
        sysMenu.setDelFlag(CommonConstant.STATUS_DEL);
        return this.update(sysMenu, new EntityWrapper<>(condition2));
    }

    @Override
    @CacheEvict(value = "DETAIL::MENU", allEntries = true)
    public Boolean updateMenuById(SysMenu sysMenu) {
        return this.updateById(sysMenu);
    }
}
