package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.repository.*;
import com.egovchina.partybuilding.partybuild.service.LeadTeamMemberService;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;


/**
 * @Author GuanYingxin
 **/
@Service("leadTeamMemberService")
public class LeadTeamMemberServiceImpl implements LeadTeamMemberService {

    @Autowired
    private TabPbLeadTeamMemberMapper tabPbLeadTeamMemberMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbLeadTeamMapper tabPbLeadTeamMapper;

    @Autowired
    private TabPbPositivesMapper tabPbPositivesMapper;

    @Transactional
    @Override
    public int logicDeleteLeadTeamMemberById(Long memberId) {
        TabPbLeadTeamMember dbMember = tabPbLeadTeamMemberMapper.selectByPrimaryKey(memberId);
        if (dbMember == null) {
            throw new BusinessDataNotFoundException("班子成员数据不存在");
        }
        TabPbLeadTeamMember delete = new TabPbLeadTeamMember();
        delete.setMemberId(memberId);
        delete.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(delete);
        int judgment = tabPbLeadTeamMemberMapper.updateByPrimaryKeySelective(delete);
        if (judgment > 0) {
            tabPbLeadTeamMapper.correctTheNumberOfTeamsAccordingToTheTeamId(dbMember.getLeadTeamId());
        }
        return judgment;
    }

    @Override
    public List<LeadTeamMemberListVO> selectLeadTeamMemberVOListByLeadTeamId(Long leadTeamId, Page page) {
        PageHelper.startPage(page);
        return tabPbLeadTeamMemberMapper.selectTeamMemberVOListByTeamId(leadTeamId);
    }

    @Override
    public LeadTeamMemberVO selectLeadTeamMemberVOById(Long memberId) {
        LeadTeamMemberVO leadTeamMemberVO = tabPbLeadTeamMemberMapper.selectLeadTeamMemberVOById(memberId);
        if (leadTeamMemberVO == null) {
            throw new BusinessDataNotFoundException("您要查询的班子成员信息不存在");
        }
        return tabPbLeadTeamMemberMapper.selectLeadTeamMemberVOById(memberId);
    }

    @Override
    public List<LeadTeamMemberVO> selectLeadTeamMemberVOListByCondition(LeadTeamMemberQueryBean queryBean, Page page) {
        PageHelper.startPage(page);
        List<LeadTeamMemberVO> list = tabPbLeadTeamMemberMapper.selectLeadTeamMemberVOListByCondition(queryBean);
        return list;
    }

    @Transactional
    @Override
    public int insertLeadTeamMember(LeadTeamMemberDTO leadTeamMemberDTO) {
        //数据验证
        dataValidation(leadTeamMemberDTO);
        TabPbLeadTeamMember tabPbLeadTeamMember =
                generateTargetCopyPropertiesAndPaddingBaseField(leadTeamMemberDTO, TabPbLeadTeamMember.class, false);
        int judgment = tabPbLeadTeamMemberMapper.insertSelective(tabPbLeadTeamMember);
        if (judgment > 0) {
            tabPbLeadTeamMapper.correctTheNumberOfTeamsAccordingToTheTeamId(leadTeamMemberDTO.getLeadTeamId());
            //新增班子成员的头像时一同修改党员表的头像
            if (StringUtils.isNotEmpty(leadTeamMemberDTO.getAvatar())) {
                judgment += tabSysUserMapper.updateAvatarByUserId(leadTeamMemberDTO.getUserId(), leadTeamMemberDTO.getAvatar());
            }
            //修改职务信息
            modifyPosition(leadTeamMemberDTO);
        }
        return judgment;
    }

