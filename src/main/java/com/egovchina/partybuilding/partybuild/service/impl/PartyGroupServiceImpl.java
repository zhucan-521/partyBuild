package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyGroupMemberInfoDTO;
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
    private ITabPbAttachmentService tabPbAttachmentService;

    @Override
    public int insertPartyGroup(PartyGroupDTO partyGroupDTO) {
        verificationPartyGroup(partyGroupDTO);
        TabPbPartyGroup tabPbPartyGroup = generateTargetCopyPropertiesAndPaddingBaseField(partyGroupDTO, TabPbPartyGroup.class, false);
        int result = tabPbPartyGroupMapper.insertSelective(tabPbPartyGroup);
        if (result > 0) {
            maintainInsertPartyGroupAction(partyGroupDTO, tabPbPartyGroup.getGroupId());
        }
        return result;
    }

    @Override
    public int updatePartyGroup(PartyGroupDTO partyGroupDTO) {
        verificationPartyGroup(partyGroupDTO);
        int result = tabPbPartyGroupMapper.updateByPrimaryKeySelective(generateTargetCopyPropertiesAndPaddingBaseField(partyGroupDTO, TabPbPartyGroup.class, true));
        if (result > 0) {
            result += maintainUpdatePartyGroupAction(partyGroupDTO);
        }
        return result;
    }

    @Override
    public int deletePartyGroup(Long groupId) {
        TabPbPartyGroup tabPbPartyGroup = new TabPbPartyGroup().setGroupId(groupId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbPartyGroup);
        return tabPbPartyGroupMapper.logicDeletePartyGroupCascadeMembers(tabPbPartyGroup);
    }

    @Override
    public int revokePartyGroup(Long groupId) {
        return tabPbPartyGroupMapper.updateByPrimaryKeySelective(maintainRevocationOfPartyGroups(groupId, 1));
    }

    @Override
    public int recoveryPartyGroup(Long groupId) {
        return tabPbPartyGroupMapper.updateByPrimaryKeySelective(maintainRevocationOfPartyGroups(groupId, 0));
    }

    @Override
    public PageInfo<PartyMemberBaseVO> screenPartyGroupMembers(Long orgId, Long groupId) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(orgId)) {
            throw new BusinessDataCheckFailException("该党组织不存在");
        }
        return new PageInfo<>(tabPbPartyGroupMemberMapper.screenPartyGroupMembers(orgId, groupId));
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
    private void verificationPartyGroup(PartyGroupDTO partyGroupDTO) {
        Long orgId = partyGroupDTO.getOrgId();
        Long groupId = partyGroupDTO.getGroupId();
        if (!tabSysDeptMapper.checkIsExistByOrgId(orgId)) {
            throw new BusinessDataCheckFailException("该党组织不存在");
        }
        if (tabPbPartyGroupMapper.checkIsExistByGroupName(orgId, partyGroupDTO.getGroupName(), groupId)) {
            throw new BusinessDataCheckFailException("该党小组名称已存在");
        }
        if (partyGroupDTO.getBuildTime() == null) {
            partyGroupDTO.setBuildTime(tabSysDeptMapper.getFoundedDateByOrgId(orgId));
        }
        if (groupId != null && !tabPbPartyGroupMapper.checkIsExistByGroupId(groupId)) {
            throw new BusinessDataCheckFailException("该党小组不存在");
        }
    }

    private void verifyThatTheLeaderIsUnique(List<PartyGroupMemberInfoDTO> memberList) {
        boolean leaderExist = false;
        for (PartyGroupMemberInfoDTO member : memberList) {
            if (member.getIsLeader() == 1) {
                if (leaderExist) {
                    throw new BusinessDataCheckFailException("党小组组长不能超过一个");
                }
                leaderExist = true;
            }
        }
        if (!leaderExist) {
            throw new BusinessDataCheckFailException("党小组组长不能为空");
        }
    }

    /**
     * desc: 维护党小组撤销功能
     *
     * @param groupId  党小组ID
     * @param isRevoke 撤销标识
     * @return TabPbPartyGroup
     * @auther FanYanGen
     * @date 2019-05-10 09:44
     */
    private TabPbPartyGroup maintainRevocationOfPartyGroups(Long groupId, Integer isRevoke) {
        TabPbPartyGroup tabPbPartyGroup = new TabPbPartyGroup();
        tabPbPartyGroup.setGroupId(groupId);
        tabPbPartyGroup.setIsRevoke(isRevoke);
        if (isRevoke == 0) {
            tabPbPartyGroup.setRevokeName("");
            tabPbPartyGroup.setRevokeTime(null);
        } else {
            tabPbPartyGroup.setRevokeName(UserContextHolder.getUserName());
            tabPbPartyGroup.setRevokeTime(new Date());
        }
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbPartyGroup);
        return tabPbPartyGroup;
    }

    /**
     * desc:
     * 1- 接收并转换前端传入的值
     * 2- 验证数据是否合理
     * 3- 再根据前端传的值添加
     * 4- 添加附件
     *
     * @param partyGroupDTO 党小组dto
     * @return int
     * @author FanYanGen
     * @date 2019/5/8 17:49
     **/
    private int maintainInsertPartyGroupAction(PartyGroupDTO partyGroupDTO, Long groupId) {
        int result = 0;
        List<PartyGroupMemberInfoDTO> memberList = partyGroupDTO.getMembers();
        verifyThatTheLeaderIsUnique(memberList);
        List<TabPbPartyGroupMember> tabPbPartyGroupMembers = generateTargetListCopyPropertiesAndPaddingBaseField(memberList, TabPbPartyGroupMember.class, member -> member.setGroupId(groupId), false);
        result += tabPbPartyGroupMemberMapper.batchInsert(tabPbPartyGroupMembers);
        result += tabPbAttachmentService.intelligentOperation(partyGroupDTO.getAttachments(), groupId, AttachmentType.PARTY_GROUP);
        return result;
    }

    /**
     * desc:
     * 1- 根据党组ID删除数据库已有的人员
     * 2- 调用maintainInsertPartyGroupAction() 方法的操作
     *
     * @param partyGroupDTO 党小组dto
     * @return int
     * @author FanYanGen
     * @date 2019/5/8 17:39
     **/
    private int maintainUpdatePartyGroupAction(PartyGroupDTO partyGroupDTO) {
        int result = 0;
        Long groupId = partyGroupDTO.getGroupId();
        result += tabPbPartyGroupMemberMapper.batchDeleteByGroupId(groupId);
        result += maintainInsertPartyGroupAction(partyGroupDTO, groupId);
        return result;
    }

}