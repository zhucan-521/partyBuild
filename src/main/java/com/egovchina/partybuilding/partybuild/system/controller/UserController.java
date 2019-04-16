package com.egovchina.partybuilding.partybuild.system.controller;


import com.egovchina.partybuilding.common.entity.UserInfo;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.service.TabSysUserService;
import com.egovchina.partybuilding.partybuild.system.dto.UserAdminDTO;
import com.egovchina.partybuilding.partybuild.system.dto.UserDTO;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.system.entity.SysUserRole;
import com.egovchina.partybuilding.partybuild.system.service.SysUserService;
import com.egovchina.partybuilding.partybuild.system.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.system.vo.UserVO;
import com.egovchina.partybuilding.partybuild.util.PasswordCodecUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 该接口已弃用，是之前后台用户与党员集合在一起时使用的，后台用户的接口请使用 AccountController
 * zhuyu 2019-03-15
 */
//@Api(tags = "系统用户模块")
//@RestController
//@RequestMapping("/user")
@Deprecated
public class UserController {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private SysUserService userService;

    @Autowired
    private TabSysUserService sysUserService;

    @Deprecated
    @ApiOperation(value = "系统用户登录--默认：99999999-123456", notes = "根据用户名和密码登录", httpMethod = "POST")
    @PostMapping(value = "/login")
    public String login(String idCardNo, String password) {
        return userService.login(idCardNo, password);
    }

    @Deprecated
    @ApiOperation(value = "获取用户信息（角色、权限）", notes = "获取用户信息（角色、权限）", httpMethod = "GET")
    @GetMapping(value = {"/info", "/info/{idCardNo}"})
    public UserInfo user(@ApiParam(value = "身份证, 为空时查当前用户") @PathVariable(required = false) String idCardNo) {
        //为空时查询当前用户
        if (StringUtils.isEmpty(idCardNo)) {
            return userService.findUserInfo("PWD", UserContextHolder.getIdCardNo());
        }
        return userService.findUserInfo("PWD", idCardNo);
    }

    @Deprecated
    @ApiOperation(value = "用户信息", notes = "通过ID查询当前用户信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public UserVO user(@ApiParam(value = "用户ID", required = true) @PathVariable Integer id) {
        return userService.selectUserVoById(id);
    }

    @Deprecated
    @ApiOperation(value = "删除用户", notes = "根据ID删除用户", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Boolean userDel(@ApiParam(value = "用户ID", required = true) @PathVariable Integer id) {
        SysUser sysUser = userService.selectById(id);
        return userService.deleteUserById(sysUser);
    }

    @Deprecated
    @ApiOperation(value = "添加用户", notes = "添加用户", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form"),
            @ApiImplicitParam(name = "idCardNo", required = true, value = "身份证号", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "manageDeptId", value = "组织ID", paramType = "form"),
            @ApiImplicitParam(name = "roleIds", value = "角色IDS 多个用,号分隔", paramType = "form")
    })
    @PostMapping
    public Boolean user(@ApiIgnore UserDTO userDto) {
        SysUser deletedUser = userService.selectDeletedUserByCardNo(userDto.getIdCardNo());
        if (deletedUser != null) {
            userService.deleteSysUserByCardNoAndUserId(userDto.getIdCardNo(), deletedUser.getUserId());
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setCreateUserid(UserContextHolder.getUserIdLong());
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        userService.insert(sysUser);
        userDto.getRoleIds().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }

    @Deprecated
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "form"),
            @ApiImplicitParam(name = "idCardNo", required = true, value = "身份证", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "manageDeptId", value = "管理组织ID", paramType = "form"),
            @ApiImplicitParam(name = "roleIds", value = "角色IDS 多个用,号分隔", paramType = "form")
    })
    @PutMapping
    public Boolean userUpdate(@ApiIgnore @Valid UserDTO userDto) {
        UserInfo user = userService.findUserInfo("PWD", userDto.getIdCardNo());
        if (user == null) {
            throw new BusinessDataNotFoundException("用户信息不存在");
        }
        return userService.updateUser(userDto);
    }

    @Deprecated
    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST")
    @PostMapping("/updatePwd")
    public Boolean updatePwd(@RequestParam @ApiParam(value = "用户Id", required = true) Long userId,
                              @RequestParam @ApiParam(value = "旧密码", required = true) String oldPwd,
                              @RequestParam @ApiParam(value = "新密码", required = true) String newPwd) {
        SysUser sysUser = userService.selectById(userId);
        if (sysUser == null) {
            throw new BusinessDataNotFoundException("用户信息不存在");
        }
        if (!PasswordCodecUtil.matches(oldPwd, sysUser.getPassword())) {
            throw new BusinessDataCheckFailException("原密码不正确");
        }
        //加密新密码
        sysUser.setPassword(ENCODER.encode(newPwd));
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(sysUser);
        return userService.updatePwdById(sysUser);
    }

    @Deprecated
    @ApiOperation(value = "重置密码", notes = "重置密码", httpMethod = "POST")
    @PostMapping("/resetPwd")
    public Boolean updatePwd(@RequestParam @ApiParam(value = "用户Id", required = true) Long userId) {
        SysUser sysUser = userService.selectById(userId);
        if (sysUser == null) {
            throw new BusinessDataNotFoundException("用户信息不存在");
        }
        sysUser.setPassword(ENCODER.encode("123456")); //默认
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(sysUser);
        return userService.updatePwdById(sysUser);
    }

    @Deprecated
    @ApiOperation(value = "用户列表", notes = "用户列表", httpMethod = "GET")
    @GetMapping("/userPage")
    public List<SysUser> userPage() {
        return userService.selectList();
    }

    @Deprecated
    @ApiOperation(value = "列表-获取当前用户创建的所有后台用户信息", notes = "列表-获取当前用户创建的所有后台用户信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query")
    })
    @GetMapping("/listAll")
    public List<UserAdminDTO> listAll(@ApiIgnore @Valid UserAdminDTO userDto) {
        //超级管理员可以查看所有用户信息，其他角色只能查看自己创建的用户
        if(!UserContextHolder.getRoles().anyMatch(role -> role.equals("ROLE_ADMIN"))){
            userDto.setCreateUserid(UserContextHolder.getUserIdLong());
        }
        return userService.listAll(userDto);
    }

    @Deprecated
    @ApiOperation(value = "修改个人信息", notes = "修改个人信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "form"),
            @ApiImplicitParam(name = "idCardNo", required = true, value = "身份证", paramType = "form"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "manageDeptId", value = "管理组织ID", paramType = "form"),
            @ApiImplicitParam(name = "roleIds", value = "角色IDS 多个用,号分隔", paramType = "form")
    })
    @PutMapping("/editInfo")
    public Boolean editInfo(@ApiIgnore @Valid UserDTO userDto) {
        return userService.updateUserInfo(userDto);
    }
}
