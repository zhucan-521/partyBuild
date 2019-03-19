package com.yizheng.partybuilding.service.impl;

import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.dto.RegistryDto;
import com.yizheng.partybuilding.dto.TransferUserDeptInfo;
import com.yizheng.partybuilding.entity.TabPbMemberAddList;
import com.yizheng.partybuilding.entity.TabPbMemberReduceList;
import com.yizheng.partybuilding.repository.TabPbMemberAddListMapper;
import com.yizheng.partybuilding.repository.TabPbMemberReduceListMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.ExtendedInfoService;
import com.yizheng.partybuilding.service.inf.ITabSysUserService;
import com.yizheng.partybuilding.service.inf.TabSysDeptService;
import com.yizheng.partybuilding.system.entity.SysDept;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.partybuilding.system.util.CommonConstant;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 党员--用户信息接口
 */
@Service
public class TabSysUserServiceImpl implements ITabSysUserService {

    @Autowired
    private TabSysUserMapper sysUserMapper;

    @Autowired
    private TabPbMemberReduceListMapper memberReduceListMapper;

    @Autowired
    private TabPbMemberAddListMapper memberAddListMapper;

    @Autowired
    private TabSysDeptService sysDeptService;

    @Autowired
    private ExtendedInfoService extendedInfoService;

    /**
     * 根据组织Id 获取党务工作者信息
     *
     * @param deptId
     * @return
     */
    @Override
    public TransferUserDeptInfo getDWRoleUserInfoByDeptId(Long deptId) {
        return sysUserMapper.getDWRoleUserInfoByDeptId(deptId);
    }

    /**
     * 根据多个组织id获取，多个党务工作者
     *
     * @param manageDeptIds
     * @return
     */
    @Override
    public List<TransferUserDeptInfo> getDWRoleUserInfoByDeptIds(List<String> manageDeptIds) {
        return sysUserMapper.getDWRoleUserInfoByDeptIds(manageDeptIds);
    }

    /**
     * 组织关系接转-修改用户的deptId
     *
     * @param userId
     * @param deptId
     */
    @Override
    public void updateDeptIdByUserId(Integer userId, Integer deptId) {
        sysUserMapper.updateDeptIdByUserId(userId, deptId);
    }

    /**
     * 获取党员党籍相关信息
     * @param userId
     * @return
     */
    @Override
    public SysUser getRegistryId(Long userId) {
        if(userId == null){
            return null;
        }
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        SysDept dept = sysDeptService.selectByPrimaryKey(user.getJoinOrgId());
        TabPbMemberReduceList reduce = memberReduceListMapper.selectByUserId(userId);
        if(reduce!=null){
            user.setOutType(reduce.getOutType());
            user.setReduceTime(reduce.getReduceTime());
        }
        TabPbMemberAddList add = memberAddListMapper.selectByUserId(userId);
        if(add!=null){
            user.setAddTime(add.getAddTime());
        }
        if(dept!=null){
            user.setJoinOrgName(dept.getName());
        }
        setRegularTime(user);
        return user;
    }

    @Override
    public boolean updataUser(SysUser sysUser) {
        if(sysUser != null && !ObjectUtils.isEmpty(sysUser.getUserId())){
            Integer userId = sysUser.getUserId();
            SysUser oldUser = sysUserMapper.selectByPrimaryKey(userId.longValue());
            if (sysUser.getReduceTime()!=null && sysUser.getOutType()!=null && CommonConstant.STATUS_DEL.equals(oldUser.getDelFlag())){
                TabPbMemberReduceList reduce = new TabPbMemberReduceList();
                reduce.setUserId(userId.longValue());
                reduce.setOutType(sysUser.getOutType());
                reduce.setReduceTime(sysUser.getReduceTime());
                reduce.setDeptId(oldUser.getDeptId().longValue());
                reduce.setRealName(oldUser.getUsername());
                memberReduceListMapper.insertSelective(reduce);
                sysUser.setRegistryStatus(reduce.getOutType());
                sysUser.setDelFlag(CommonConstant.STATUS_DEL);
            }
            return sysUserMapper.updateByPrimaryKeySelective(sysUser) > 0;
        }
        return false;
    }

    @Override
    public List<RegistryDto> getRegistryList(Long userId) {
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        if(user==null){return null;}
        List<RegistryDto> list = new ArrayList<>();
        //预备党员
        if(user.getJoinTime()!= null){
            list.add(new RegistryDto(1L,user.getJoinTime(),user.getJoinTime(),user.getContactor()));
        }
        //正式党员
        if(user.getRegularTime() != null){
            list.add(new RegistryDto(2L,user.getRegularTime(),user.getRegularTime(),user.getUpdateUsername()));
        }
        //新增 和 减少
        List<TabPbMemberAddList> add = memberAddListMapper.selectListByUserId(userId);
        add.forEach(a -> {
            list.add(new RegistryDto(a.getInType(),a.getCreateTime(),a.getCreateTime(),a.getCreateUsername()));
        });
        List<TabPbMemberReduceList> reduce = memberReduceListMapper.selectListByUserId(userId);
        reduce.forEach(r -> {
            list.add(new RegistryDto(r.getOutType(),r.getCreateTime(),r.getCreateTime(),r.getCreateUsername()));
        });
        return list;
    }

    private void setRegularTime(SysUser sysUser) {
        if (sysUser.getRegularTime() != null) {
            Calendar calBegin = Calendar.getInstance();
            Calendar calEnd = Calendar.getInstance();
            calBegin.setTime(sysUser.getRegularTime());
            calEnd.setTime(new Date());
            sysUser.setPartyStanding(calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR) + 1);
        }
    }
    /**
     * 社区活动选人接口(通过组织主键来获取)
     * @param user
     * @return
     */
    @Override
    public List<SysUser> selectAllRegister(SysUser user){
        if(user.getReportOrgId()!=null && !"".equals(user.getReportOrgId())){
            var list = sysUserMapper.selectAllRegister(user);
            return list;
        }else {
            user.setDeptId(UserContextHolder.currentUser().getDeptId());
            var list = sysUserMapper.selectAllRegister(user);
            return list;
        }
    }
}
