package com.egovchina.partybuilding.partybuild.system.controller;

import com.egovchina.partybuilding.common.entity.UserInfo;
import com.egovchina.partybuilding.common.enums.LoginType;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.exception.BusinessException;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.system.dto.UserAdminDTO;
import com.egovchina.partybuilding.partybuild.system.dto.UserDTO;
import com.egovchina.partybuilding.partybuild.system.entity.SysAccount;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.egovchina.partybuilding.partybuild.system.service.LoginService;
import com.egovchina.partybuilding.partybuild.system.service.SysAccountService;
import com.egovchina.partybuilding.partybuild.system.service.SysUserService;
import com.egovchina.partybuilding.partybuild.system.vo.UserVO;
import com.egovchina.partybuilding.partybuild.util.PasswordCodecUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author Admin
 */
@Deprecated
@Api(tags = "系统用户模块v2")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private SysAccountService accountService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private LoginService loginService;

    @Deprecated
    @ApiOperation(value = "系统用户登录", notes = "根据用户名和密码登录，返回token", httpMethod = "POST")
    @PostMapping(value = "/login")
    public String login(@RequestParam @ApiParam(value = "登录类型：admin(后台)，edu(教育平台)，represent(党代表平台)", required = true) LoginType loginType,
                        @RequestParam @ApiParam(value = "身份证号", required = true) String idCardNo,
                        @RequestParam @ApiParam(value = "密码", required = true) String password) {
        return loginService.login(loginType, idCardNo, password);
    }

    @Deprecated
    @ApiOperation(value = "获取用户信息（角色、权限）", notes = "获取用户信息（角色、权限）", httpMethod = "GET")
    @GetMapping(value = {"/info", "/info/{idCardNo}"})
    public UserInfo user(@ApiParam(value = "身份证, 为空时查当前用户") @PathVariable(required = false) String idCardNo) {
        //为空时查询当前用户
        if (StringUtils.isEmpty(idCardNo)) {
            return accountService.findUserInfo("PWD", UserContextHolder.getIdCardNo());
        }
        return accountService.findUserInfo("PWD", idCardNo);
    }

    @Deprecated
    @ApiOperation(value = "系统用户信息", notes = "通过ID查询当前系统用户信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public UserVO user(@ApiParam(value = "用户ID", required = true) @PathVariable Integer id) {
        return accountService.selectUserVoById(id);
    }

    @Deprecated
    @ApiOperation(value = "删除系统用户", notes = "根据ID删除系统用户", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Boolean userDel(@ApiParam(value = "用户ID", required = true) @PathVariable Integer id) {
        SysAccount sysAccount = accountService.selectById(id);
        if (sysAccount == null) {
            throw new BusinessException("没有查到该用户，无法删除");
        }
        return accountService.deleteUserById(sysAccount);
    }

    @Deprecated
    @ApiOperation(value = "添加用户", notes = "添加用户", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addUser(@RequestBody @Validated UserDTO userDto) {
        //判断是否已存在
        SysAccount deletedUser = accountService.selectDeletedUserByCardNo(userDto.getIdCardNo());
        if (deletedUser != null) {
            throw new BusinessDataCheckFailException("用户已存在");
        }

        SysAccount sysAccount = SysAccount.copyOf(userDto);
        //关联党员id
        associatedPartyMemberIdIfExists(sysAccount);

        int retVal = accountService.insertAccountWithRole(sysAccount, userDto.getRoleIds());
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateUser(@RequestBody @Validated UserDTO userDto) {
        UserInfo dbUser = accountService.findUserInfo("PWD", userDto.getIdCardNo());
        if (dbUser == null) {
            throw new BusinessDataNotFoundException("用户信息不存在");
        }
        return ReturnUtil.buildReturn(accountService.updateUser(userDto));
    }

    @Deprecated
    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST")
    @PostMapping("/updatePwd")
    public Boolean updatePwd(@RequestParam @ApiParam(value = "用户Id", required = true) Long userId,
                             @RequestParam @ApiParam(value = "旧密码", required = true) String oldPwd,
                             @RequestParam @ApiParam(value = "新密码", required = true) String newPwd) {
        SysAccount sysAccount = accountService.selectById(userId);
        if (sysAccount == null) {
            throw new BusinessDataNotFoundException("用户不存在");
        }
        if (!PasswordCodecUtil.matches(oldPwd, sysAccount.getPassword())) {
            throw new BusinessDataCheckFailException("原密码不正确");
        }
        //加密新密码
        sysAccount.setPassword(PasswordCodecUtil.encode(newPwd));
        return accountService.updatePwdById(sysAccount);
    }

    @Deprecated
    @ApiOperation(value = "重置密码", notes = "重置密码", httpMethod = "POST")
    @PostMapping("/resetPwd")
    public Boolean updatePwd(@RequestParam @ApiParam(value = "用户Id", required = true) Long userId) {
        SysAccount sysAccount = accountService.selectById(userId);
        if (sysAccount == null) {
            throw new BusinessDataNotFoundException("用户信息不存在");
        }
        sysAccount.setPassword(PasswordCodecUtil.encode("123456")); //默认
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(sysAccount);
        return accountService.updatePwdById(sysAccount);
    }

    @Deprecated
    @ApiOperation(value = "用户列表", notes = "用户列表", httpMethod = "GET")
    @GetMapping("/userPage")
    public List<SysAccount> userPage() {
        return accountService.selectList();
    }

    @Deprecated
    @ApiOperation(value = "列表-获取当前用户创建的所有后台用户信息", notes = "列表-获取当前用户创建的所有后台用户信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realname", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query")
    })
    @GetMapping("/listAll")
    public List<UserAdminDTO> listAll(@ApiIgnore UserAdminDTO userDto) {
        //超级管理员可以查看所有用户信息，其他角色只能查看自己创建的用户
        if (UserContextHolder.getRoles().noneMatch(role -> role.equals("ROLE_ADMIN"))) {
            userDto.setCreateUserid(UserContextHolder.getUserIdLong());
        }
        return accountService.listAll(userDto);
    }

    /**
     * 如果指定身份证对应的党员存在，给账号关联党员id
     * @param sysAccount 账号实体
     */
    private void associatedPartyMemberIdIfExists(SysAccount sysAccount) {
        SysUser sysUser = userService.selectDeletedUserByCardNo(sysAccount.getIdCardNo());
        if (sysUser != null) {
            sysAccount.setSysUserId(sysUser.getUserId().intValue());
        }
    }

}
