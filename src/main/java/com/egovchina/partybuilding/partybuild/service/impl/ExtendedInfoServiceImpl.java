package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
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
import com.egovchina.partybuilding.partybuild.vo.SecretariesPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SysUserVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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

    //停止党籍 党籍状态
    private final Long STOPPARTYMEMBERSHIP = 59328L;

    // 停止党籍减少方式
    private final Long STOPPARTYREDUCTION = 59591L;

    //出党 减少方式
    private final Long OUTOFTHEPARTY = 59590L;

    //出党 党籍状态
    private final Long PARTYMEMBERSHIPSTATUS = 59329L;

    //死亡 减少方式
    private final Long DEATHREDUCTIONMETHOD = 59592L;

    //死亡 党籍状态
    private final Long DEATHPARTYSTATUS = 59327L;

    //其他方式减少 党籍状态
    private final Long OTHERWAYSTOREDUCEPARTYSTATUS = 59585L;

    //开除党籍 出党方式
    private final Long DISMISSALOFPARTYMEMBERSHIP = 30019L;

    //正式党员 党籍状态
    private final Long OFFICIALPARTYMEMBER = 59325L;

    @Override
    public PartyMemberVO selectPartyMemberVOById(Long userId) {
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
    public int deleteByUserId(DeletePartyMemberDTO reduce) {
        SysUser user =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(reduce, SysUser.class, false);
        //设置无效状态
        user.setEblFlag(CommonConstant.STATUS_NOEBL);
        //停止党籍
        if (STOPPARTYREDUCTION.equals(reduce.getOutType())) {
            user.setRegistryStatus(STOPPARTYMEMBERSHIP);
            //出党
        } else if (OUTOFTHEPARTY.equals(reduce.getOutType())) {
            user.setRegistryStatus(PARTYMEMBERSHIPSTATUS);
            //死亡
        } else if (DEATHREDUCTIONMETHOD.equals(reduce.getOutType())) {
            user.setRegistryStatus(DEATHPARTYSTATUS);
        } else {
            //其他原因
            user.setRegistryStatus(OTHERWAYSTOREDUCEPARTYSTATUS);
        }
        int flag = tabSysUserMapper.updateByPrimaryKeySelective(user);
        SysUser newuser = null;
        if (flag > 0) {
            TabPbMemberReduceList tabPbMemberReduceList =
                    BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(reduce, TabPbMemberReduceList.class, false);
            //获取用户名+组织id
            newuser = tabSysUserMapper.selectByPrimaryKey(reduce.getUserId());
            if (newuser != null) {
                tabPbMemberReduceList.setDeptId(newuser.getDeptId());
                tabPbMemberReduceList.setRealName(newuser.getRealname());
                tabPbMemberReduceList.setReduceTime(new Date());
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbMemberReduceList);
                flag += reduceListMapper.insertSelective(tabPbMemberReduceList);
            }
            //添加一条党籍
            MembershipDTO membershipDTO = new MembershipDTO();
            membershipDTO.setUserId(reduce.getUserId()).setIdentityType(newuser.getIdentityType()).setType(user.getRegistryStatus());
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
        if (DISMISSALOFPARTYMEMBERSHIP.equals(reduceList.getQuitType())) {
            throw new BusinessDataNotFoundException("该党员已被开除党籍,无法恢复");
        }
        //设置无效状态
        reduceList.setEblFlag(CommonConstant.STATUS_NOEBL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(reduceList);
        //更新历史党员表
        int num = reduceListMapper.updateByPrimaryKeySelective(reduceList);
        //查询查询identity_type 只能查询党员状态为无效的
        Long identityType = tabSysUserMapper.selectUserByIdFindIdentity(userId);
        if (identityType == null) {
            throw new BusinessDataNotFoundException("该党员未被删除或者人员类别为空");
        }
        MembershipDTO membershipDTO = new MembershipDTO();
        membershipDTO.setUserId(userId).setIdentityType(identityType).setType(OFFICIALPARTYMEMBER);
        //新增党籍
        num += partyMembershipServiceImpl.insertMembershipDTO(membershipDTO);
        //党员状态设置有效
        SysUser user = new SysUser().setUserId(userId).setRegistryStatus(OFFICIALPARTYMEMBER).setEblFlag(CommonConstant.STATUS_EBL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(user);
        //更新党员表
        num += tabSysUserMapper.updateByPrimaryKeySelective(user);
        return num;
    }

}
