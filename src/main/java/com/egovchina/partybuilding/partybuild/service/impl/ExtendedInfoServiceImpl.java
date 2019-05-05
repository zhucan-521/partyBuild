package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbMemberReduceList;
import com.egovchina.partybuilding.partybuild.repository.TabPbMemberReduceListMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ExtendedInfoService;
import com.egovchina.partybuilding.partybuild.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.vo.PartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SysUserVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
public class ExtendedInfoServiceImpl implements ExtendedInfoService {

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbMemberReduceListMapper reduceListMapper;

    @Override
    public PartyMemberVO selectPartyMemberVOById(Long userId) {
        return tabSysUserMapper.selectByPrimaryKeyToAll(userId);
    }

    @Override
    public PageInfo<SysUserVO> selectPartyByIdCardNoOrUserName(String idCardNo, String username, Page page) {
        PageHelper.startPage(page);
        List<SysUserVO> sysUsers = tabSysUserMapper.selectPartyByIdCardNoOnUserName(idCardNo, username);
        return new PageInfo<>(sysUsers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateByUserId(DeletePartyMemberDTO reduce) {
        SysUser user = new SysUser();
        user.setUserId(reduce.getUserId());
        user.setDelFlag(CommonConstant.STATUS_DEL);
        user.setRegistryStatus(reduce.getOutType());
        TabPbMemberReduceList tabPbMemberReduceList =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(reduce, TabPbMemberReduceList.class, false);
        int flag = tabSysUserMapper.updateByPrimaryKeySelective(user);
        if (flag > 0) {
            SysUser newuser = tabSysUserMapper.selectByPrimaryKey(reduce.getUserId());
            if (newuser != null && !ObjectUtils.isEmpty(newuser.getDeptId())) {
                tabPbMemberReduceList.setDeptId(newuser.getDeptId());
                tabPbMemberReduceList.setRealName(newuser.getUsername());
                flag += reduceListMapper.insertSelective(tabPbMemberReduceList);
            }
        }
        return flag > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean restoreUser(Long userId) {
        TabPbMemberReduceList reduceList = reduceListMapper.selectByUserId(userId);
        if(reduceList==null){
            throw  new BusinessDataNotFoundException("查不到该党员减少记录");
        }
        reduceList.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(reduceList);
        reduceListMapper.updateByPrimaryKeySelective(reduceList);
        SysUser user = new SysUser().setUserId(userId).setRegistryStatus(2L).setDelFlag(CommonConstant.STATUS_NORMAL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(user);
        tabSysUserMapper.updateByPrimaryKeySelective(user);
        return true;
    }
}