    @Transactional
    @Override
    public int updateLeadTeamMember(LeadTeamMemberDTO leadTeamMemberDTO) {
        LeadTeamMemberVO leadTeamMemberVO = tabPbLeadTeamMemberMapper.selectLeadTeamMemberVOById(leadTeamMemberDTO.getMemberId());
        if (leadTeamMemberVO == null) {
            throw new BusinessDataNotFoundException("您要修改的班子成员信息不存在");
        }
        TabPbLeadTeamMember tabPbLeadTeamMember =
                generateTargetCopyPropertiesAndPaddingBaseField(leadTeamMemberDTO, TabPbLeadTeamMember.class, true);

        int judgment = tabPbLeadTeamMemberMapper.updateByPrimaryKeySelective(tabPbLeadTeamMember);
        if (judgment > 0) {
            //更新用户头像
            if (StringUtils.isNotEmpty(leadTeamMemberDTO.getAvatar())) {
                judgment += tabSysUserMapper.updateAvatarByUserId(leadTeamMemberDTO.getUserId(), leadTeamMemberDTO.getAvatar());
            }
            modifyPosition(leadTeamMemberDTO);
        }
        return judgment;
    }

    @Override
    public List<CommunityPartTimeMemberVO> selectCommunityPartTimeMemberVOListByCondition(CommunityPartTimeMemberQueryBean queryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbLeadTeamMemberMapper.selectCommunityPartTimeMemberVOListByCondition(queryBean);
    }

    /**
     * 列表查询书记
     *
     * @param partyMemberSecretaryMemberQueryBean
     * @return
     */
    @Override
    public List<PartySecretarysVO> selectSecretaryList(PartyMemberSecretaryMemberQueryBean partyMemberSecretaryMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbLeadTeamMemberMapper.selectSecretaryVOList(partyMemberSecretaryMemberQueryBean);
    }

    @Override
    public Long getLeadTeamIdByCurrent(Long orgId) {
        return tabPbLeadTeamMapper.selectLeadTeamIdByOrgId(orgId);
    }

    /**
     * 数据校验
     *
     * @param leadTeamMemberDTO
     */
    private void dataValidation(LeadTeamMemberDTO leadTeamMemberDTO) {
        SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(leadTeamMemberDTO.getUserId());
        if (sysUser == null) {
            throw new BusinessDataNotFoundException("用户不存在");
        }
        LeadTeamVO leadTeamVO = tabPbLeadTeamMapper.selectLeadTeamVOById(leadTeamMemberDTO.getLeadTeamId());
        if (leadTeamVO == null) {
            throw new BusinessDataNotFoundException("领导班子不存在");
        }
        boolean exists = Optional.ofNullable(tabPbLeadTeamMemberMapper
                .memberAlreadyExistsInTheTeam(leadTeamMemberDTO.getLeadTeamId(), leadTeamMemberDTO.getUserId()))
                .orElse(false);
        if (exists) {
            throw new BusinessDataCheckFailException("该成员已存在");
        }
    }

    /**
     * 维护职务信息
     *
     * @param leadTeamMemberDTO 班子成员DTO
     */
    private void modifyPosition(LeadTeamMemberDTO leadTeamMemberDTO) {
        //党内职务
        final Long Party_Position = 59421L;
        //先移除同一个班子里已有的职务
        TabPbPositives delete = new TabPbPositives();
        delete.setPositiveOrgId(leadTeamMemberDTO.getOrgId());
        delete.setUserId(leadTeamMemberDTO.getUserId());
        delete.setBusinessFrom(Byte.parseByte("1")); //领导班子
        delete.setBusinessId(leadTeamMemberDTO.getLeadTeamId());
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(delete);
        tabPbPositivesMapper.logicDeletePositiveByCondition(delete);

        TabPbPositives tabPbPositives =
                generateTargetCopyPropertiesAndPaddingBaseField(delete, TabPbPositives.class, false);
        tabPbPositives.setPositiveName(leadTeamMemberDTO.getPositiveId());
        tabPbPositives.setPositiveType(Party_Position); // 党内职务
        tabPbPositives.setPositiveStart(leadTeamMemberDTO.getTenureBegin());
        tabPbPositives.setPositiveFinished(leadTeamMemberDTO.getTenureLeave());
        tabPbPositives.setDescription(leadTeamMemberDTO.getDescription());
        tabPbPositives.setPositiveLevel(leadTeamMemberDTO.getRank());
        tabPbPositivesMapper.insertSelective(tabPbPositives);
    }

}
