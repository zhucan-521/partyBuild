package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.JointMeetDTO;
import com.egovchina.partybuilding.partybuild.dto.JointMeetOrgDTO;
import com.egovchina.partybuilding.partybuild.entity.JointMeetOrgQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
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
import java.util.Objects;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author wuyunjie
 * @version 1.0
 * @date 2018/12/29
 */
@Service
public class JointMeetServiceImpl implements JointMeetService {

    @Autowired
    private TabPbJointMeetMapper tabPbJointMeetMapper;
    @Autowired
    private TabPbJointMeetOrgMapper tabPbJointMeetOrgMapper;

    /**
     * @param jointMeetDto 联席会组织信息及成员信息, 如果没有可不传。 联席会组织成员列表为必传
     * @return
     */
    @Override
    @Transactional
    public Long addJointMeet(JointMeetDTO jointMeetDto) {
        if (isEmpty(jointMeetDto.getOrgId())) {
            jointMeetDto.setOrgId(Objects.requireNonNull(UserContextHolder.currentUser()).getDeptId());
        }

        // 默认是当前时间
        jointMeetDto.setFoundedDate(isEmpty(jointMeetDto.getFoundedDate()) ? new Date() : jointMeetDto.getFoundedDate());

        var jointMeet = this.tabPbJointMeetMapper.selectJointMeetByOrgId(jointMeetDto.getOrgId());
        if (isEmpty(jointMeet)) {
            TabPbJointMeet tabPbJointMeet =
                    BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(jointMeetDto, TabPbJointMeet.class, false);
            this.tabPbJointMeetMapper.insertSelective(tabPbJointMeet);
            jointMeetDto.setJointMeetId(tabPbJointMeet.getJointMeetId());
        } else {
            jointMeetDto.setJointMeetId(jointMeet.getJointMeetId());
        }
        jointMeetDto.getJointMeetOrgs().forEach(jointMeetOrgDTO -> {
            jointMeetOrgDTO.setJointMeetId(jointMeetDto.getJointMeetId());
            jointMeetOrgDTO.setJoinDate(isEmpty(jointMeetOrgDTO.getJoinDate()) ? new Date() : jointMeetOrgDTO.getJoinDate());
            Long check = this.tabPbJointMeetOrgMapper.selectCheck(jointMeetDto.getOrgId(),jointMeetOrgDTO.getOrgId());
            if (check == 0L) {
                TabPbJointMeetOrg tabPbJointMeetOrg =
                        BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(jointMeetOrgDTO, TabPbJointMeetOrg.class, false);
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
                generateTargetListCopyPropertiesAndPaddingBaseField(
                        jointMeetOrgs, TabPbJointMeetOrg.class, false);
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
        this.tabPbJointMeetMapper.updateByPrimaryKeySelective(jointMeet.setDelFlag(CommonConstant.STATUS_DEL));
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
        TabPbJointMeetOrg tabPbJointMeetOrg = this.tabPbJointMeetOrgMapper.selectByPrimaryKey(jointMeetOrg.getMemberOrgId());
        if (isEmpty(tabPbJointMeetOrg)) {
            throw new BusinessDataNotFoundException("成员不存在");
        }
        //校验是否能删除成员
        if(tabPbJointMeetOrgMapper.checkIfItCanBeDeleted(tabPbJointMeetOrg.getOrgId())){
            throw new BusinessDataNotFoundException("该单位存在联席活动，删除失败");
        }
        this.tabPbJointMeetOrgMapper.updateByPrimaryKeySelective(jointMeetOrg.setDelFlag(CommonConstant.STATUS_DEL));
    }

    @Override
    @Transactional
    public int updateJointMeet(JointMeetDTO jointMeetDto) {
        if (isEmpty(jointMeetDto.getJointMeetId())) {
            throw new BusinessDataIncompleteException("jointMeetId不可为null");
        }
        TabPbJointMeet tabPbJointMeet =
                generateTargetCopyPropertiesAndPaddingBaseField(jointMeetDto, TabPbJointMeet.class, true);
        if(jointMeetDto.getJointMeetOrgs() != null){
            List<TabPbJointMeetOrg> tabPbJointMeetOrgList = generateTargetListCopyPropertiesAndPaddingBaseField(
                    jointMeetDto.getJointMeetOrgs(), TabPbJointMeetOrg.class, true);
            this.tabPbJointMeetOrgMapper.batchUpdate(tabPbJointMeetOrgList);
        }
        return this.tabPbJointMeetMapper.updateByPrimaryKeySelective(tabPbJointMeet);
    }

    @Override
    @Transactional
    public int updateJointMeetOrg(JointMeetOrgDTO jointMeetOrgDTO) {
        if (isEmpty(jointMeetOrgDTO.getMemberOrgId())) {
            throw new BusinessDataIncompleteException("memberOrg不可为null");
        }
        TabPbJointMeetOrg tabPbJointMeetOrg =
                generateTargetCopyPropertiesAndPaddingBaseField(jointMeetOrgDTO, TabPbJointMeetOrg.class, true);
        return this.tabPbJointMeetOrgMapper.updateByPrimaryKeySelective(tabPbJointMeetOrg);
    }

    @Override
    public PageInfo<JointMeetOrgVO> getJointMeetOrgList(JointMeetOrgQueryBean jointMeetOrgQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(this.tabPbJointMeetOrgMapper.selectJointMeetOrgVOList(jointMeetOrgQueryBean));
    }

    @Override
    public PageInfo<JointMeetOrgVO> getJointMeetMemberList(JointMeetOrgQueryBean jointMeetOrgQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(this.tabPbJointMeetMapper.getJointMeetMemberList(jointMeetOrgQueryBean));
    }

    @Override
    public JointMeetVO getJointMeet(Long jointMeetId) {
        return tabPbJointMeetMapper.getJointMeet(jointMeetId);
    }

}
