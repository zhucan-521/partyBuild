package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.JointMeetDTO;
import com.egovchina.partybuilding.partybuild.dto.JointMeetOrgDTO;
import com.egovchina.partybuilding.partybuild.entity.JointMeetOrgQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivities;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import com.egovchina.partybuilding.partybuild.repository.TabPbActivitiesMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbJointMeetMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbJointMeetOrgMapper;
import com.egovchina.partybuilding.partybuild.service.JointMeetService;
import com.egovchina.partybuilding.partybuild.vo.JointMeetOrgVO;
import com.egovchina.partybuilding.partybuild.vo.JointMeetVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.copyListPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.BeanUtil.copyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author wuyunjie
 * @version 1.0
 * @date 2018/12/29
 */
@Service
public class JointMeetServiceImpl implements JointMeetService {

    public final static String DEL_FLAG = "1";

    @Autowired
    private TabPbJointMeetMapper tabPbJointMeetMapper;

    @Autowired
    private TabPbJointMeetOrgMapper tabPbJointMeetOrgMapper;

    @Autowired
    private TabPbActivitiesMapper tabPbActivitiesMapper;

    /**
     * @param jointMeetDto 联席会组织信息及成员信息, 如果没有可不传。 联席会组织成员列表为必传
     * @return
     */
    @Override
    @Transactional
    public Long addJointMeet(JointMeetDTO jointMeetDto) {
        if (isEmpty(jointMeetDto.getOrgId())) {
            jointMeetDto.setOrgId(UserContextHolder.currentUser().getDeptId().longValue());
        }

        // 默认是当前时间
        jointMeetDto.setFoundedDate(isEmpty(jointMeetDto.getFoundedDate()) ? new Date() : jointMeetDto.getFoundedDate());

        var jointMeet = this.tabPbJointMeetMapper.selectJointMeetByOrgId(jointMeetDto.getOrgId());
        if (isEmpty(jointMeet)) {
            TabPbJointMeet tabPbJointMeet =
                    copyPropertiesAndPaddingBaseField(jointMeetDto, TabPbJointMeet.class, true, false);
            this.tabPbJointMeetMapper.insertSelective(tabPbJointMeet);
            jointMeetDto.setJointMeetId(tabPbJointMeet.getJointMeetId());
        } else {
            jointMeetDto.setJointMeetId(jointMeet.getJointMeetId());
        }
        jointMeetDto.getJointMeetOrgs().forEach(jointMeetOrgDTO -> {
            jointMeetOrgDTO.setJointMeetId(jointMeetDto.getJointMeetId());
            jointMeetOrgDTO.setJoinDate(isEmpty(jointMeetOrgDTO.getJoinDate()) ? new Date() : jointMeetOrgDTO.getJoinDate());
            Long check = this.tabPbJointMeetOrgMapper.selectCheck(jointMeetDto.getOrgId(), jointMeetOrgDTO.getOrgId());
            if (check == 0L) {
                TabPbJointMeetOrg tabPbJointMeetOrg =
                        copyPropertiesAndPaddingBaseField(jointMeetOrgDTO, TabPbJointMeetOrg.class, true, false);
                this.tabPbJointMeetOrgMapper.insertSelective(tabPbJointMeetOrg);
            }
        });
        return jointMeetDto.getJointMeetId();
    }

    /**
     * @param jointMeetOrgs 党建联席会主键id, 成员列表
     * @return
     */
    @Override
    @Transactional
    public ReturnEntity addJointMeetOrgList(List<JointMeetOrgDTO> jointMeetOrgs) {
        List<TabPbJointMeetOrg> tabPbJointMeetOrgList =
                copyListPropertiesAndPaddingBaseField(
                        jointMeetOrgs, TabPbJointMeetOrg.class, true, false);
        this.tabPbJointMeetOrgMapper.batchInsert(tabPbJointMeetOrgList);
        return ReturnUtil.success();
    }

    /**
     * @param jointMeet
     * @return
     */
    @Override
    @Transactional
    public void deleteJointMeet(TabPbJointMeet jointMeet) {
        if (isEmpty(jointMeet.getJointMeetId())) {
            throw new BusinessDataIncompleteException("jointMeetId不可为null");
        }
        paddingUpdateRelatedBaseFiled(jointMeet);
        this.tabPbJointMeetMapper.updateByPrimaryKeySelective(jointMeet.setDelFlag(DEL_FLAG));
        var jointMeetOrg = new TabPbJointMeetOrg()
                .setJointMeetId(jointMeet.getJointMeetId());
        paddingUpdateRelatedBaseFiled(jointMeetOrg);
        this.tabPbJointMeetOrgMapper.select(jointMeetOrg).forEach(this::deleteJointMeetOrg);
    }

    /**
     * 根据主键删除
     *
     * @param jointMeetOrg
     */
    @Override
    @Transactional
    public void deleteJointMeetOrg(TabPbJointMeetOrg jointMeetOrg) {
        if (isEmpty(jointMeetOrg.getMemberOrgId())) {
            throw new BusinessDataIncompleteException("memberOrgId不可为null");
        }
        var tmp = this.tabPbJointMeetOrgMapper.selectByPrimaryKey(jointMeetOrg.getMemberOrgId());
        if (isEmpty(tmp)) {
            throw new BusinessDataNotFoundException("成员不存在");
        }
        var activity = new TabPbActivities()
                .setDelFlag(DEL_FLAG)
                .setOrgId(tmp.getOrgId())
                .setActivitiesType(AttachmentType.JOINT_MEET);
        paddingUpdateRelatedBaseFiled(activity);
        this.tabPbActivitiesMapper.deleteByOrgIdAndActivityType(activity);
        this.tabPbJointMeetOrgMapper.updateByPrimaryKeySelective(jointMeetOrg.setDelFlag(DEL_FLAG));
    }

    @Override
    @Transactional
    public int updateJointMeet(JointMeetDTO jointMeetDto) {
        if (isEmpty(jointMeetDto.getJointMeetId())) {
            throw new BusinessDataIncompleteException("jointMeetId不可为null");
        }
        TabPbJointMeet tabPbJointMeet =
                copyPropertiesAndPaddingBaseField(jointMeetDto, TabPbJointMeet.class, true, true);
        return this.tabPbJointMeetMapper.updateByPrimaryKeySelective(tabPbJointMeet);
    }

    @Override
    @Transactional
    public int updateJointMeetOrg(JointMeetOrgDTO jointMeetOrgDTO) {
        if (isEmpty(jointMeetOrgDTO.getMemberOrgId())) {
            throw new BusinessDataIncompleteException("memberOrg不可为null");
        }
        TabPbJointMeetOrg tabPbJointMeetOrg =
                copyPropertiesAndPaddingBaseField(jointMeetOrgDTO, TabPbJointMeetOrg.class, true, true);
        return this.tabPbJointMeetOrgMapper.updateByPrimaryKeySelective(tabPbJointMeetOrg);
    }

    @Override
    public PageInfo<JointMeetVO> getJointMeetList(TabPbJointMeet meet, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(this.tabPbJointMeetMapper.selectJointMeetVOList(meet));
    }

    @Override
    public PageInfo<JointMeetOrgVO> getJointMeetOrgList(JointMeetOrgQueryBean jointMeetOrgQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(this.tabPbJointMeetOrgMapper.selectJointMeetOrgVOList(jointMeetOrgQueryBean));
    }
}
