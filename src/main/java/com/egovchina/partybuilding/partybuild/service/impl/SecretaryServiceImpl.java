package com.egovchina.partybuilding.partybuild.service.impl;

import com.alibaba.fastjson.JSON;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.ResumeDTO;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeamMember;
import com.egovchina.partybuilding.partybuild.repository.TabPbDeptSecretaryMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.LeadTeamMemberService;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.ResumeVO;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author zhucan
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SecretaryServiceImpl implements SecretaryService {

    @Autowired
    private TabPbDeptSecretaryMapper tabPbDeptSecretaryMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private LeadTeamMemberService leadTeamMemberService;

    /**
     * 新增书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int addSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        TabPbDeptSecretary tabPbDeptSecretary = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, TabPbDeptSecretary.class, false);
        int flag = tabPbDeptSecretaryMapper.insertSelective(tabPbDeptSecretary);
        if (flag > 0) {
            SysUser sysUser = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, SysUser.class, true);
            tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
        return flag;
    }

    /**
     * 删除书记
     *
     * @param secretaryId
     * @return
     */
    @Override
    public int removeSecretary(Long secretaryId) {
        TabPbDeptSecretary tabPbDeptSecretary = new TabPbDeptSecretary().setDelFlag(CommonConstant.STATUS_DEL).setSecretaryId(secretaryId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbDeptSecretary);
        return tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
    }

    /**
     * 修改书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int updateSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        SysUser sysUser = new SysUser();
        Long userId = tabSysUserMapper.SelectUserIdByIDcard(secretaryMemberDTO.getIdCardNo());
        BeanUtil.copyPropertiesIgnoreNull(secretaryMemberDTO, sysUser);
        sysUser.setUserId(userId);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        TabPbDeptSecretary tabPbDeptSecretary = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, TabPbDeptSecretary.class, true);
        Long memberId = tabPbDeptSecretaryMapper.findMemberIdByLeadTeamIdAndUserId(secretaryMemberDTO.getUserId(), secretaryMemberDTO.getLeadTeamId());
        LeadTeamMemberDTO leadTeamMemberDTO = new LeadTeamMemberDTO();
        leadTeamMemberDTO.setMemberId(memberId);
        leadTeamMemberDTO.setPositiveId(secretaryMemberDTO.getNewPosition());
        leadTeamMemberDTO.setPositiveName(secretaryMemberDTO.getNewPositionName());
        leadTeamMemberDTO.setTenureBegin(secretaryMemberDTO.getServeTime());
        leadTeamMemberDTO.setRank(secretaryMemberDTO.getRank());
        leadTeamMemberDTO.setUserId(secretaryMemberDTO.getUserId());
        leadTeamMemberDTO.setOrgId(secretaryMemberDTO.getDeptId());
        leadTeamMemberDTO.setAvatar2(secretaryMemberDTO.getAvatar2());
        leadTeamMemberDTO.setAvatar(secretaryMemberDTO.getAvatar());
        leadTeamMemberService.updateLeadTeamMember(leadTeamMemberDTO);
        //修改简历
        List<ResumeDTO> resumeDTOs = secretaryMemberDTO.getResumes();
        if (CollectionUtil.isNotEmpty(resumeDTOs)) {
            String resumes = JSON.toJSONString(resumeDTOs);
            tabPbDeptSecretary.setResume(resumes);
        }
        tabPbDeptSecretary.setJoinWorkerTime(secretaryMemberDTO.getWorkDate());
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
        SecretaryMemberVO secretaryMemberVO = tabPbDeptSecretaryMapper.selectSecretaryVOBySecretaryId(secretaryId);
        if (StringUtils.isNotEmpty(secretaryMemberVO.getResume())) {
            List<ResumeVO> resumeVOS = JSON.parseArray(secretaryMemberVO.getResume(), ResumeVO.class);
            secretaryMemberVO.setResumes(resumeVOS);
        }
        return secretaryMemberVO;
    }

    /**
     * 列表查询书记
     *
     * @param secretaryMemberQueryBean
     * @return
     */
    @Override
    public List<SecretaryMemberVO> selectSecretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbDeptSecretaryMapper.selectSecretaryVOList(secretaryMemberQueryBean);
    }

    @Override
    public TabPbDeptSecretary selectOldSecretaryInfoByUserIdAndLeadTeamId(Long userId, Long leadTeamId) {
        return tabPbDeptSecretaryMapper.selectSecretaryByUserIdAndLeadTeamId(userId, leadTeamId);
    }

    @Override
    public int insertTabPbDeptSecretary(TabPbDeptSecretary tabPbDeptSecretary) {
        return tabPbDeptSecretaryMapper.insertSelective(tabPbDeptSecretary);
    }

    @Override
    public int logicDeleteTabPbSecretary(TabPbDeptSecretary secretary) {
        return tabPbDeptSecretaryMapper.logicDeleteTabPbSecretary(secretary);
    }

    @Override
    public int updateTabPbDeptSecretarySelective(TabPbDeptSecretary tabPbDeptSecretary) {
        return tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
    }

}
