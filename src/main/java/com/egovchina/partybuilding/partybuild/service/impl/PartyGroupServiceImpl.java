package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupMemberIdsDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyGroupQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyGroup;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyGroupMember;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyGroupMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyGroupMemberMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.PartyGroupService;
import com.egovchina.partybuilding.partybuild.vo.PartyGroupVO;
import com.egovchina.partybuilding.partybuild.vo.PartyMemberBaseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField;

/**
 * desc: 党小组-服务接口实现
 * Created by FanYanGen on 2019/4/28 10:33
 */
@Transactional(rollbackFor = Exception.class)
@Service("partyGroupService")
public class PartyGroupServiceImpl implements PartyGroupService {

    @Autowired
    private TabPbPartyGroupMapper tabPbPartyGroupMapper;

    @Autowired
    private TabPbPartyGroupMemberMapper tabPbPartyGroupMemberMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private ITabPbAttachmentService attachmentService;

    @Override
    public int insertPartyGroup(PartyGroupDTO partyGroupDTO) {
        verificationPartyGroup(partyGroupDTO, false);
        int result = tabPbPartyGroupMapper.insertSelective(generateTargetCopyPropertiesAndPaddingBaseField(partyGroupDTO, TabPbPartyGroup.class, false));
        if (0 < result) {
            result += attachmentService.intelligentOperation(partyGroupDTO.getAttachments(), partyGroupDTO.getGroupId(), AttachmentType.DOUBLE_COMMENTARY);
        }
        return result;
    }

    @Override
    public int insertMemberToPartyGroup(PartyGroupMemberDTO partyGroupMemberDTO) {
        verificationInsertMemberToPartyGroup(partyGroupMemberDTO);
        List<TabPbPartyGroupMember> tabPbPartyGroupMembers = generateTargetListCopyPropertiesAndPaddingBaseField(partyGroupMemberDTO.getMembers(), TabPbPartyGroupMember.class, member -> member.setGroupId(partyGroupMemberDTO.getGroupId()), false);
        return tabPbPartyGroupMemberMapper.batchInsert(tabPbPartyGroupMembers);
    }

    @Override
    public int updatePartyGroup(PartyGroupDTO partyGroupDTO) {
        verificationPartyGroup(partyGroupDTO, true);
        int result = tabPbPartyGroupMapper.updateByPrimaryKeySelective(generateTargetCopyPropertiesAndPaddingBaseField(partyGroupDTO, TabPbPartyGroup.class, true));
        if (0 < result) {
            result += attachmentService.intelligentOperation(partyGroupDTO.getAttachments(), partyGroupDTO.getGroupId(), AttachmentType.DOUBLE_COMMENTARY);
        }
        return result;
    }

    @Override
    public int deletePartyGroup(Long groupId) {
        TabPbPartyGroup tabPbPartyGroup = new TabPbPartyGroup();
        tabPbPartyGroup.setGroupId(groupId);
        tabPbPartyGroup.setDelFlag(1);
        generateTargetCopyPropertiesAndPaddingBaseField(tabPbPartyGroup, TabPbPartyGroup.class, true);
        TabPbPartyGroupMember tabPbPartyGroupMember = new TabPbPartyGroupMember();
        tabPbPartyGroupMember.setGroupId(groupId);
        tabPbPartyGroupMember.setDelFlag(1);
        generateTargetCopyPropertiesAndPaddingBaseField(tabPbPartyGroupMember, TabPbPartyGroupMember.class, true);
        int result = tabPbPartyGroupMapper.updateByPrimaryKeySelective(tabPbPartyGroup);
        // 删除党小组及成员
        if (0 < result) {
            tabPbPartyGroupMemberMapper.batchDelete(tabPbPartyGroupMember);
        }
        return result;
    }

    @Override
    public int removePartyGroupMembers(List<PartyGroupMemberIdsDTO> partyGroupMemberIdsDTOList) {
        if (partyGroupMemberIdsDTOList.isEmpty()) {
            throw new BusinessDataCheckFailException("所传ID不能为空");
        }
        return tabPbPartyGroupMemberMapper.removePartyGroupMembers(generateTargetListCopyPropertiesAndPaddingBaseField(partyGroupMemberIdsDTOList, TabPbPartyGroupMember.class, true));
    }

    @Override
    public int revokePartyGroup(Long groupId) {
        TabPbPartyGroup tabPbPartyGroup = new TabPbPartyGroup();
        tabPbPartyGroup.setGroupId(groupId);
        tabPbPartyGroup.setDelFlag(1);
        tabPbPartyGroup.setRevokeName(UserContextHolder.getUserName());
        tabPbPartyGroup.setRevokeTime(new Date());
        generateTargetCopyPropertiesAndPaddingBaseField(tabPbPartyGroup, TabPbPartyGroup.class, true);
        return tabPbPartyGroupMapper.updateByPrimaryKeySelective(tabPbPartyGroup);
    }

    @Override
    public int recoveryPartyGroup(Long groupId) {
        TabPbPartyGroup tabPbPartyGroup = new TabPbPartyGroup();
        tabPbPartyGroup.setGroupId(groupId);
        tabPbPartyGroup.setDelFlag(0);
        tabPbPartyGroup.setRevokeName(UserContextHolder.getUserName());
        tabPbPartyGroup.setRevokeTime(new Date());
        generateTargetCopyPropertiesAndPaddingBaseField(tabPbPartyGroup, TabPbPartyGroup.class, true);
        return tabPbPartyGroupMapper.updateByPrimaryKeySelective(tabPbPartyGroup);
    }

    @Override
    public PageInfo<PartyMemberBaseVO> screenPartyGroupMembers(Long deptId, Page page) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(deptId)) {
            throw new BusinessDataCheckFailException("该党组织不存在");
        }
        PageHelper.startPage(page);
        return new PageInfo<>(tabPbPartyGroupMemberMapper.screenPartyGroupMembers(deptId));
    }

    @Override
    public PartyGroupVO getPartyGroupDetails(Long groupId) {
        return tabPbPartyGroupMapper.selectPartyGroupDetailsByGroupId(groupId);
    }

    @Override
    public PageInfo<PartyGroupVO> getPartyGroupList(PartyGroupQueryBean partyGroupQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(tabPbPartyGroupMapper.selectPartyGroupDetails(partyGroupQueryBean));
    }

    /**
     * desc:  数据校验提示
     *
     * @param partyGroupDTO dto
     * @author FanYanGen
     * @date 2019/4/29 14:28
     **/
    private void verificationPartyGroup(PartyGroupDTO partyGroupDTO, boolean isCheckPrimaryKey) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(partyGroupDTO.getOrgId())) {
            throw new BusinessDataCheckFailException("该党组织不存在");
        }
        if (isCheckPrimaryKey && !tabPbPartyGroupMapper.checkIsExistByGroupId(partyGroupDTO.getGroupId())) {
            throw new BusinessDataCheckFailException("该党组不存在");
        }
    }

    private void verificationInsertMemberToPartyGroup(PartyGroupMemberDTO partyGroupMemberDTO) {
        if (!tabPbPartyGroupMapper.checkIsExistByGroupId(partyGroupMemberDTO.getGroupId())) {
            throw new BusinessDataCheckFailException("该党组不存在");
        }
    }

}