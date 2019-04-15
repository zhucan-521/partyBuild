package com.egovchina.partybuilding.partybuild.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.UserInfo;
import com.egovchina.partybuilding.common.entity.UserLoginDto;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.partybuild.service.TabSysDeptService;
import com.egovchina.partybuilding.partybuild.system.dto.UserAdminDTO;
import com.egovchina.partybuilding.partybuild.system.dto.UserDTO;
import com.egovchina.partybuilding.partybuild.system.entity.SysAccount;
import com.egovchina.partybuilding.partybuild.system.entity.SysDept;
import com.egovchina.partybuilding.partybuild.system.entity.SysRole;
import com.egovchina.partybuilding.partybuild.system.entity.SysUserRole;
import com.egovchina.partybuilding.partybuild.system.mapper.SysAccountMapper;
import com.egovchina.partybuilding.partybuild.system.service.SysAccountService;
import com.egovchina.partybuilding.partybuild.system.service.SysMenuService;
import com.egovchina.partybuilding.partybuild.system.service.SysRoleService;
import com.egovchina.partybuilding.partybuild.system.service.SysUserRoleService;
import com.egovchina.partybuilding.partybuild.system.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.system.vo.MenuVO;
import com.egovchina.partybuilding.partybuild.system.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Integer userId = sysAccount.getUserId();

        //生成用户信息
        UserInfo userInfo = generateUserInfo(sysAccount);
        //设置角色列表
        String[] roleCodes = getUserRoleCodes(userId);
        userInfo.setRoles(roleCodes);

        //设置权限列表
        String[] permissions = getPermissionByRole(roleCodes);
        userInfo.setPermissions(permissions);
        return userInfo;
    }

    /**
     * 生成UserInfo实体
     *
     * @param sysAccount 系统账号实体
     * @return UserInfo实体
     */
    private UserInfo generateUserInfo(SysAccount sysAccount) {
        UserInfo userInfo = new UserInfo();
        UserLoginDto userLoginDto = new UserLoginDto();
        BeanUtils.copyProperties(sysAccount, userLoginDto);
        userLoginDto.setUsername(sysAccount.getRealname());
        //获取后台用户所能管理的组织名称
        if (userLoginDto.getManageDeptId() != null && userLoginDto.getManageDeptId() > 0) {
            SysDept manageDept = sysDeptService.selectByPrimaryKey(Long.valueOf(userLoginDto.getManageDeptId()));
            if (manageDept != null) {
                userLoginDto.setManageDeptName(manageDept.getName());
            }
        }
        userInfo.setUserLogin(userLoginDto);
        return userInfo;
    }

    /**
     * 根据角色获取权限数组
     *
     * @param roleCodes 角色编码集
     * @return 权限数组
     */
    private String[] getPermissionByRole(String[] roleCodes) {
        Set<String> permissions = new HashSet<>();
        for (String role : roleCodes) {
            List<MenuVO> menuVos = sysMenuService.findMenuByRoleCode(role);
            if (CollectionUtil.isNotEmpty(menuVos)) {
                Set<String> collect = menuVos.stream().map(MenuVO::getPermission)
                        .filter(StringUtils::isNotEmpty)
                        .collect(Collectors.toSet());
                permissions.addAll(collect);
            }
        }
        return permissions.toArray(new String[0]);
    }

    /**
     * 获取用户的角色编码
     *
     * @param userId 用户id
     * @return 角色编码数组
     */
    private String[] getUserRoleCodes(Integer userId) {
        List<String> roleCodes = new ArrayList<>();
        List<SysRole> roleList = sysRoleService.findRolesByUserId(userId);
        if (CollectionUtil.isNotEmpty(roleList)) {
            roleList.forEach(sysRole -> roleCodes.add(sysRole.getRoleCode()));
        }
        return roleCodes.toArray(new String[0]);
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

    @Transactional
    @PaddingBaseField(updateOnly = true)
    @Override
    @CacheEvict(value = "DETAIL::ACCOUNT", key = "#userDto.idCardNo")
    public int updateUser(UserDTO userDto) {
        SysAccount sysAccount = SysAccount.copyOf(userDto);
        boolean updated = this.updateById(sysAccount);
        int affected = 0;
        if (updated) {
            affected = 1;
            SysUserRole condition = new SysUserRole();
            condition.setUserId(userDto.getUserId());
            sysUserRoleService.delete(new EntityWrapper<>(condition));
            userDto.getRoleIds().forEach(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysAccount.getUserId());
                userRole.setRoleId(roleId);
                userRole.insert();
            });
        }
        return affected;
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

    @PaddingBaseField(updateOnly = true)
    @Override
    @CacheEvict(value = "DETAIL::ACCOUNT", key = "#sysAccount.idCardNo")
    public Boolean updatePwdById(SysAccount sysAccount) {
        return sysAccountMapper.updatePwdById(sysAccount);
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int insertAccountWithRole(SysAccount sysAccount, List<Integer> roleIds) {
        int retVal = sysAccountMapper.insert(sysAccount);
        if (retVal > 0 && CollectionUtil.isNotEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysAccount.getUserId());
                userRole.setRoleId(roleId);
                userRole.insert();
            });
        }
        return retVal;
    }
}
