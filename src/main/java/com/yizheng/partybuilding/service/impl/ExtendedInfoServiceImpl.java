package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.UserLoginDto;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbMemberReduceList;
import com.yizheng.commons.exception.BusinessDataIncompleteException;
import com.yizheng.partybuilding.repository.TabPbMemberReduceListMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.ExtendedInfoService;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.partybuilding.system.util.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;


@Service("extendedInfoService")
public class ExtendedInfoServiceImpl implements ExtendedInfoService {
    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    private TabPbMemberReduceListMapper reduceListMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return 0;
    }

    /**
     * 补录扩展信息
     * @param record
     * @return
     */
    @Override
    public int insert(SysUser record) {
        if(isEmpty(record.getAncestorPlace())){
            throw new BusinessDataIncompleteException("籍贯不能为空");
        }
        if(isEmpty(record.getWorkDate())){
            throw new BusinessDataIncompleteException("参加工作时间不能为空");
        }
        if(isEmpty(record.getTechnician())){
            throw new BusinessDataIncompleteException("技术职务不能为空");
        }
        if(isEmpty(record.getStratum())){
            throw new BusinessDataIncompleteException("新阶层不能为空");
        }
        return tabSysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return 0;
    }

    @Override
    public SysUser selectByPrimaryKey(Long userId) {
        if(ObjectUtils.isEmpty(userId)){
            throw new BusinessDataIncompleteException("id不能为空");
        }
        return tabSysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(SysUser record) {
        return 0;
    }


    /**
     * 编辑扩展信息
     * @param record
     * @return
     */
    @Transactional
    @Override
    public int updateByPrimaryKey(SysUser record) {
        if(isEmpty(record.getUserId())){
            throw new BusinessDataIncompleteException("人员id不存在");
        }
        if(isEmpty(record.getAncestorPlace())){
            throw new BusinessDataIncompleteException("籍贯不能为空");
        }
        return tabSysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<SysUser> selectPartyByIdCardNoOnUserName(SysUser sysUser, Page page) {
        PageHelper.startPage(page);
        List<SysUser> sysUsers = tabSysUserMapper.selectPertyByIdCardNoOnUserName(sysUser);
        return new PageInfo<>(sysUsers);
    }

    /**
     * 删除user信息 逻辑删除
     * @param userId
     * @return
     */
    @Override
    public int updateByUserId(Long userId) {
        return tabSysUserMapper.updateByUserId(userId);
    }

    /**
     * 删除单元录入到党员减少表中。delFlag -> 1
     * @param reduce
     * @return
     */
    @Override
    public Boolean updateByUserId(TabPbMemberReduceList reduce) {
        if(reduce==null || ObjectUtils.isEmpty(reduce.getUserId())){
            return false;
        }
        SysUser user = new SysUser();
        user.setUserId(reduce.getUserId().intValue());
        user.setDelFlag(CommonConstant.STATUS_DEL);
        user.setRegistryStatus(reduce.getOutType());
        int flag = tabSysUserMapper.updateByPrimaryKeySelective(user);
        if (flag > 0){
            SysUser newuser = tabSysUserMapper.selectByPrimaryKey(reduce.getUserId());
            if(newuser!=null && !ObjectUtils.isEmpty(newuser.getDeptId())){
                reduce.setDeptId(newuser.getDeptId().longValue());
                reduce.setRealName(newuser.getUsername());
                flag += reduceListMapper.insertSelective(reduce);
            }
        }
        return flag > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean restoreUser(Long userId) {
        UserLoginDto user = UserContextHolder.currentUser();
        if(user==null){
            return false;
        }
        TabPbMemberReduceList reduceList = reduceListMapper.selectByUserId(userId);
        reduceList.setDelFlag(CommonConstant.STATUS_DEL);
        reduceListMapper.updateByPrimaryKeySelective(reduceList);
        tabSysUserMapper.updateByPrimaryKeySelective(new SysUser()
                .setUserId(userId.intValue())
                .setRegistryStatus(2L)
                .setDelFlag(CommonConstant.STATUS_NORMAL)
                .setUpdateUserid(user.getUserId().longValue())
                .setUpdateUsername(user.getRealname()));
        return true;
    }
}
