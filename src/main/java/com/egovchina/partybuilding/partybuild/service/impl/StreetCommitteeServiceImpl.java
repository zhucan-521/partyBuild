package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeDTO;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.StreetCommitteeMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.StreetCommitteeQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbGrantCommitteMember;
import com.egovchina.partybuilding.partybuild.entity.TabPbGrantCommittee;
import com.egovchina.partybuilding.partybuild.repository.TabPbGrantCommitteMemberMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbGrantCommitteeMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.StreetCommitteeService;
import com.egovchina.partybuilding.partybuild.vo.StreetCommitteeMemberVO;
import com.egovchina.partybuilding.partybuild.vo.StreetCommitteeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @author wuyunjie
 * @version 1.0
 * @date 2018/12/04
 */
@Service
public class StreetCommitteeServiceImpl implements StreetCommitteeService {

    @Autowired
    private TabPbGrantCommitteMemberMapper tabPbGrantCommitteMemberMapper;

    @Autowired
    private TabPbGrantCommitteeMapper tabPbGrantCommitteeMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Transactional
    @Override
    public int saveStreetCommittee(StreetCommitteeDTO streetCommitteeDTO) {

        //判断是否需要修改为往届
        determineIfItNeedsToBeRevisedToThePast(streetCommitteeDTO);

        //新增大工委班子
        TabPbGrantCommittee tabPbGrantCommittee =
                generateTargetCopyPropertiesAndPaddingBaseField(
                        streetCommitteeDTO, TabPbGrantCommittee.class, false);
        return this.tabPbGrantCommitteeMapper.insertSelectiveAndReturnPrimaryKey(tabPbGrantCommittee);
    }

    @Override
    public int updateStreetCommittee(StreetCommitteeDTO streetCommitteeDTO) {
        if (streetCommitteeDTO.getGrantCommitteeId() == null) {
            throw new BusinessDataIncompleteException("工委班子不存在");
        }
        //判断是否需要修改为往届
        determineIfItNeedsToBeRevisedToThePast(streetCommitteeDTO);
        TabPbGrantCommittee tabPbGrantCommittee =
                generateTargetCopyPropertiesAndPaddingBaseField(
                        streetCommitteeDTO, TabPbGrantCommittee.class, false);
        return this.tabPbGrantCommitteeMapper.updateByPrimaryKeySelective(tabPbGrantCommittee);
    }

    @Transactional
    @Override
    public int deleteStreetCommittee(Long grantCommitteeId) {
        int result = 0;
        TabPbGrantCommittee tabPbGrantCommittee = new TabPbGrantCommittee()
                .setGrantCommitteeId(grantCommitteeId)
                .setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbGrantCommittee);
        result += this.tabPbGrantCommitteeMapper.updateByPrimaryKeySelective(tabPbGrantCommittee);

        // 删除关联表
        TabPbGrantCommitteMember tabPbGrantCommitteMember = new TabPbGrantCommitteMember()
                .setGrantCommitteeId(grantCommitteeId)
                .setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbGrantCommitteMember);
        result += this.tabPbGrantCommitteMemberMapper.updateByGrantCommitteeSelective(tabPbGrantCommitteMember);
        return result;
    }

    @Override
    public StreetCommitteeVO getStreetCommitteeById(Long grantCommitteeId) {
        return this.tabPbGrantCommitteeMapper.selectStreetCommitteeVOById(grantCommitteeId);
    }

    @Override
    public PageInfo<StreetCommitteeVO> getStreetCommitteeList(StreetCommitteeQueryBean streetCommitteeQueryBean, Page page) {
        PageHelper.startPage(page);
        List<StreetCommitteeVO> list = this.tabPbGrantCommitteeMapper.selectStreetCommitteeVOList(streetCommitteeQueryBean);
        return new PageInfo<>(list);
    }

    @Override
    public int addStreetCommitteeMember(StreetCommitteeMemberDTO streetCommitteeMemberDTO) {
        Long committeeOrgId = streetCommitteeMemberDTO.getCommitteeOrgId();
        Long grantCommitteId = this.tabPbGrantCommitteeMapper.selectCommitteeIdByOrgId(committeeOrgId);
        if (grantCommitteId == null) {
            throw new BusinessDataIncompleteException("没有当届工委班子不能添加成员");
        }
        //校验工委成员是否存在
        int count = this.tabPbGrantCommitteMemberMapper.verifyStreetCommitteeMembers(grantCommitteId, streetCommitteeMemberDTO.getUserId());
        if (count > 0) {
            throw new BusinessDataIncompleteException(streetCommitteeMemberDTO.getPersonName() + "成员已存在");
        }
        streetCommitteeMemberDTO.setGrantCommitteeId(grantCommitteId);
        TabPbGrantCommitteMember tabPbGrantCommitteMember =
                generateTargetCopyPropertiesAndPaddingBaseField(streetCommitteeMemberDTO, TabPbGrantCommitteMember.class, false);
        return this.tabPbGrantCommitteMemberMapper.insertSelective(tabPbGrantCommitteMember);
    }

    @Override
    public StreetCommitteeMemberVO getStreetCommitteeMemberById(Long grantCommitteeMemberId) {
        return this.tabPbGrantCommitteMemberMapper.selectStreetCommitteeMemberVOById(grantCommitteeMemberId);
    }

    @Override
    public int deleteStreetCommitteeMemberById(Long grantCommitteeMemberId) {
        var tabPbGrantCommitteMember = new TabPbGrantCommitteMember()
                .setGrantCommitteeMemberId(grantCommitteeMemberId)
                .setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbGrantCommitteMember);
        return this.tabPbGrantCommitteMemberMapper.updateByPrimaryKeySelective(tabPbGrantCommitteMember);
    }

    @Override
    public PageInfo<StreetCommitteeMemberVO> getStreetCommitteeMemberList(
            StreetCommitteeMemberQueryBean streetCommitteeMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        List<StreetCommitteeMemberVO> list = this.tabPbGrantCommitteMemberMapper.selectStreetCommitteeMemberVOList(streetCommitteeMemberQueryBean);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean checkStreetCommitteeWhetherAddMembers(Long orgId) {
        return Optional.ofNullable(this.tabPbGrantCommitteeMapper.selectStreetCommitteeWhetherAddMembers(orgId)).orElse(false);
    }

    private void determineIfItNeedsToBeRevisedToThePast(StreetCommitteeDTO streetCommitteeDTO) {
        //校验组织是否存在
        Long orgId = streetCommitteeDTO.getOrgId();
        if (tabSysDeptMapper.selectByPrimaryKey(orgId) == null) {
            throw new BusinessDataIncompleteException("组织不存在");
        }
        Long grantCommitteId = this.tabPbGrantCommitteeMapper.selectCommitteeIdByOrgId(orgId);
        //修改大工委班子为往届
        if (grantCommitteId != null && CommonConstant.CURRENT_MARK.equals(streetCommitteeDTO.getCurrent())) {
            TabPbGrantCommittee modifyPast = new TabPbGrantCommittee();
            modifyPast.setGrantCommitteeId(grantCommitteId);
            modifyPast.setCurrent(CommonConstant.PAST_MARK);
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(modifyPast);
            this.tabPbGrantCommitteeMapper.updateByPrimaryKeySelective(modifyPast);
        }
    }
}
