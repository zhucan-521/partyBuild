package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.FamilyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.egovchina.partybuilding.partybuild.entity.TabPbFamily;
import com.egovchina.partybuilding.partybuild.repository.SysUserMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbFamilyMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.FamilyService;
import com.egovchina.partybuilding.partybuild.vo.FamilyMemberVO;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhucan on 2018/11/26
 */
@Service
@Transactional(rollbackFor = BusinessException.class)
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private TabPbFamilyMapper pbFamilyMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TabSysUserMapper userMapper;

    /**
     * 根据党员id加载list数据
     *
     * @param partyMemberId
     * @return
     */
    @Override
    public List<FamilyMemberVO> selectFamilyMemberList(Long partyMemberId) {
        return pbFamilyMapper.getFamilyMemberVoByUserId(partyMemberId);
    }

    /**
     * 根据主键ID查询单条记录
     *
     * @param relationId
     * @return
     */
    @Override
    public FamilyMemberVO selectFamilyMemberById(Long relationId) {
        if (!"".equals(relationId)) {
            TabPbFamily tabPbFamily = pbFamilyMapper.findById(relationId);
            FamilyMemberVO familyMemberVO = new FamilyMemberVO();
            BeanUtil.copyPropertiesIgnoreNull(tabPbFamily, familyMemberVO);
            return familyMemberVO;
        } else {
            throw new BusinessDataIncompleteException("家庭成员Id为空");
        }
    }

    /**
     * 根据主键ID删除单条记录
     *
     * @param relationId
     */
    @Override
    public int deleteFamilyMemberByPrimaryKey(Long relationId) {
        TabPbFamily tabPbFamily = pbFamilyMapper.findById(relationId);
        if (null == tabPbFamily) {
            throw new BusinessDataCheckFailException("不存在这个人");
        }
        tabPbFamily.setDelFlag("1");
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbFamily);
        int flag = pbFamilyMapper.updateByPrimaryKeySelective(tabPbFamily);
        if (flag > 0) {
            userMapper.flagDelete(tabPbFamily.getRelationUserId());
        }
        return flag;
    }

    /**
     * 保存实体
     *
     * @param
     */
    @Override
    public int addFamilyDTO(FamilyMemberDTO Family) {
        TabPbFamily tabPbFamily = new TabPbFamily();
        BeanUtil.copyPropertiesIgnoreNull(Family, tabPbFamily);
        var user = this.userMapper.selectUserByIdCardNo(tabPbFamily.getIdCardNo());
        if(user != null) {
            tabPbFamily.setRelationUserId(user.getUserId().longValue());
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbFamily);
            return pbFamilyMapper.insertSelective(tabPbFamily);
        }else {
            SysUser sysUser = new SysUser();
            BeanUtil.copyPropertiesIgnoreNull(tabPbFamily, sysUser);
            sysUser.setRealname(sysUser.getUsername());
            int retVal = userMapper.saveEntity(sysUser);
            if (retVal > 0) {
                tabPbFamily.setRelationUserId(sysUser.getUserId().longValue());
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbFamily);
                pbFamilyMapper.insertSelective(tabPbFamily);
            }
            return retVal;
        }
    }

    @Override
    public int updateByPrimaryKeySelective(FamilyMemberDTO familyMemberDTO) {
        TabPbFamily tabPbFamily = BeanUtil
                .copyPropertiesAndPaddingBaseField(
                        familyMemberDTO, TabPbFamily.class, true, true);
        pbFamilyMapper.updateByPrimaryKeySelective(tabPbFamily);
        SysUser sysUser = new SysUser();
        TabPbFamily family = pbFamilyMapper.findById(tabPbFamily.getRelationId());
        BeanUtil.copyPropertiesIgnoreNull(tabPbFamily, sysUser);
        sysUser.setUserId(family.getRelationUserId());
        if (sysUser.getUsername() != null && !"".equals(sysUser.getUsername())) {
            sysUser.setRealname(sysUser.getUsername());
        }
        return sysUserMapper.updateById(sysUser);
    }
}
