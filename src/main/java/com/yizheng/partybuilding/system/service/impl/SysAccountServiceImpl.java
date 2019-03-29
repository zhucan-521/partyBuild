package com.yizheng.partybuilding.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yizheng.commons.domain.UserInfo;
import com.yizheng.commons.domain.UserLoginDto;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.partybuilding.service.inf.TabSysDeptService;
import com.yizheng.partybuilding.system.dto.UserAdminDTO;
import com.yizheng.partybuilding.system.dto.UserDTO;
import com.yizheng.partybuilding.system.entity.SysAccount;
import com.yizheng.partybuilding.system.entity.SysDept;
import com.yizheng.partybuilding.system.entity.SysRole;
import com.yizheng.partybuilding.system.entity.SysUserRole;
import com.yizheng.partybuilding.system.mapper.SysAccountMapper;
import com.yizheng.partybuilding.system.service.*;
import com.yizheng.partybuilding.system.util.CommonConstant;
import com.yizheng.partybuilding.system.vo.MenuVO;
import com.yizheng.partybuilding.system.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final SysMenuService sysMenuService;
    private final SysAccountMapper sysAccountMapper;
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;
    private final TabSysDeptService sysDeptService;


    /**
     * 通过用户名查用户的全部信息
     * @param idCardNo 身份证
     * @return
     */
    @Override
    @Cacheable(value = "DETAIL::ACCOUNT", key = "#idCardNo", unless = "#result == null")
    public UserInfo findUserInfo(String type, String idCardNo) {
        SysAccount condition = new SysAccount();
        condition.setIdCardNo(idCardNo);
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        SysAccount sysAccount = this.selectOne(new EntityWrapper<>(condition));
        if (sysAccount == null) {
            return null;
        }

        UserInfo userInfo = new UserInfo();
        UserLoginDto userLoginDto = new UserLoginDto();
        BeanUtils.copyProperties(sysAccount, userLoginDto);
        userLoginDto.setUsername(sysAccount.getRealname());
        //获取后台用户所能管理的组织名称
        if (userLoginDto.getManageDeptId() != null && userLoginDto.getManageDeptId() > 0) {
            SysDept dept = sysDeptService.selectByPrimaryKey(Long.valueOf(userLoginDto.getManageDeptId()));
            if (dept != null) {
                userLoginDto.setManageDeptName(dept.getName());
            }
        }
        userInfo.setUserLogin(userLoginDto);
        //设置角色列表
        List<SysRole> roleList = sysRoleService.findRolesByUserId(sysAccount.getUserId());
        List<String> roleCodes = new ArrayList<>();
        if (CollUtil.isNotEmpty(roleList)) {
            roleList.forEach(sysRole -> roleCodes.add(sysRole.getRoleCode()));
        }
        userInfo.setRoles(ArrayUtil.toArray(roleCodes, String.class));

        //设置权限列表（menu.permission）
        Set<MenuVO> menuVoSet = new HashSet<>();
        for (String role : roleCodes) {
            List<MenuVO> menuVos = sysMenuService.findMenuByRoleCode(role);
            menuVoSet.addAll(menuVos);
        }
        Set<String> permissions = new HashSet<>();
        for (MenuVO menuVo : menuVoSet) {
            if (StringUtils.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return userInfo;
    }

    @Override
    public List<SysAccount> selectList() {
        return sysAccountMapper.selectList(null);
    }

    /**
     * 翻页用户列表 - ROLE_ADMIN角色查看所有用户，其他角色查看自己创建的用户
     * @return
     */
    @Override
    public List<UserAdminDTO> listAll(UserAdminDTO userDto) {
        return sysAccountMapper.listAll(userDto);
    }

    /**
     * 删除用户信息
     * @param sysAccount 用户
     * @return
     */
    @Override
    @CacheEvict(value = "DETAIL::ACCOUNT", key = "#sysAccount.idCardNo")
    public Boolean deleteUserById(SysAccount sysAccount) {
        deleteSysUserByCardNoAndUserId(sysAccount.getIdCardNo(), sysAccount.getUserId());
        //sysUserRoleService.deleteByUserId(sysAccount.getUserId());
        //this.deleteById(sysAccount.getUserId());
        return Boolean.TRUE;
    }

    /**
     * 修改用户信息
     * @param userDto  用户信息
     * @return
     */
    @Override
    @CacheEvict(value = "DETAIL::ACCOUNT", key = "#userDto.idCardNo")
    public Boolean updateUserInfo(UserDTO userDto) {
        UserVO userVO = sysAccountMapper.selectUserVoByCardNo(userDto.getIdCardNo());
        SysAccount sysAccount = new SysAccount();
        if (StrUtil.isNotBlank(userDto.getPassword()) && StrUtil.isNotBlank(userDto.getNewPassword())) {
            if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
                sysAccount.setPassword(ENCODER.encode(userDto.getNewPassword()));
            } else {
                log.warn("原密码错误，修改密码失败:{}", userDto.getIdCardNo());
                throw new BusinessDataCheckFailException("原密码错误，修改失败");
            }
        }
        sysAccount.setPhone(userDto.getPhone());
        sysAccount.setUserId(userVO.getUserId());
        sysAccount.setAvatar(userDto.getAvatar());
        return this.updateById(sysAccount);
    }

    @Override
    @CacheEvict(value = "DETAIL::ACCOUNT", key = "#userDto.idCardNo")
    public Boolean updateUser(UserDTO userDto) {
        SysAccount sysAccount = new SysAccount();
        BeanUtils.copyProperties(userDto, sysAccount);
        sysAccount.setUpdateTime(LocalDateTime.now());
        this.updateById(sysAccount);

        SysUserRole condition = new SysUserRole();
        condition.setUserId(userDto.getUserId());
        sysUserRoleService.delete(new EntityWrapper<>(condition));
        userDto.getRoleIds().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysAccount.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }

    /**
     * 通过ID查询用户信息
     * @param id 用户ID
     * @return
     */
    @Override
    public UserVO selectUserVoById(Integer id) {
        return sysAccountMapper.selectUserVoById(id);
    }

    /**
     * 通过用户名查找已经删除的用户
     * @param idCardNo 身份证
     * @return
     */
    @Override
    public SysAccount selectDeletedUserByCardNo(String idCardNo) {
        return sysAccountMapper.selectDeletedUserByCardNo(idCardNo);
    }

    /**
     * 根据身份证删除用户（假删除）
     * @param idCardNo idCardNo
     * @param userId   userId
     * @return
     */
    @Override
    public Boolean deleteSysUserByCardNoAndUserId(String idCardNo, Integer userId) {
        sysAccountMapper.deleteSysUserByCardNoAndUserId(idCardNo , userId);
        SysUserRole condition = new SysUserRole();
        condition.setUserId(userId);
        sysUserRoleService.delete(new EntityWrapper<>(condition));
        return Boolean.TRUE;
    }

    /**
     * 保存用户验证码，和randomStr绑定
     * @param randomStr 客户端生成
     * @param imageCode 验证码信息
     */
    @Override
    public void saveImageCode(String randomStr, String imageCode) {
        //redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + randomStr, imageCode, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
    }

    @Override
    @CacheEvict(value = "DETAIL::ACCOUNT", key = "#sysAccount.idCardNo")
    public Boolean updatePwdById(SysAccount sysAccount) {
        return sysAccountMapper.updatePwdById(sysAccount);
    }
}
