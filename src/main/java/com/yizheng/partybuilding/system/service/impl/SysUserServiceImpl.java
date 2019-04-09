package com.yizheng.partybuilding.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yizheng.commons.domain.UserInfo;
import com.yizheng.commons.domain.UserLoginDto;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.util.JwtUtil;
import com.yizheng.partybuilding.service.inf.TabSysDeptService;
import com.yizheng.partybuilding.system.dto.UserAdminDTO;
import com.yizheng.partybuilding.system.dto.UserDTO;
import com.yizheng.partybuilding.system.entity.SysDept;
import com.yizheng.partybuilding.system.entity.SysRole;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.partybuilding.system.entity.SysUserRole;
import com.yizheng.partybuilding.system.mapper.SysUserMapper;
import com.yizheng.partybuilding.system.service.SysMenuService;
import com.yizheng.partybuilding.system.service.SysRoleService;
import com.yizheng.partybuilding.system.service.SysUserRoleService;
import com.yizheng.partybuilding.system.service.SysUserService;
import com.yizheng.partybuilding.system.util.CommonConstant;
import com.yizheng.partybuilding.system.vo.MenuVO;
import com.yizheng.partybuilding.system.vo.UserVO;
import com.yizheng.partybuilding.util.PasswordCodecUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service("sysUserService")
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final SysMenuService sysMenuService;
    private final SysUserMapper sysUserMapper;
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;
    private final RedisTemplate redisTemplate;
    private final TabSysDeptService sysDeptService;

    /**
     * 通过用户名查用户的全部信息
     *
     * @param idCardNo 身份证
     * @return
     */
    @Override
    @Cacheable(value = "DETAIL::USER", key = "#idCardNo", unless = "#result == null")
    public UserInfo findUserInfo(String type, String idCardNo) {
        SysUser condition = new SysUser();
        condition.setIdCardNo(idCardNo);
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        SysUser sysUser = this.selectOne(new EntityWrapper<>(condition));
        if (sysUser == null) {
            return null;
        }

        UserInfo userInfo = new UserInfo();
        UserLoginDto userLoginDto = new UserLoginDto();
        BeanUtils.copyProperties(sysUser, userLoginDto);
        userLoginDto.setUsername(sysUser.getRealname());
        //获取后台用户所能管理的组织名称
        if (userLoginDto.getManageDeptId() != null && userLoginDto.getManageDeptId() > 0) {
            SysDept dept = sysDeptService.selectByPrimaryKey(Long.valueOf(userLoginDto.getManageDeptId()));
            if (dept != null) {
                userLoginDto.setManageDeptName(dept.getName());
            }
        }
        userInfo.setUserLogin(userLoginDto);
        //设置角色列表
        List<SysRole> roleList = sysRoleService.findRolesByUserId(sysUser.getUserId());
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
    public List<SysUser> selectList() {
        return sysUserMapper.selectList(null);
    }

    /**
     * 翻页列表
     * @return
     */
    @Override
    public List<UserAdminDTO> listAll(UserAdminDTO userDto) {
        return sysUserMapper.listAll(userDto);
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO selectUserVoById(Integer id) {
        return sysUserMapper.selectUserVoById(id);
    }

    /**
     * 通过用户名查找已经删除的用户
     *
     * @param idCardNo 用户名
     * @return 用户对象
     */
    @Override
    public SysUser selectDeletedUserByCardNo(String idCardNo) {
        return sysUserMapper.selectDeletedUserByCardNo(idCardNo);
    }

    /**
     * 根据身份证删除用户（真实删除）
     *
     * @param idCardNo
     * @return
     */
    @Override
    public Boolean deleteSysUserByCardNoAndUserId(String idCardNo, Integer userId) {

        sysUserMapper.deleteSysUserByCardNoAndUserId(idCardNo, userId);
        SysUserRole condition = new SysUserRole();
        condition.setUserId(userId);
        sysUserRoleService.delete(new EntityWrapper<>(condition));
        return Boolean.TRUE;
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = "DETAIL::USER", key = "#sysUser.idCardNo")
    public Boolean deleteUserById(SysUser sysUser) {
        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        this.deleteById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = "DETAIL::USER", key = "#userDto.idCardNo")
    public Boolean updateUserInfo(UserDTO userDto) {
        UserVO userVO = sysUserMapper.selectUserVoByCardNo(userDto.getIdCardNo());
        SysUser sysUser = new SysUser();
        if (StrUtil.isNotBlank(userDto.getPassword())
                && StrUtil.isNotBlank(userDto.getNewPassword())) {
            if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
                sysUser.setPassword(ENCODER.encode(userDto.getNewPassword()));
            } else {
                log.warn("原密码错误，修改密码失败:{}", userDto.getIdCardNo());
                throw new BusinessDataCheckFailException("原密码错误，修改失败");
            }
        }
        sysUser.setPhone(userDto.getPhone());
        sysUser.setUserId(userVO.getUserId());
        sysUser.setAvatar(userDto.getAvatar());
        return this.updateById(sysUser);
    }

    @Override
    @CacheEvict(value = "DETAIL::USER", key = "#userDto.idCardNo")
    public Boolean updateUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());
        this.updateById(sysUser);

        SysUserRole condition = new SysUserRole();
        condition.setUserId(userDto.getUserId());
        sysUserRoleService.delete(new EntityWrapper<>(condition));
        userDto.getRoleIds().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }


    /**
     * 保存用户验证码，和randomStr绑定
     *
     * @param randomStr 客户端生成
     * @param imageCode 验证码信息
     */
    @Override
    public void saveImageCode(String randomStr, String imageCode) {
//		TODO redisTemplate.opsForValue().set(SecurityConstants.DEFAULT_CODE_KEY + randomStr, imageCode, SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 根据身份证号和密码登录
     * @param idCardNo
     * @param password
     * @return
     */
    @Override
    public String login(String idCardNo, String password) {
        String token = "";
        UserInfo userInfo = findUserInfo("PWD", idCardNo);
        if (userInfo != null && userInfo.getUserLogin() != null) {
            UserLoginDto user = userInfo.getUserLogin();
            if(user != null && !StringUtils.isEmpty(user.getPassword())){
                boolean flag = PasswordCodecUtil.matches(password , user.getPassword());
                if(flag){
                    user.setPassword(null);
                    token = JwtUtil.generateToken(userInfo);
                } else {
                    throw new BusinessDataCheckFailException("请输入正确的密码");
                }
            } else {
                throw new BusinessDataNotFoundException("未查询到用户信息");
            }
        } else {
            throw new BusinessDataCheckFailException("请输入正确用户名");
        }
        return token;
    }

    @Override
    public Boolean updatePwdById(SysUser sysUser) {
        return sysUserMapper.updatePwdById(sysUser);
    }
}
