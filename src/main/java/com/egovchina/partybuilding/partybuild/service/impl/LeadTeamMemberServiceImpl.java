package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.LeadTeamMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.repository.TabPbLeadTeamMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbLeadTeamMemberMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPositivesMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.LeadTeamMemberService;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Autowired
    private SecretaryService secretaryService;

    private static final Long IS_SECRETARY = 1L;

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
                judgment += tabSysUserMapper.updateAvatarByUserId(leadTeamMemberDTO.getUserId(), leadTeamMemberDTO.getAvatar2(), leadTeamMemberDTO.getAvatar());
            }
            //修改职务信息
            modifyPosition(leadTeamMemberDTO);
            //往书记表添加信息
            addSecretaryInfo(leadTeamMemberDTO);
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

        //修改书记表信息
        updateSecretaryInfo(leadTeamMemberDTO);

        TabPbLeadTeamMember tabPbLeadTeamMember =
                generateTargetCopyPropertiesAndPaddingBaseField(leadTeamMemberDTO, TabPbLeadTeamMember.class, true);

        int judgment = tabPbLeadTeamMemberMapper.updateByPrimaryKeySelective(tabPbLeadTeamMember);
        if (judgment > 0) {
            //更新用户头像
            if (StringUtils.isNotEmpty(leadTeamMemberDTO.getAvatar())) {
                judgment += tabSysUserMapper.updateAvatarByUserId(leadTeamMemberDTO.getUserId(), leadTeamMemberDTO.getAvatar2(), leadTeamMemberDTO.getAvatar());
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

    /**
     * 往书记表添加数据
     *
     * @param leadTeamMemberDTO 班子成员dto
     */
    private void addSecretaryInfo(LeadTeamMemberDTO leadTeamMemberDTO) {
        //如果职务是书记，则往书记表添加一条数据
        boolean judgement = tabPbLeadTeamMapper.checkIfSecretary(leadTeamMemberDTO.getPositiveId());
        if (!judgement) {
            return;
        }
        TabPbDeptSecretary oldSecretary = secretaryService.selectOldSecretaryInfoByUserIdAndLeadTeamId(leadTeamMemberDTO.getUserId(), leadTeamMemberDTO.getLeadTeamId());
        TabPbDeptSecretary tabPbDeptSecretary = new TabPbDeptSecretary();
        if (Objects.nonNull(oldSecretary)) {
            tabPbDeptSecretary.setOldPosition(oldSecretary.getNewPosition());
        }
        tabPbDeptSecretary.setUserId(leadTeamMemberDTO.getUserId())
                .setLeadTeamId(leadTeamMemberDTO.getLeadTeamId())
                .setServeTime(leadTeamMemberDTO.getTenureBegin())
                .setWhetherMember(IS_SECRETARY)
                .setPostive(leadTeamMemberDTO.getPositiveName())
                .setNewPosition(leadTeamMemberDTO.getPositiveId())
                .setAppointmentTime(leadTeamMemberDTO.getTenureLeave())
                .setWhetherMember((long) leadTeamMemberDTO.getAsCommitteeMember());
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbDeptSecretary);
        secretaryService.insertTabPbDeptSecretary(tabPbDeptSecretary);
    }

    /**
     * 更新书记表信息
     *
     * @param leadTeamMemberDTO 班子成员dto
     */
    private void updateSecretaryInfo(LeadTeamMemberDTO leadTeamMemberDTO) {
        //如果该成员原来是书记但是现在不是书记了，则逻辑删除书记表信息
        TabPbLeadTeamMember tabPbLeadTeamMember = tabPbLeadTeamMemberMapper.selectByPrimaryKey(leadTeamMemberDTO.getMemberId());
        boolean judgementMemberIfSecretary = tabPbLeadTeamMapper.checkIfSecretary(tabPbLeadTeamMember.getPositiveId());
        boolean judgement = tabPbLeadTeamMapper.checkIfSecretary(leadTeamMemberDTO.getPositiveId());
        //如果现在不是书记
        if (!judgement) {
            //如果原来是书记
            if (judgementMemberIfSecretary) {
                //逻辑删除原书记信息
                TabPbDeptSecretary originSecretary = new TabPbDeptSecretary();
                originSecretary.setDelFlag(CommonConstant.STATUS_DEL)
                        .setUserId(tabPbLeadTeamMember.getUserId())
                        .setLeadTeamId(leadTeamMemberDTO.getLeadTeamId());
                PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(originSecretary);
                secretaryService.logicDeleteTabPbSecretary(originSecretary);
            }
            return;
        }

        //现在是书记并且原来也是书记的情况,则修改原来的书记信息
        TabPbDeptSecretary oldSecretary = secretaryService.selectOldSecretaryInfoByUserIdAndLeadTeamId(leadTeamMemberDTO.getUserId(), leadTeamMemberDTO.getLeadTeamId());
        if (oldSecretary != null) {
            oldSecretary.setUserId(leadTeamMemberDTO.getUserId())
                    .setLeadTeamId(leadTeamMemberDTO.getLeadTeamId())
                    .setServeTime(leadTeamMemberDTO.getTenureBegin())
                    .setWhetherMember(IS_SECRETARY)
                    .setPostive(leadTeamMemberDTO.getPositiveName())
                    .setNewPosition(leadTeamMemberDTO.getPositiveId())
                    .setWhetherMember((long) leadTeamMemberDTO.getAsCommitteeMember())
                    .setAppointmentTime(leadTeamMemberDTO.getTenureLeave());
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(oldSecretary);
            secretaryService.updateTabPbDeptSecretarySelective(oldSecretary);

        } else {
            //现在是书记但是原来不是书记的情况，则新增书记信息
            TabPbDeptSecretary tabPbDeptSecretary = new TabPbDeptSecretary();
            tabPbDeptSecretary.setUserId(leadTeamMemberDTO.getUserId())
                    .setLeadTeamId(leadTeamMemberDTO.getLeadTeamId())
                    .setServeTime(leadTeamMemberDTO.getTenureBegin())
                    .setWhetherMember(IS_SECRETARY)
                    .setPostive(leadTeamMemberDTO.getPositiveName())
                    .setOldPosition(leadTeamMemberDTO.getPositiveId())
                    .setNewPosition(leadTeamMemberDTO.getPositiveId())
                    .setWhetherMember((long) leadTeamMemberDTO.getAsCommitteeMember())
                    .setAppointmentTime(leadTeamMemberDTO.getTenureLeave());
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbDeptSecretary);
            secretaryService.insertTabPbDeptSecretary(tabPbDeptSecretary);
        }
    }
}
