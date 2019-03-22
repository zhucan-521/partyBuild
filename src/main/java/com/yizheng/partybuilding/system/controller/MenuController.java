package com.yizheng.partybuilding.system.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yizheng.commons.exception.BusinessException;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.system.dto.MenuTree;
import com.yizheng.partybuilding.system.entity.SysMenu;
import com.yizheng.partybuilding.system.service.SysMenuService;
import com.yizheng.partybuilding.system.util.CommonConstant;
import com.yizheng.partybuilding.system.vo.MenuVO;
import com.yizheng.partybuilding.system.vo.TreeUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.*;

@Api(tags = "系统菜单模块")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "新增菜单", notes = "新增菜单", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "菜单名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "菜单类型 （0菜单 1按钮）", paramType = "form"),
            @ApiImplicitParam(name = "parentId", value = "父菜单ID", paramType = "form"),
            @ApiImplicitParam(name = "icon", value = "图标", paramType = "form"),
            @ApiImplicitParam(name = "component", value = "VUE页面", paramType = "form"),
            @ApiImplicitParam(name = "path", value = "前端URL", paramType = "form"),
            @ApiImplicitParam(name = "sort", value = "排序值", paramType = "form")
    })
    @PostMapping
    public Boolean menu(@ApiIgnore @Valid SysMenu sysMenu) {
        if(!(sysMenu != null && sysMenu.getMenuId() != null)){
            throw new BusinessException("请传入菜单信息");
        }
        SysMenu dbMenu = sysMenuService.selectById(sysMenu.getMenuId());
        if(dbMenu != null){
            throw new BusinessException("传入的MenuId已存在");
        }
        return sysMenuService.insert(sysMenu);
    }

    @ApiOperation(value = "删除菜单(级联删除下级节点)", notes = "删除菜单(级联删除下级节点)", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Boolean menuDel(@ApiParam(value = "菜单ID", required = true) @PathVariable Integer id) {
        return sysMenuService.deleteMenu(id);
    }

    @ApiOperation(value = "更新菜单", notes = "更新菜单", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "菜单名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "type", value = "菜单类型 （0菜单 1按钮）", paramType = "form"),
            @ApiImplicitParam(name = "parentId", value = "父菜单ID", paramType = "form"),
            @ApiImplicitParam(name = "icon", value = "图标", paramType = "form"),
            @ApiImplicitParam(name = "component", value = "VUE页面", paramType = "form"),
            @ApiImplicitParam(name = "path", value = "前端URL", paramType = "form"),
            @ApiImplicitParam(name = "sort", value = "排序值", paramType = "form")
    })
    @PutMapping
    public Boolean menuUpdate(@ApiIgnore @Valid SysMenu sysMenu) {
        return sysMenuService.updateMenuById(sysMenu);
    }

    @ApiOperation(value = "通过角色查询用户菜单", notes = "通过角色查询用户菜单", httpMethod = "GET")
    @GetMapping("/findMenuByRole/{role}")
    public List<MenuVO> findMenuByRole(@ApiParam(value = "角色名称", required = true) @PathVariable String role) {
        return sysMenuService.findMenuByRoleCode(role);
    }

    @ApiOperation(value = "返回当前用户菜单树", notes = "返回当前用户的菜单并以树形返回", httpMethod = "GET")
    @GetMapping("/userMenu")
    public List<MenuTree> userMenu() {
        // 获取符合条件得菜单
        Set<MenuVO> all = new HashSet<>();
        UserContextHolder.getRoles().forEach(role -> all.addAll(sysMenuService.findMenuByRoleCode(role)));
        List<MenuTree> menuTreeList = new ArrayList<>();
        all.forEach(menuVo -> {
            String menuType = menuVo.getType();
            if (CommonConstant.MENU.equals(menuType) ||
                    CommonConstant.JUMP.equals(menuType)) {
                menuTreeList.add(new MenuTree(menuVo));
            }
        });
        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        return TreeUtil.bulid(menuTreeList, -1);
    }

    @ApiOperation(value = "返回系统菜单树", notes = "该方法返回系统中所有菜单并以树的形式展现", httpMethod = "GET")
    @GetMapping(value = "/allTree")
    public List<MenuTree> getTree() {
        SysMenu condition = new SysMenu();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        return TreeUtil.bulidTree(sysMenuService.selectList(new EntityWrapper<>(condition).orderBy(CommonConstant.SQL_SORT)), -1);
    }

    @ApiOperation(value = "返回角色菜单树", notes = "返回指定角色拥有的菜单并以树形放回", httpMethod = "GET")
    @GetMapping("/roleTree/{role}")
    public List<MenuTree> roleTree(@ApiParam(value = "角色名", required = true) @PathVariable String role) {
        List<MenuVO> menus = sysMenuService.findMenuByRoleCode(role);
        List<MenuTree> menuTreeList = new ArrayList<>();
        menus.forEach(menuVo -> {
            String menuType = menuVo.getType();
            if (CommonConstant.MENU.equals(menuType) ||
                    CommonConstant.JUMP.equals(menuType)) {
                menuTreeList.add(new MenuTree(menuVo));
            }
        });
        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        return TreeUtil.bulid(menuTreeList, -1);
    }

    @ApiOperation(value = "菜单详情", notes = "返回指定菜单ID的详情信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public SysMenu menu(@ApiParam(value = "菜单ID", required = true) @PathVariable Integer id) {
        return sysMenuService.selectById(id);
    }

}
