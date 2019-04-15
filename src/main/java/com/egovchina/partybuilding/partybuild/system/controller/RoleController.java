package com.egovchina.partybuilding.partybuild.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.system.dto.RoleDTO;
import com.egovchina.partybuilding.partybuild.system.entity.SysRole;
import com.egovchina.partybuilding.partybuild.system.service.SysRoleMenuService;
import com.egovchina.partybuilding.partybuild.system.service.SysRoleService;
import com.egovchina.partybuilding.partybuild.system.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.system.util.Query;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Deprecated
@Api(tags = "系统角色模块")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Deprecated
    @ApiOperation(value = "角色详情", notes = "返回指定角色ID的详情信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public SysRole role(@ApiParam(value = "角色ID", required = true) @PathVariable Integer id) {
        return sysRoleService.selectById(id);
    }

    @Deprecated
    @ApiOperation(value = "添加角色", notes = "添加角色", httpMethod = "POST")
    @PostMapping
    public Boolean role(@RequestBody RoleDTO roleDto) {
        roleDto.setCreateUserid(UserContextHolder.getUserIdLong());
        return sysRoleService.insertRole(roleDto);
    }

    @Deprecated
    @ApiOperation(value = "修改角色", notes = "修改角色", httpMethod = "PUT")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, paramType = "form"),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", paramType = "form"),
            @ApiImplicitParam(name = "roleDesc", value = "角色描述", paramType = "form"),
            @ApiImplicitParam(name = "deptId", value = "组织Id", paramType = "form"),
            @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "form")
    })*/
    @PutMapping
    public Boolean roleUpdate(@RequestBody RoleDTO roleDto) {
        return sysRoleService.updateRoleById(roleDto);
    }

    @Deprecated
    @ApiOperation(value = "删除角色", notes = "删除角色", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Boolean roleDel(@ApiParam(value = "角色ID", required = true) @PathVariable Integer id) {
        SysRole sysRole = sysRoleService.selectById(id);
        if (sysRole == null) {
            throw new BusinessDataIncompleteException("操作失败, 角色不存在");
        }
        sysRole.setDelFlag(CommonConstant.STATUS_DEL);
        return sysRoleService.updateById(sysRole);
    }

    @Deprecated
    @ApiOperation(value = "获取角色列表", notes = "获取指定组织下的所有角色", httpMethod = "GET")
    @GetMapping("/roleList/{deptId}")
    public List<SysRole> roleList(@ApiParam(value = "组织ID", required = true) @PathVariable Integer deptId) {
        return sysRoleService.selectListByDeptId(deptId);
    }

    @Deprecated
    @ApiOperation(value = "获取当前用户的所有角色列表", notes = "获取当前用户的所有角色列表", httpMethod = "GET")
    @GetMapping("/roleList")
    public List<SysRole> roleList() {
        SysRole role = new SysRole();
        role.setCreateUserid(UserContextHolder.getUserIdLong());
        role.setDelFlag(CommonConstant.STATUS_NORMAL);
        return sysRoleService.selectList(new EntityWrapper<>(role));
    }

    @Deprecated
    @ApiOperation(value = "更新角色菜单", notes = "更新角色菜单", httpMethod = "PUT")
    @PutMapping("/roleMenuUpd")
    public Boolean roleMenuUpd(@ApiParam(value = "角色ID" ) @RequestParam(value = "roleId", required = false) Integer roleId,
                               @ApiParam(value = "菜单ID 多个用,号分隔") @RequestParam(value = "menuIds", required = false) String menuIds) {
        SysRole sysRole = sysRoleService.selectById(roleId);
        if (sysRole == null) {
            throw new BusinessDataIncompleteException("角色不存在");
        }
        return sysRoleMenuService.insertRoleMenus(sysRole.getRoleCode(), roleId, menuIds);
    }


    /**
     * 分页查询角色信息
     * 要被废弃
     * @param params 分页对象
     * @return 分页对象
     */
    @Deprecated
    @ApiOperation(value = "分页查询角色菜单", notes = "分页查询角色菜单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query")
    })
    @GetMapping("/rolePage")
    public Page rolePage(@ApiIgnore @RequestParam Map<String, Object> params) {
        return sysRoleService.selectwithDeptPage(new Query<>(params), new EntityWrapper<>());
    }

    @Deprecated
    @ApiOperation(value = "分页查询角色菜单v2", notes = "分页查询角色菜单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query")
    })
    @GetMapping("/rolePageList")
    public PageInfo<Object> rolePageList(String roleName, com.egovchina.partybuilding.common.entity.Page page) {
        Long createUserid = 0L;
        //超级管理员可以查看所有权限信息，其他角色只能查看自己创建的权限
        if(!UserContextHolder.getRoles().anyMatch(role -> role.equals("ROLE_ADMIN"))){
            createUserid = UserContextHolder.getUserIdLong();
        }
        return sysRoleService.getRolePageList(createUserid , roleName, page);
    }
}
