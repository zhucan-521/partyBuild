package com.yizheng.partybuilding.system.controller;

import com.yizheng.commons.domain.UserInfo;
import com.yizheng.commons.enums.LoginType;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.exception.BusinessException;
import com.yizheng.commons.util.PaddingBaseFieldUtil;
import com.yizheng.commons.util.StringUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.system.dto.UserAdminDTO;
import com.yizheng.partybuilding.system.dto.UserDTO;
import com.yizheng.partybuilding.system.entity.SysAccount;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.partybuilding.system.entity.SysUserRole;
import com.yizheng.partybuilding.system.service.LoginService;
import com.yizheng.partybuilding.system.service.SysAccountService;
import com.yizheng.partybuilding.system.service.SysUserService;
import com.yizheng.partybuilding.system.util.CommonConstant;
import com.yizheng.partybuilding.system.vo.UserVO;
import com.yizheng.partybuilding.util.PasswordDecoderUtil;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "系统用户模块v2")
@RestController
@RequestMapping("/account")
public class AccountController {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired private SysAccountService accountService; //后台用户服务
    @Autowired private SysUserService userService;       //党员服务
    @Autowired private LoginService loginService;        //登录服务

    @ApiOperation(value = "系统用户登录--默认：99999999-123456", notes = "根据用户名和密码登录", httpMethod = "POST")
    @PostMapping(value = "/login")
    public String login(@RequestParam @ApiParam(value = "登录类型：admin(后台)，edu(教育平台)，represent(党代表平台)", required = true) LoginType loginType ,
                        @RequestParam @ApiParam(value = "身份证号", required = true) String idCardNo,
                        @RequestParam @ApiParam(value = "密码", required = true) String password) {
        return loginService.login(loginType ,idCardNo, password);
    }

    @ApiOperation(value = "获取用户信息（角色、权限）", notes = "获取用户信息（角色、权限）", httpMethod = "GET")
    @GetMapping(value = {"/info", "/info/{idCardNo}"})
    public UserInfo user(@ApiParam(value = "身份证, 为空时查当前用户") @PathVariable(required = false) String idCardNo) {
        //为空时查询当前用户
        if (StringUtil.isEmpty(idCardNo) || (idCardNo != null && idCardNo.equals("undefined"))) {
            return accountService.findUserInfo("PWD", UserContextHolder.getIdCardNo());
        }
        return accountService.findUserInfo("PWD", idCardNo);
    }

    @ApiOperation(value = "系统用户信息", notes = "通过ID查询当前系统用户信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public UserVO user(@ApiParam(value = "用户ID", required = true) @PathVariable Integer id) {
        return accountService.selectUserVoById(id);
    }

