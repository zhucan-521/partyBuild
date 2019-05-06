package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.MembershipDTO;
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

    @Autowired
    private PartyMembershipServiceImpl partyMembershipServiceImpl;

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
    public int updateByUserId(DeletePartyMemberDTO reduce) {
        SysUser user =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(reduce, SysUser.class, false);
        user.setDelFlag(CommonConstant.STATUS_DEL);
        user.setRegistryStatus(reduce.getOutType());
        int flag = tabSysUserMapper.updateByPrimaryKeySelective(user);
        SysUser newuser = null;
        if (flag > 0) {
            TabPbMemberReduceList tabPbMemberReduceList =
                    BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(reduce, TabPbMemberReduceList.class, false);
            newuser = tabSysUserMapper.selectByPrimaryKey(reduce.getUserId());
            if (newuser != null && !ObjectUtils.isEmpty(newuser.getDeptId())) {
                tabPbMemberReduceList.setDeptId(newuser.getDeptId());
                tabPbMemberReduceList.setRealName(newuser.getRealname());
                flag += reduceListMapper.insertSelective(tabPbMemberReduceList);
            }
            //添加一条党籍
            MembershipDTO membershipDTO = new MembershipDTO();
            membershipDTO.setUserId(reduce.getUserId()).setIdentityType(newuser.getIdentityType()).setType(reduce.getOutType());

            flag += partyMembershipServiceImpl.insertMembershipDTO(membershipDTO);
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int restoreUser(Long userId) {
        //修改党员减少表
        TabPbMemberReduceList reduceList = reduceListMapper.selectByUserId(userId);
        if (reduceList == null) {
            throw new BusinessDataNotFoundException("查不到该党员减少记录");
        }
        reduceList.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(reduceList);
        int num = reduceListMapper.updateByPrimaryKeySelective(reduceList);
        //查询查询identity_type
        Long identity_type = tabSysUserMapper.selectUserByIdFindIdentity(userId);
        MembershipDTO membershipDTO = new MembershipDTO();
        membershipDTO.setUserId(userId).setIdentityType(identity_type).setType(2L);
        //新增党籍
        num += partyMembershipServiceImpl.insertMembershipDTO(membershipDTO);
        SysUser user = new SysUser().setUserId(userId).setRegistryStatus(2L).setDelFlag(CommonConstant.STATUS_NORMAL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(user);
        num += tabSysUserMapper.updateByPrimaryKeySelective(user);
        return num;
    }
}
