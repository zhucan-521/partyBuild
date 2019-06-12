package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.MembershipDTO;
import com.egovchina.partybuilding.partybuild.dto.UpdateHistoryDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbAbroad;
import com.egovchina.partybuilding.partybuild.entity.TabPbMemberReduceList;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyGroupMember;
import com.egovchina.partybuilding.partybuild.repository.TabPbAbroadMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbMemberReduceListMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyGroupMemberMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ExtendedInfoService;
import com.egovchina.partybuilding.partybuild.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ExtendedInfoServiceImpl implements ExtendedInfoService {

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbMemberReduceListMapper reduceListMapper;

    @Autowired
    private PartyMembershipServiceImpl partyMembershipServiceImpl;

    @Autowired
    private TabPbAbroadMapper tabPbAbroadMapper;

    @Autowired
    private TabPbPartyGroupMemberMapper tabPbPartyGroupMemberMapper;

    //停止党籍 党籍状态
    private final Long STOP_PARTY_MEMBERSHIP = 59328L;

    // 停止党籍减少方式
    private final Long STOP_PARTY_REDUCTION = 59591L;

    //出党 减少方式
    private final Long OUT_OF_THE_PARTY = 59590L;

    //出党 党籍状态
    private final Long PARTY_MEMBERSHIP_STATUS = 59329L;

    //死亡 减少方式
    private final Long DEATH_REDUCTION_METHOD = 59592L;

    //死亡 党籍状态
    private final Long DEATH_PARTY_STATUS = 59327L;

    //其他方式减少 党籍状态
    private final Long OTHER_WAYS_TO_REDUCE_PARTY_STATUS = 59585L;

    //开除党籍 出党方式
    private final Long DISMISSAL_OF_PARTY_MEMBERSHIP = 30019L;

    //正式党员 党籍状态
    private final Long OFFICIAL_PARTY_MEMBER = 59325L;

    //出党方式为出国出境
    private final Long GOINGABROAD = 59624L;

    //党籍其他处理
    private final Long OTHERTREATMENTOFPARTYMEMBERSHIP = 59579L;

    //停止党籍处理
    private final Long STOPPARTYPROCESSING = 19583L;
    //党籍死亡处理类型
    private final Long PARTYDEATHTREATMENT = 59581L;

    //党籍出党处理类型
    private final Long PARTYMEMBERSHIPTYPE = 59582L;

    @Override
    public PartyMemberDetailVO selectPartyDetailById(Long userId) {
        return tabSysUserMapper.selectDeailsByPrimaryKey(userId);
    }

    @Override
    public PartyMemberVO selectPartyMemberDetailsById(Long userId) {
        return tabSysUserMapper.selectByPrimaryKeyToAll(userId);
    }

    @Override
    public SecretariesPartyMemberVO selectSecretariesPartyMemberVO(Long userId) {
        return tabSysUserMapper.selectSecretariesPartyByPrimaryKey(userId);
    }

    @Override
    public PageInfo<SysUserVO> selectPartyByIdCardNoOrUserName(String idCardNo, String username, Page page) {
        PageHelper.startPage(page);
        List<SysUserVO> sysUsers = tabSysUserMapper.selectPartyByIdCardNoOnUserName(idCardNo, username);
        return new PageInfo<>(sysUsers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int invalidByUserId(DeletePartyMemberDTO reduce) {
        SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(reduce.getUserId());
        if (sysUser == null) {
            throw new BusinessDataNotFoundException("查不到该党员记录");
        }
        SysUser user =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(reduce, SysUser.class, false);
        //设置无效状态
        user.setEblFlag(CommonConstant.STATUS_NOEBL);
        //党籍实体
        MembershipDTO membershipDTO = new MembershipDTO();
        //设置用户党籍状态,党籍处理
        setPartyStatus(membershipDTO, reduce.getOutType(), user);
        int flag = tabSysUserMapper.updateByPrimaryKeySelective(user);
        if (flag > 0) {
            TabPbMemberReduceList tabPbMemberReduceList =
                    BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(reduce, TabPbMemberReduceList.class, false);
            //新增历史党员
            tabPbMemberReduceList.setDeptId(sysUser.getDeptId());
            tabPbMemberReduceList.setRealName(sysUser.getRealname());
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbMemberReduceList);
            flag += reduceListMapper.insertSelective(tabPbMemberReduceList);
            //如果出党方式为出国出境
            if (reduce.getWhetherThisClass() && GOINGABROAD.equals(reduce.getQuitType())) {
                TabPbAbroad tabPbAbroad = new TabPbAbroad().setUserId(sysUser.getUserId()).setOrgId(sysUser.getDeptId());
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbAbroad);
                flag += tabPbAbroadMapper.insertSelective(tabPbAbroad);
            }
            // 移出党小组
            TabPbPartyGroupMember tabPbPartyGroupMember = new TabPbPartyGroupMember().setUserId(sysUser.getUserId()).setDelFlag(1);
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbPartyGroupMember);
            flag += tabPbPartyGroupMemberMapper.updateByPrimaryKeySelective(tabPbPartyGroupMember);
            //添加一条党籍
            membershipDTO.setUserId(reduce.getUserId()).setIdentityType(sysUser.getIdentityType()).setType(user.getRegistryStatus());
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
        //若是开除党籍无法恢复
        if (DISMISSAL_OF_PARTY_MEMBERSHIP.equals(reduceList.getQuitType())) {
            throw new BusinessDataNotFoundException("该党员已被开除党籍,无法恢复");
        }
        //设置无效状态
        reduceList.setEblFlag(CommonConstant.STATUS_NOEBL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(reduceList);
        //更新历史党员表
        int num = reduceListMapper.updateByPrimaryKeySelective(reduceList);
        //查询查询identity_type 只能查询党员状态为无效并且未被删除的
        Long identityType = tabSysUserMapper.selectUserByIdFindIdentity(userId);
        if (identityType == null) {
            throw new BusinessDataNotFoundException("该党员不需要恢复或者人员类别为空");
        }
        MembershipDTO membershipDTO = new MembershipDTO();
        membershipDTO.setUserId(userId).setIdentityType(identityType).setType(OFFICIAL_PARTY_MEMBER);
        //新增党籍
        num += partyMembershipServiceImpl.insertMembershipDTO(membershipDTO);
        //党员状态设置有效
        SysUser user = new SysUser().setUserId(userId).setRegistryStatus(OFFICIAL_PARTY_MEMBER).setEblFlag(CommonConstant.STATUS_EBL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(user);
        //更新党员表
        num += tabSysUserMapper.updateByPrimaryKeySelective(user);
        return num;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByUserId(Long userId) {
        int flag = 0;
        //设置有效状态,删除状态
        TabPbMemberReduceList reduceList = reduceListMapper.selectByUserId(userId);
        if (reduceList == null) {
            throw new BusinessDataNotFoundException("查不到该党员减少记录");
        }
        reduceList.setEblFlag(CommonConstant.STATUS_NOEBL);
        reduceList.setDelFlag(CommonConstant.STATUS_DEL);
        //基本字段维护
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(reduceList);
        //删除历史记录
        flag += reduceListMapper.updateByPrimaryKeySelective(reduceList);
        //TODO 暂时需求设置删除历史党员 删除出国出境 删除用户
        if (flag > 0) {
            //删除出国出境信息
            Long abroadId = tabPbAbroadMapper.findAbroadIdByUserId(userId);
            if (abroadId != null) {
                TabPbAbroad tabPbAbroad = new TabPbAbroad();
                tabPbAbroad.setAbroadId(abroadId);
                tabPbAbroad.setDelFlag(CommonConstant.STATUS_DEL);
                PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbAbroad);
                flag += tabPbAbroadMapper.updateByPrimaryKeySelective(tabPbAbroad);
            }
            //删除用户信息
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysUser.setDelFlag(CommonConstant.STATUS_DEL);
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(sysUser);
            flag += tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
        return flag;
    }

    @Override
    public HistoryPartyVO selectHistoryPartyVO(Long userId) {
        return reduceListMapper.selectHistoryPartyVOByUserId(userId);
    }

    @Override
    public int updateHistoryParty(UpdateHistoryDTO updateHistoryDTO) {
        //查询现在该历史党员修改之前的信息
        TabPbMemberReduceList tabPbMemberReduceList1 = reduceListMapper.selectByPrimaryKey(updateHistoryDTO.getMemberReduceId());
        if (tabPbMemberReduceList1 == null) {
            throw new BusinessDataNotFoundException("找不到该党员的历史记录");
        }
        SysUser user =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(updateHistoryDTO, SysUser.class, false);
        //设置无效状态
        user.setEblFlag(CommonConstant.STATUS_NOEBL);
        //党籍实体
        MembershipDTO membershipDTO = new MembershipDTO();
        //设置用户党籍状态,党籍处理
        setPartyStatus(membershipDTO, updateHistoryDTO.getOutType(), user);
        //更改用户党籍状态
        int flag = tabSysUserMapper.updateByPrimaryKeySelective(user);
        TabPbMemberReduceList tabPbMemberReduceList =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(updateHistoryDTO, TabPbMemberReduceList.class, true);
        //如果曾经为出国出境 现在不是出国出境
        if (GOINGABROAD.equals(updateHistoryDTO.getQuitType()) && !GOINGABROAD.equals(tabPbMemberReduceList.getQuitType())) {
            //删除出国出境信息
            Long abroadId = tabPbAbroadMapper.findAbroadIdByUserId(updateHistoryDTO.getUserId());
            if (abroadId != null) {
                TabPbAbroad tabPbAbroad = new TabPbAbroad();
                tabPbAbroad.setAbroadId(abroadId);
                tabPbAbroad.setDelFlag(CommonConstant.STATUS_DEL);
                PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbAbroad);
                flag += tabPbAbroadMapper.updateByPrimaryKeySelective(tabPbAbroad);

            }
        }
        //维护出党方式,避免其他未赋值的未修改该值
        if (updateHistoryDTO.getQuitType() == null || "".equals(updateHistoryDTO.getQuitType())) {
            tabPbMemberReduceList.setQuitType(null);
        }
        tabPbMemberReduceList.setDeptId(tabPbMemberReduceList1.getDeptId());
        tabPbMemberReduceList.setRealName(tabPbMemberReduceList1.getRealName());
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbMemberReduceList);
        flag += reduceListMapper.updateByPrimaryKeySelectiveCondition(tabPbMemberReduceList);
        //查询identity_type
        Long identityType = tabSysUserMapper.selectUserByIdFindIdentity(tabPbMemberReduceList1.getUserId());
        if (identityType == null) {
            throw new BusinessDataNotFoundException("该党员不需要恢复或者人员类别为空");
        }
        membershipDTO.setUserId(tabPbMemberReduceList1.getUserId()).setIdentityType(identityType).setType(user.getRegistryStatus());
        //新增党籍
        flag += partyMembershipServiceImpl.insertMembershipDTO(membershipDTO);
        return flag;

    }

    public void setPartyStatus(MembershipDTO membershipDTO, Long outType, SysUser user) {
        //停止党籍
        if (STOP_PARTY_REDUCTION.equals(outType)) {
            user.setRegistryStatus(STOP_PARTY_MEMBERSHIP);
            //设置党籍停止处理类型
            membershipDTO.setType(STOPPARTYPROCESSING);
            //出党
        } else if (OUT_OF_THE_PARTY.equals(outType)) {
            user.setRegistryStatus(PARTY_MEMBERSHIP_STATUS);
            //设置党籍出党处理类型
            membershipDTO.setType(PARTYMEMBERSHIPTYPE);
            //死亡
        } else if (DEATH_REDUCTION_METHOD.equals(outType)) {
            user.setRegistryStatus(DEATH_PARTY_STATUS);
            //设置党籍死亡处理类型
            membershipDTO.setType(PARTYDEATHTREATMENT);
        } else {
            //其他原因
            user.setRegistryStatus(OTHER_WAYS_TO_REDUCE_PARTY_STATUS);
            //设置党籍其他处理类型
            membershipDTO.setType(OTHERTREATMENTOFPARTYMEMBERSHIP);
        }
    }

}