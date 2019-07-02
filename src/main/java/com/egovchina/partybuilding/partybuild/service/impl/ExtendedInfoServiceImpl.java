package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
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

import java.util.Date;
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
    private static final Long STOP_PARTY_MEMBERSHIP = 59328L;

    // 停止党籍减少方式
    private static final Long STOP_PARTY_REDUCTION = 59591L;

    //出党 减少方式
    private static final Long OUT_OF_THE_PARTY = 59590L;

    //出党 党籍状态
    private static final Long PARTY_MEMBERSHIP_STATUS = 59329L;

    //死亡 减少方式
    private static final Long DEATH_REDUCTION_METHOD = 59592L;

    //死亡 党籍状态
    private static final Long DEATH_PARTY_STATUS = 59327L;

    //其他方式减少 党籍状态
    private static final Long OTHER_WAYS_TO_REDUCE_PARTY_STATUS = 59585L;

    //开除党籍 出党方式
    private static final Long DISMISSAL_OF_PARTY_MEMBERSHIP = 30019L;

    //正式党员 党籍状态
    private static final Long OFFICIAL_PARTY_MEMBER = 59325L;

    //出党方式为出国出境
    private static final Long GOINGABROAD = 59624L;

    //党籍其他处理
    private static final Long OTHERTREATMENTOFPARTYMEMBERSHIP = 59579L;

    //停止党籍处理
    private static final Long STOPPARTYPROCESSING = 59583L;

    //党籍死亡处理类型
    private static final Long PARTYDEATHTREATMENT = 59581L;

    //党籍出党处理类型
    private static final Long PARTYMEMBERSHIPTYPE = 59582L;

    //党籍处理恢复
    private static final Long REGISTRY_STATUS_RESTORE = 59580L;

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
        //用于获取党籍处理类型
        Long[] type = new Long[1];
        //设置用户党籍状态,党籍处理
        setPartyStatus(type, reduce.getOutType(), user);
        int flag = tabSysUserMapper.updateByPrimaryKeySelective(user);
        if (flag > 0) {
            //如果出党方式为出国出境
            if (reduce.getWhetherThisClass() && GOINGABROAD.equals(reduce.getQuitType())) {
                TabPbAbroad tabPbAbroad = new TabPbAbroad().setUserId(sysUser.getUserId()).setOrgId(sysUser.getDeptId()).setAbroadDate(reduce.getReduceTime());
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbAbroad);
                flag += tabPbAbroadMapper.insertSelective(tabPbAbroad);
                //关联出国出境
                reduce.setAbroadId(tabPbAbroad.getAbroadId());
            }
            TabPbMemberReduceList tabPbMemberReduceList =
                    BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(reduce, TabPbMemberReduceList.class, false);
            //新增历史党员
            tabPbMemberReduceList.setDeptId(sysUser.getDeptId());
            tabPbMemberReduceList.setRealName(sysUser.getRealname());
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbMemberReduceList);
            flag += reduceListMapper.insertSelective(tabPbMemberReduceList);

            //判断是否为党小组组长
            if (tabPbPartyGroupMemberMapper.isLeaderByUserId(sysUser.getUserId())) {
                throw new BusinessDataCheckFailException("请先移除该党员党小组组长的身份");
            }
            // 移出党小组
            TabPbPartyGroupMember tabPbPartyGroupMember = new TabPbPartyGroupMember().setUserId(sysUser.getUserId()).setDelFlag(1);
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbPartyGroupMember);
            flag += tabPbPartyGroupMemberMapper.updateByPrimaryKeySelective(tabPbPartyGroupMember);
            //添加一条党籍
            checkIsOpeartionMeberShip(reduce.getUserId(), type[0]);
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int restoreUser(Long reduce_id, Date restoreTime) {
        //修改党员减少表
        TabPbMemberReduceList reduceList = reduceListMapper.selectByUserId(reduce_id);
        if (reduceList == null) {
            throw new BusinessDataNotFoundException("查不到该党员减少记录");
        }
        //如果为出国出境 特别处理 针对已经回国的 不再继续操作
        if (GOINGABROAD.equals(reduceList.getQuitType())) {
            //如果已回国直接返回,不进行恢复操作
            if (reduceList.getRecoveryTime() != null) {
                return 1;
            }
        }
        //若是开除党籍无法恢复
        if (DISMISSAL_OF_PARTY_MEMBERSHIP.equals(reduceList.getQuitType())) {
            throw new BusinessDataNotFoundException("该党员已被开除党籍,无法恢复");
        }
        //设置无效状态
        reduceList.setEblFlag(CommonConstant.STATUS_NOEBL);
        //设置恢复时间
        reduceList.setRecoveryTime(restoreTime == null ? new Date() : restoreTime);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(reduceList);
        //更新历史党员表
        int num = reduceListMapper.updateByPrimaryKeySelective(reduceList);
        //如果出党方式为出国出境
        if (GOINGABROAD.equals(reduceList.getQuitType())) {
            //设置其回国
            TabPbAbroad tabPbAbroad = new TabPbAbroad();
            Long abordId = reduceList.getAbroadId();
            if (abordId != null) {
                tabPbAbroad = tabPbAbroadMapper.selectByPrimaryKey(abordId);
                if (tabPbAbroad != null) {
                    if (tabPbAbroad.getReturnDate() == null) {
                        tabPbAbroad.setReturnDate(new Date());
                        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbAbroad);
                        tabPbAbroadMapper.updateByPrimaryKeySelective(tabPbAbroad);
                    }
                }
            }
        }
        //添加党籍记录
        checkIsOpeartionMeberShip(reduceList.getUserId(), REGISTRY_STATUS_RESTORE);
        //党员状态设置有效
        SysUser user = new SysUser().setUserId(reduceList.getUserId()).setRegistryStatus(OFFICIAL_PARTY_MEMBER).setEblFlag(CommonConstant.STATUS_EBL);
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
        //用于获取党籍处理类型
        Long[] type = new Long[1];
        //设置用户党籍状态,党籍处理
        setPartyStatus(type, updateHistoryDTO.getOutType(), user);
        //更改用户党籍状态
        int flag = tabSysUserMapper.updateByPrimaryKeySelective(user);
        TabPbMemberReduceList tabPbMemberReduceList =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(updateHistoryDTO, TabPbMemberReduceList.class, true);
        //如果曾经为出国出境 现在不是出国出境
        if (!GOINGABROAD.equals(updateHistoryDTO.getQuitType()) && GOINGABROAD.equals(tabPbMemberReduceList1.getQuitType())) {
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
        //修改历史党员
        tabPbMemberReduceList.setDeptId(tabPbMemberReduceList1.getDeptId());
        tabPbMemberReduceList.setRealName(tabPbMemberReduceList1.getRealName());
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbMemberReduceList);
        flag += reduceListMapper.updateByPrimaryKeySelectiveCondition(tabPbMemberReduceList);
        //党籍处理
        checkIsOpeartionMeberShip(tabPbMemberReduceList1.getUserId(), type[0]);
        return flag;
    }

    /**
     * 党籍处理和减少方式转换
     *
     * @param types   用于党籍处理值传递
     * @param outType 减少方式
     * @param user    对象
     */
    public void setPartyStatus(Long[] types, Long outType, SysUser user) {
        boolean isCheckUser = true;
        if (user == null) {
            isCheckUser = false;
        }
        //停止党籍
        if (STOP_PARTY_REDUCTION.equals(outType)) {
            if (isCheckUser) {
                user.setRegistryStatus(STOP_PARTY_MEMBERSHIP);
            }
            //设置党籍停止处理类型
            types[0] = STOPPARTYPROCESSING;
            //出党
        } else if (OUT_OF_THE_PARTY.equals(outType)) {
            if (isCheckUser) {
                user.setRegistryStatus(PARTY_MEMBERSHIP_STATUS);
            }
            //设置党籍出党处理类型
            types[0] = PARTYMEMBERSHIPTYPE;
            //死亡
        } else if (DEATH_REDUCTION_METHOD.equals(outType)) {
            if (isCheckUser) {
                user.setRegistryStatus(DEATH_PARTY_STATUS);
            }
            //设置党籍死亡处理类型
            types[0] = PARTYDEATHTREATMENT;
        } else {
            //其他原因
            if (isCheckUser) {
                user.setRegistryStatus(OTHER_WAYS_TO_REDUCE_PARTY_STATUS);
            }
            //设置党籍其他处理类型
            types[0] = OTHERTREATMENTOFPARTYMEMBERSHIP;
        }
    }

    /**
     * @param userId 用户id
     * @param type   党籍处理
     */
    //TODO 目前需求暂未涉及修改党籍,以后需求更改
    public void updatePartyMembership(Long userId, Long type) {
        //查询identity_type
        Long identityType = tabSysUserMapper.selectUserByIdFindIdentity(userId);
        MembershipDTO membershipDTO = new MembershipDTO();
        membershipDTO.setUserId(userId).setIdentityType(identityType).setType(type);
        //新增党籍
        partyMembershipServiceImpl.insertMembershipDTO(membershipDTO);
    }

    /**
     * 用于判断是否操作党籍
     *
     * @param userId 用户id
     * @param type   党籍处理
     */
    public void checkIsOpeartionMeberShip(Long userId, Long type) {
        //判断党籍是否发生改变
        List<MembershipVO> membershipVOListByCondition = partyMembershipServiceImpl.getMembershipVOListByCondition(userId, new Page());
        if (membershipVOListByCondition != null && membershipVOListByCondition.size() > 0) {
            //改变就新增党籍信息
            if (!membershipVOListByCondition.get(0).getType().equals(type)) {
                updatePartyMembership(userId, type);
            }
        } else {
            //该情况出现可能很小,预防数据问题
            //不考虑membershipVOListByCondition为null,不存在null
            updatePartyMembership(userId, type);
        }
    }
}