package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
import com.egovchina.partybuilding.partybuild.entity.TabPbFamily;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositives;
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

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

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
        Long userId = secretaryMemberDTO.getUserId();
        if (userId==null) {
            SysUser sysUser = generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO,SysUser.class, false);
            tabSysUserMapper.insertSelective(sysUser);
            userId = sysUser.getUserId();
        }
        TabPbDeptSecretary tabPbDeptSecretaryinsert = generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, TabPbDeptSecretary.class, false);
        int flag = tabPbDeptSecretaryMapper.insertSelective(tabPbDeptSecretaryinsert);
        if (flag > 0) {
            List<TabPbFamily> familyList = secretaryMemberDTO.getFamilyList();
            final Long finalUserId = userId;
            if (CollectionUtil.isNotEmpty(familyList)) {
                List<TabPbFamily> familys = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(familyList, TabPbFamily.class, family -> family.setUserId(finalUserId), false);
                tabPbFamilyMapper.batchInsertFamilyList(familys);
            }
            List<TabPbPositives> positivesList = secretaryMemberDTO.getPositivesList();
            if (CollectionUtil.isNotEmpty(positivesList)) {
                List<TabPbPositives> positives = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(positivesList, TabPbPositives.class, positive -> positive.setUserId(finalUserId), false);
                tabPbPositivesMapper.batchInsertPositivesList(positives);
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
        if(CollectionUtil.isNotEmpty(familyList)){
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
     * @param secretaryId
     * @return
     */
    @Override
    public SecretaryMemberVO selectSecretaryBySecretaryId(Long secretaryId) {
        return tabPbDeptSecretaryMapper.selectSecretaryVOBySecretaryId(secretaryId);
    }

    /**
     * 列表查询书记
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
     * @param secretaryId
     * @return
     */
    @Override
    public ReturnEntity deleteSecretary(Long secretaryId) {
        TabPbDeptSecretary tabPbDeptSecretary = new TabPbDeptSecretary();
        tabPbDeptSecretary.setSecretaryId(secretaryId);
        tabPbDeptSecretary.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbDeptSecretary);
        int flag = tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
        return ReturnUtil.buildReturn(flag);
    }

}
