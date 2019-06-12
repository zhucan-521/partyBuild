package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.repository.*;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryInfoVO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SecretarysVO;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @author zhucan
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SecretaryServiceImpl implements SecretaryService {

    @Autowired
    private TabPbDeptSecretaryMapper tabPbDeptSecretaryMapper;

    @Autowired
    private TabPbLeadTeamMemberMapper tabPbLeadTeamMemberMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    /**
     * 修改书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int updateSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        SysUser sysUser=BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO,SysUser.class,true);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        TabPbDeptSecretary tabPbDeptSecretary=BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO,TabPbDeptSecretary.class,true);
        TabPbLeadTeamMember tabPbLeadTeamMember=BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO,TabPbLeadTeamMember.class,true);
        tabPbLeadTeamMember.setMemberId(secretaryMemberDTO.getSecretaryId());
        tabPbLeadTeamMemberMapper.updateByPrimaryKeySelective(tabPbLeadTeamMember);
        Long secretaryId=tabPbDeptSecretaryMapper.getSecretaryIdByUserId(secretaryMemberDTO.getUserId(),secretaryMemberDTO.getDeptId());
        tabPbDeptSecretary.setSecretaryId(secretaryId);
        return tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
    }

    /**
     * 根据书记id获取书记详情
     *
     * @param secretaryId
     * @return
     */
    @Override
    public SecretaryMemberVO selectSecretaryBySecretaryId(Long secretaryId) {
        LeadTeamMemberVO leadTeamMemberVO=tabPbLeadTeamMemberMapper.selectLeadTeamMemberVOById(secretaryId);
        if(!tabPbDeptSecretaryMapper.CheckSecretaryIsexistByUserId(leadTeamMemberVO.getUserId(),leadTeamMemberVO.getOrgId())){
            TabPbDeptSecretary tabPbDeptSecretary=BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(leadTeamMemberVO,TabPbDeptSecretary.class,false);
            tabPbDeptSecretary.setDeptId(leadTeamMemberVO.getOrgId());
            tabPbDeptSecretaryMapper.insertSelective(tabPbDeptSecretary);
        }
        SecretaryMemberVO secretaryMemberVO=tabPbDeptSecretaryMapper.selectSecretaryVOBySecretaryId(secretaryId);
        secretaryMemberVO.setSecretaryId(secretaryId);
        return secretaryMemberVO;
    }

    /**
     * 列表查询书记
     *
     * @param secretaryMemberQueryBean
     * @return
     */
    @Override
    public List<SecretarysVO> selectSecretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        if(StringUtils.isNotEmpty(secretaryMemberQueryBean.getUnitProperty())){
            secretaryMemberQueryBean.setUnitProperties(Arrays.asList(secretaryMemberQueryBean.getUnitProperty().split(",")));
        }
        return tabPbDeptSecretaryMapper.selectSecretaryVOList(secretaryMemberQueryBean);
    }

}
