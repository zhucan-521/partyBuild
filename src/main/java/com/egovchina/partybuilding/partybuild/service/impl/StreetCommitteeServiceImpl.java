package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeDTO;
import com.egovchina.partybuilding.partybuild.dto.StreetCommitteeMemberDTO;
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
import java.util.stream.Collectors;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField;

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

    @Override
    @Transactional
    public int saveStreetCommittee(StreetCommitteeDTO streetCommitteeDTO) {
        if (tabSysDeptMapper.selectByPrimaryKey(streetCommitteeDTO.getOrgId()) == null) {
            throw new BusinessDataIncompleteException("组织不存在");
        }

        var old = this.tabPbGrantCommitteeMapper.selectByOrgId(streetCommitteeDTO.getOrgId());
        TabPbGrantCommittee tabPbGrantCommittee =
                generateTargetCopyPropertiesAndPaddingBaseField(
                        streetCommitteeDTO, TabPbGrantCommittee.class, false);
        int count = 0;
        if (old == null) {
            streetCommitteeDTO.setCurrent((byte) 1);
            count += this.tabPbGrantCommitteeMapper.insertSelectiveAndReturnPrimaryKey(tabPbGrantCommittee);
            streetCommitteeDTO.setGrantCommitteeId(tabPbGrantCommittee.getGrantCommitteeId());
        } else {
            streetCommitteeDTO.setGrantCommitteeId(old.getGrantCommitteeId());
            count += this.tabPbGrantCommitteeMapper.updateByPrimaryKeySelective(tabPbGrantCommittee);
        }
        return count;
    }

    /**
     * 保存大公委及大公委成员数据
     *
     * @param streetCommitteeDTO
     * @param streetCommitteMemberDTOList
     */
    @Override
    @Transactional
    public int addStreetCommittee(StreetCommitteeDTO streetCommitteeDTO, List<StreetCommitteeMemberDTO> streetCommitteMemberDTOList) {
        int count = 0;
        count += this.saveStreetCommittee(streetCommitteeDTO);
        streetCommitteMemberDTOList.forEach(v -> v.setGrantCommitteeId(streetCommitteeDTO.getGrantCommitteeId()));
        count += this.addStreetCommitteeMembers(streetCommitteMemberDTOList);
        return count;
    }

    @Override
    public int deleteStreetCommittee(Long id) {
        int result = 0;
        var tabPbGrantCommittee = new TabPbGrantCommittee()
                .setGrantCommitteeId(id)
                .setDelFlag("1");
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbGrantCommittee);
        result += this.tabPbGrantCommitteeMapper.updateByPrimaryKeySelective(tabPbGrantCommittee);

        // 删除关联表
        var tabPbGrantCommitteMember = new TabPbGrantCommitteMember()
                .setGrantCommitteeId(id)
                .setDelFlag("1");
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbGrantCommitteMember);
        result += this.tabPbGrantCommitteMemberMapper.updateByGrantCommitteeSelective(tabPbGrantCommitteMember);
        return result;
    }

    /**
     * 通过id查询街道大公委数据
     *
     * @param grantCommitteeId
     * @return
     */
    @Override
    public StreetCommitteeVO getStreetCommitteeById(Long grantCommitteeId) {
        return this.tabPbGrantCommitteeMapper.selectStreetCommitteeVOById(grantCommitteeId);
    }

    /**
     * 分页查询街道大公委数据
     *
     * @param streetCommitteeQueryBean
     * @param page                     分页
     * @return 街道大公委数据
     */
    @Override
    public PageInfo<StreetCommitteeVO> getStreetCommitteeList(StreetCommitteeQueryBean streetCommitteeQueryBean, Page page) {
        PageHelper.startPage(page);
        List<StreetCommitteeVO> list = this.tabPbGrantCommitteeMapper.selectStreetCommitteeVOList(streetCommitteeQueryBean);
        return new PageInfo<>(list);
    }


    @Override
    public int addStreetCommitteeMember(StreetCommitteeMemberDTO streetCommitteeMemberDTO) {
        TabPbGrantCommitteMember tabPbGrantCommitteMember = new TabPbGrantCommitteMember();
        BeanUtil.copyPropertiesIgnoreNull(streetCommitteeMemberDTO, tabPbGrantCommitteMember);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbGrantCommitteMember);
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
                .setDelFlag("1");
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbGrantCommitteMember);
        return this.tabPbGrantCommitteMemberMapper.updateByPrimaryKeySelective(tabPbGrantCommitteMember);
    }

    @Override
    public PageInfo<StreetCommitteeMemberVO> getStreetCommitteeMemberList(
            Long grantCommitteeId, String personName, String positiveName, Page page) {
        PageHelper.startPage(page);
        var tabPbGrantCommitteMember = new TabPbGrantCommitteMember()
                .setGrantCommitteeId(grantCommitteeId)
                .setPersonName(personName)
                .setPositiveName(positiveName);
        List<StreetCommitteeMemberVO> list = this.tabPbGrantCommitteMemberMapper.selectStreetCommitteeMemberVOList(tabPbGrantCommitteMember);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public int addStreetCommitteeMembers(List<StreetCommitteeMemberDTO> streetCommitteMemberDTOList) {
        List<TabPbGrantCommitteMember> tabPbGrantCommitteMembers =
                generateTargetListCopyPropertiesAndPaddingBaseField(streetCommitteMemberDTOList, TabPbGrantCommitteMember.class, false);
        int count = 0;
        for (TabPbGrantCommitteMember tabPbGrantCommitteMember : tabPbGrantCommitteMembers.stream()
                .filter(v -> v.getGrantCommitteeId() != null)
                .collect(Collectors.toList())) {
            //校验街道大工委成员是否存在
            if (this.tabPbGrantCommitteMemberMapper.verifyStreetCommitteeMembers(
                    tabPbGrantCommitteMember.getLeadTeamId(), tabPbGrantCommitteMember.getGrantCommitteeId(),
                    tabPbGrantCommitteMember.getUserId()) > 0) {
                throw new BusinessDataIncompleteException(tabPbGrantCommitteMember.getPersonName() + "已经存在");
            }
            count += this.tabPbGrantCommitteMemberMapper.insertSelective(tabPbGrantCommitteMember);
        }
        return count;
    }

}
