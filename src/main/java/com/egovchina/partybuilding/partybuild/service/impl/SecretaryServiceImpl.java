package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.repository.TabPbDeptSecretaryMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbFamilyMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPositivesMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.SecretaryInfoVO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhucan
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SecretaryServiceImpl implements SecretaryService {

    @Autowired
    TabPbDeptSecretaryMapper tabPbDeptSecretaryMapper;

    @Autowired
    TabPbFamilyMapper tabPbFamilyMapper;

    @Autowired
    TabPbPositivesMapper tabPbPositivesMapper;

    @Autowired
    TabSysUserMapper tabSysUserMapper;


    /**
     * 根据用户id获取书记基本信息
     * @param userId
     * @return
     */
    @Override
    public SecretaryInfoVO getSecretaryInfoVOByUserId(Long userId) {
        SecretaryMemberVO secretaryMemberVO = tabPbDeptSecretaryMapper.selectSecretaryVOByUserId(userId);
        SecretaryInfoVO secretaryInfoVO = new SecretaryInfoVO();
        BeanUtil.copyPropertiesIgnoreNull(secretaryMemberVO, secretaryInfoVO);
        return secretaryInfoVO;
    }

    /**
     * 添加书记
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public ReturnEntity insertSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        Long userId;
        if (null == secretaryMemberDTO.getUserId()) {
            SysUser sysUser = new SysUser();
            BeanUtil.copyPropertiesIgnoreNull(secretaryMemberDTO, sysUser);
            PaddingBaseFieldUtil.paddingBaseFiled(sysUser);
            tabSysUserMapper.insertSelective(sysUser);
        }
        userId = secretaryMemberDTO.getUserId();
        TabPbDeptSecretary tabPbDeptSecretaryinsert = new TabPbDeptSecretary();
        BeanUtil.copyPropertiesIgnoreNull(secretaryMemberDTO, tabPbDeptSecretaryinsert);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbDeptSecretaryinsert);
        int flag = tabPbDeptSecretaryMapper.insertSelective(tabPbDeptSecretaryinsert);
        if (flag > 0) {
            List<TabPbFamily> familyList = secretaryMemberDTO.getFamilyList();
            List<TabPbPositives> positivesList = secretaryMemberDTO.getPositivesList();
            if (CollectionUtil.isNotEmpty(familyList)) {
                for (TabPbFamily tabPbFamily : familyList) {
                    tabPbFamily.setUserId(userId);
                }
                tabPbFamilyMapper.batchInsertFamilyList(familyList);
            }
            if (CollectionUtil.isNotEmpty(secretaryMemberDTO.getPositivesList())) {
                for (TabPbPositives tabPbPositives : positivesList) {
                    tabPbPositives.setUserId(userId);
                }
                tabPbPositivesMapper.batchInsertPositivesList(positivesList);
            }
        }
        return ReturnUtil.buildReturn(flag);
    }

    /**
     * 修改书记
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public ReturnEntity updateSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        TabPbDeptSecretary tabPbDeptSecretaryinsert = new TabPbDeptSecretary();
        BeanUtil.copyPropertiesIgnoreNull(secretaryMemberDTO, tabPbDeptSecretaryinsert);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbDeptSecretaryinsert);
        int flag = tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretaryinsert);
        List<TabPbFamily> familyList = secretaryMemberDTO.getFamilyList();
        List<TabPbPositives> positivesList = secretaryMemberDTO.getPositivesList();
        if (CollectionUtil.isNotEmpty(familyList)) {
            for (TabPbFamily tabPbFamily : familyList) {
                if (tabPbFamily.getRelationId() != null) {
                    tabPbFamilyMapper.updateByPrimaryKeySelective(tabPbFamily);
                } else {
                    tabPbFamily.setUserId(secretaryMemberDTO.getUserId());
                    tabPbFamilyMapper.insertSelective(tabPbFamily);
                }
            }
        }
        if (CollectionUtil.isNotEmpty(secretaryMemberDTO.getPositivesList())) {
            for (TabPbPositives tabPbPositives : positivesList) {
                if (tabPbPositives.getPositiveId() != null) {
                    tabPbPositivesMapper.updateByPrimaryKeySelective(tabPbPositives);
                } else {
                    tabPbPositivesMapper.insertSelective(tabPbPositives);
                }
            }
        }
        return ReturnUtil.buildReturn(flag);
    }

    /**
     * 根据书记id获取书记详情
     *
     * @param secretaryId
     * @return
     */
    @Override
    public SecretaryMemberVO selectSecretaryBySecretaryId(Long secretaryId) {
        return tabPbDeptSecretaryMapper.selectSecretaryVOBySecretaryId(secretaryId);
    }


    /**
     * 列表查询书记
     *
     * @param secretaryMemberQueryBean
     * @return
     */
    @Override
    public PageInfo<SecretaryMemberVO> selectSecretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        List<SecretaryMemberVO> list = tabPbDeptSecretaryMapper.selectSecretaryVOList(secretaryMemberQueryBean);
        PageInfo<SecretaryMemberVO> pageInfo = new PageInfo(list);
        return pageInfo;
    }


    /**
     * 删除书记
     *
     * @param secretaryId
     * @return
     */
    @Override
    public ReturnEntity deleteSecretary(Long secretaryId) {
        TabPbDeptSecretary tabPbDeptSecretary = new TabPbDeptSecretary();
        tabPbDeptSecretary.setSecretaryId(secretaryId);
        tabPbDeptSecretary.setDelFlag("1");
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbDeptSecretary);
        int flag = tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
        return ReturnUtil.buildReturn(flag);
    }


}
