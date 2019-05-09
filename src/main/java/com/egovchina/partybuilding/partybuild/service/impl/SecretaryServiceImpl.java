package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.FamilyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.PositivesDTO;
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
import com.egovchina.partybuilding.partybuild.vo.SecretarysVO;
import com.github.pagehelper.PageHelper;
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
     *
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
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int insertSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        Long userId = secretaryMemberDTO.getUserId();
        if (userId == null) {
            SysUser sysUser = generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, SysUser.class, false);
            sysUser.setUsername(secretaryMemberDTO.getRealname());
            tabSysUserMapper.insertSelective(sysUser);
            userId = sysUser.getUserId();
            secretaryMemberDTO.setUserId(userId);
        }
        TabPbDeptSecretary tabPbDeptSecretaryinsert = generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, TabPbDeptSecretary.class, false);
        int flag = tabPbDeptSecretaryMapper.insertSelective(tabPbDeptSecretaryinsert);
        if (flag > 0) {
            List<FamilyMemberDTO> familyList = secretaryMemberDTO.getFamilys();
            final Long finalUserId = userId;
            if (CollectionUtil.isNotEmpty(familyList)) {
                List<TabPbFamily> familys = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(familyList, TabPbFamily.class, family -> family.setUserId(finalUserId), false);
                tabPbFamilyMapper.batchInsertFamilyList(familys);
            }
            List<PositivesDTO> positivesList = secretaryMemberDTO.getPositivesVOs();
            if (CollectionUtil.isNotEmpty(positivesList)) {
                List<TabPbPositives> positives = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(positivesList, TabPbPositives.class, positive -> positive.setUserId(finalUserId), false);
                tabPbPositivesMapper.batchInsertPositivesList(positives);
            }
        }
        return flag;
    }

    /**
     * 修改书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int updateSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        TabPbDeptSecretary tabPbDeptSecretaryinsert = new TabPbDeptSecretary();
        BeanUtil.copyPropertiesIgnoreNull(secretaryMemberDTO, tabPbDeptSecretaryinsert);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbDeptSecretaryinsert);
        int flag = tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretaryinsert);
        List<FamilyMemberDTO> familyMemberDTOS = secretaryMemberDTO.getFamilys();
        List<PositivesDTO> positivesDTOS = secretaryMemberDTO.getPositivesVOs();
        //修改或者添加家庭成员
        this.updateOrAddFamily(familyMemberDTOS, secretaryMemberDTO);
        //修改或者添加职务
        this.updateOrAddPositives(positivesDTOS, secretaryMemberDTO);
        return flag;
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
    public List<SecretarysVO> selectSecretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbDeptSecretaryMapper.selectSecretaryVOList(secretaryMemberQueryBean);
    }

    /**
     * 删除书记
     *
     * @param secretaryId
     * @return
     */
    @Override
    public int deleteSecretary(Long secretaryId) {
        TabPbDeptSecretary tabPbDeptSecretary = new TabPbDeptSecretary();
        tabPbDeptSecretary.setSecretaryId(secretaryId);
        tabPbDeptSecretary.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbDeptSecretary);
        return tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
    }

    /**
     * 修改或者添加家庭成员
     *
     * @param familyMemberDTOS
     * @param secretaryMemberDTO
     * @return
     */
    private void updateOrAddFamily(List<FamilyMemberDTO> familyMemberDTOS, SecretaryMemberDTO secretaryMemberDTO) {
        if (CollectionUtil.isNotEmpty(familyMemberDTOS)) {
            for (FamilyMemberDTO familyDTO : familyMemberDTOS) {
                if (familyDTO.getRelationId() != null) {
                    TabPbFamily tabPbFamily = new TabPbFamily();
                    BeanUtil.copyPropertiesIgnoreNull(familyDTO, tabPbFamily);
                    tabPbFamilyMapper.updateByPrimaryKeySelective(tabPbFamily);
                } else {
                    TabPbFamily tabPbFamily = new TabPbFamily();
                    BeanUtil.copyPropertiesIgnoreNull(familyDTO, tabPbFamily);
                    tabPbFamily.setUserId(secretaryMemberDTO.getUserId());
                    tabPbFamilyMapper.insertSelective(tabPbFamily);
                }
            }
        }
    }

    /**
     * 添加或者修改书记
     *
     * @param positivesDTOS
     * @param secretaryMemberDTO
     */
    private void updateOrAddPositives(List<PositivesDTO> positivesDTOS, SecretaryMemberDTO secretaryMemberDTO) {
        if (CollectionUtil.isNotEmpty(secretaryMemberDTO.getPositivesVOs())) {
            for (PositivesDTO positivesDTO : positivesDTOS) {
                if (positivesDTO.getPositiveId() != null) {
                    TabPbPositives tabPbPositives = new TabPbPositives();
                    BeanUtil.copyPropertiesIgnoreNull(positivesDTO, tabPbPositives);
                    tabPbPositivesMapper.updateByPrimaryKeySelective(tabPbPositives);
                } else {
                    TabPbPositives tabPbPositives = new TabPbPositives();
                    BeanUtil.copyPropertiesIgnoreNull(positivesDTO, tabPbPositives);
                    tabPbPositives.setUserId(secretaryMemberDTO.getUserId());
                    tabPbPositivesMapper.insertSelective(tabPbPositives);
                }
            }
        }
    }

}