    @ApiOperation(value = "删除系统用户", notes = "根据ID删除系统用户", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Boolean userDel(@ApiParam(value = "用户ID", required = true) @PathVariable Integer id) {
        SysAccount sysAccount = accountService.selectById(id);
        if(sysAccount == null){
            throw new BusinessException("没有查到该用户，无法删除");
        }
        return accountService.deleteUserById(sysAccount);
    }

    @ApiOperation(value = "添加用户", notes = "添加用户", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form"),
            @ApiImplicitParam(name = "idCardNo", required = true, value = "身份证号", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "manageDeptId", value = "组织ID", paramType = "form"),
            @ApiImplicitParam(name = "roleIds", value = "角色IDS 多个用,号分隔", paramType = "form"),
            @ApiImplicitParam(name = "representDeptId", value = "XZQH数据字典值，后台帐号管理某一区域（区县、乡镇）的党代表" , paramType = "form")
    })
    @PostMapping
    public Boolean user(@ApiIgnore UserDTO userDto) {
        if(!(userDto != null && !StringUtil.isEmpty(userDto.getIdCardNo()))){
            throw new BusinessException("请传入正确的用户信息");
        }
        //判断是否已存在
        SysAccount deletedUser = accountService.selectDeletedUserByCardNo(userDto.getIdCardNo());
        if (deletedUser != null) {
            throw new BusinessException("该用户已存在，请联系后台管理员");
            //accountService.deleteSysUserByCardNoAndUserId(userDto.getIdCardNo(), deletedUser.getUserId());
        }

        SysAccount sysAccount = new SysAccount();
        BeanUtils.copyProperties(userDto, sysAccount);
        sysAccount.setUsername(userDto.getRealname());
        sysAccount.setPassword(ENCODER.encode(userDto.getPassword()));
        //关联党员id
        SysUser sysUser = userService.selectDeletedUserByCardNo(userDto.getIdCardNo());
        if(sysUser != null){
            sysAccount.setSysUserId(sysUser.getUserId());
        }
        setDefaultAccountInfo(sysAccount);
        accountService.insert(sysAccount);
        userDto.getRoleIds().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysAccount.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }
    //给用户设置默认信息
    private void setDefaultAccountInfo(SysAccount sysAccount){
        sysAccount.setCreateUserid(UserContextHolder.getUserIdLong());
        sysAccount.setCreateUsername(UserContextHolder.getUserName());
        sysAccount.setCreateTime(LocalDateTime.now());
        sysAccount.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysAccount.setUpdateTime(LocalDateTime.now());
        sysAccount.setUpdateUserid(UserContextHolder.getUserIdLong());
        sysAccount.setUpdateUsername(UserContextHolder.getUserName());
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form"),
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "form"),
            @ApiImplicitParam(name = "idCardNo", required = true, value = "身份证", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "manageDeptId", value = "管理组织ID", paramType = "form"),
            @ApiImplicitParam(name = "roleIds", value = "角色IDS 多个用,号分隔", paramType = "form"),
            @ApiImplicitParam(name = "representDeptId", value = "XZQH数据字典值，后台帐号管理某一区域（区县、乡镇）的党代表" , paramType = "form")
    })
    @PutMapping
    public Boolean userUpdate(@ApiIgnore UserDTO userDto) {
        if(!(userDto != null && !StringUtil.isEmpty(userDto.getIdCardNo()))){
            throw new BusinessException("请传入正确的用户信息");
        }
        UserInfo user = accountService.findUserInfo("PWD", userDto.getIdCardNo());
        if (user == null) {
            throw new BusinessDataNotFoundException("用户信息不存在");
        }
        return accountService.updateUser(userDto);
    }

    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST")
    @PostMapping("/updatePwd")
    public Boolean updatePwd(@RequestParam @ApiParam(value = "用户Id", required = true) Long userId,
                             @RequestParam @ApiParam(value = "旧密码", required = true) String oldPwd,
                             @RequestParam @ApiParam(value = "新密码", required = true) String newPwd) {
        SysAccount sysAccount = accountService.selectById(userId);
        if (sysAccount == null) {
            throw new BusinessDataNotFoundException("用户信息不存在");
        }
        if (!PasswordDecoderUtil.matches(oldPwd, sysAccount.getPassword())) {
            throw new BusinessDataCheckFailException("原密码不正确");
        }
        //加密新密码
        sysAccount.setPassword(ENCODER.encode(newPwd));
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(sysAccount);
        return accountService.updatePwdById(sysAccount);
    }

    @ApiOperation(value = "重置密码", notes = "重置密码", httpMethod = "POST")
    @PostMapping("/resetPwd")
    public Boolean updatePwd(@RequestParam @ApiParam(value = "用户Id", required = true) Long userId) {
        SysAccount sysAccount = accountService.selectById(userId);
        if (sysAccount == null) {
            throw new BusinessDataNotFoundException("用户信息不存在");
        }
        sysAccount.setPassword(ENCODER.encode("123456")); //默认
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(sysAccount);
        return accountService.updatePwdById(sysAccount);
    }

    @ApiOperation(value = "用户列表", notes = "用户列表", httpMethod = "GET")
    @GetMapping("/userPage")
    public List<SysAccount> userPage() {
        return accountService.selectList();
    }


    @ApiOperation(value = "列表-获取当前用户创建的所有后台用户信息", notes = "列表-获取当前用户创建的所有后台用户信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query")
    })
    @GetMapping("/listAll")
    public List<UserAdminDTO> listAll(@ApiIgnore UserAdminDTO userDto) {
        //超级管理员可以查看所有用户信息，其他角色只能查看自己创建的用户
        if(!UserContextHolder.getRoles().anyMatch(role -> role.equals("ROLE_ADMIN"))){
            userDto.setCreateUserid(UserContextHolder.getUserIdLong());
        }
        return accountService.listAll(userDto);
    }

    @ApiOperation(value = "修改个人信息", notes = "修改个人信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "form"),
            @ApiImplicitParam(name = "idCardNo", required = true, value = "身份证", paramType = "form"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "form"),
            @ApiImplicitParam(name = "manageDeptId", value = "管理组织ID", paramType = "form"),
            @ApiImplicitParam(name = "roleIds", value = "角色IDS 多个用,号分隔", paramType = "form"),
            @ApiImplicitParam(name = "representDeptId", value = "XZQH数据字典值，后台帐号管理某一区域（区县、乡镇）的党代表" , paramType = "form")
    })
    @PutMapping("/editInfo")
    public Boolean editInfo(@ApiIgnore @Valid UserDTO userDto) {
        return accountService.updateUserInfo(userDto);
    }

}
