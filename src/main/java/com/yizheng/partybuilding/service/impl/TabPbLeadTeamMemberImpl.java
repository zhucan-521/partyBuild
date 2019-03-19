package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.util.PaddingBaseFieldUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbLeadTeamMemberDto;
import com.yizheng.partybuilding.entity.TabPbLeadTeamMember;
import com.yizheng.partybuilding.entity.TabPbPositives;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.partybuilding.repository.TabPbLeadTeamMapper;
import com.yizheng.partybuilding.repository.TabPbLeadTeamMemberMapper;
import com.yizheng.partybuilding.repository.TabPbPositivesMapper;
import com.yizheng.partybuilding.repository.TabSysUserMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.TabPbLeadTeamMemberService;
import com.yizheng.partybuilding.system.entity.SysUser;
import com.yizheng.commons.util.AttachmentType;
import com.yizheng.commons.util.CollectionUtil;
import com.yizheng.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * @Author Jiang An
 **/
@Service
public class TabPbLeadTeamMemberImpl implements TabPbLeadTeamMemberService {

    @Autowired
    private TabPbLeadTeamMemberMapper tabPbLeadTeamMemberMapper;
    @Autowired
    private TabSysUserMapper tabSysUserMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;
    @Autowired
    private TabPbLeadTeamMapper tabPbLeadTeamMapper;
    @Autowired
    TabPbPositivesMapper tabPbPositivesMapper;

    @Override
    public int deleteByPrimaryKey(Long memberId) {
        return tabPbLeadTeamMemberMapper.deleteByPrimaryKey(memberId);
    }

    @Override
    public int insert(TabPbLeadTeamMember record) {
        return tabPbLeadTeamMemberMapper.insert(record);
    }

    @Transactional
    @Override
    public int insertSelective(TabPbLeadTeamMember tabPbLeadTeamMember) {
        SysUser sysUser = tabSysUserMapper.selectByPrimaryKey(tabPbLeadTeamMember.getUserId());
        if (sysUser == null) {
            throw new BusinessDataNotFoundException("用户不存在");
        }
        List<TabPbLeadTeamMember> tm = tabPbLeadTeamMemberMapper.selectByUserId(tabPbLeadTeamMember.getLeadTeamId(), tabPbLeadTeamMember.getUserId());
        if (CollectionUtil.isNotEmpty(tm)) {
            throw new BusinessDataCheckFailException("该成员已存在");
        } else {
            //添加职务校验
            addJobInformation(tabPbLeadTeamMember);
            int retVal = tabPbLeadTeamMemberMapper.insertSelective(tabPbLeadTeamMember);
            if (retVal > 0) {
                Long i = tabPbLeadTeamMemberMapper.selectBycount(tabPbLeadTeamMember.getLeadTeamId());
                tabPbLeadTeamMapper.updateNum(tabPbLeadTeamMember.getLeadTeamId());
                retVal += iTabPbAttachmentService.intelligentOperation(tabPbLeadTeamMember.getTabPbAttachments(),
                        tabPbLeadTeamMember.getMemberId(), AttachmentType.portrait);
            }
            return retVal;
        }
    }

    /**
     * 新增职务
     * @param tabPbLeadTeamMember
     */
    private void addJobInformation(TabPbLeadTeamMember tabPbLeadTeamMember){
        List<TabPbPositives> tabPbPositivesList = calibrationPosition(tabPbLeadTeamMember);
        if(CollectionUtil.isEmpty(tabPbPositivesList)){
            TabPbPositives tabPbPositives = new TabPbPositives();
            tabPbPositives.setUserId(tabPbLeadTeamMember.getUserId());
            tabPbPositives.setPositiveOrgId(tabPbLeadTeamMember.getOrgId().intValue());
            tabPbPositives.setPositiveName(tabPbLeadTeamMember.getPositiveId());
            tabPbPositives.setPositiveType(59421);
            tabPbPositives.setPositiveStart(tabPbLeadTeamMember.getTenureBegin());
            tabPbPositives.setPositiveFinished(tabPbLeadTeamMember.getTenureLeave());
            tabPbPositives.setDescription(tabPbLeadTeamMember.getDescription());
            if(!StringUtil.isEmpty(tabPbLeadTeamMember.getRank())){
                tabPbPositives.setPositiveLevel(Long.parseLong(tabPbLeadTeamMember.getRank()));
            }
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbPositives);
            tabPbPositivesMapper.insertSelective(tabPbPositives);
        }else {
            throw new BusinessDataCheckFailException("职务已存在");
        }
    }

    /**
     * 查询职务信息
     * @param tabPbLeadTeamMember
     * @return
     */
    private List<TabPbPositives> calibrationPosition(TabPbLeadTeamMember tabPbLeadTeamMember){
        TabPbPositives tabPbPositives = new TabPbPositives();
        tabPbPositives.setUserId(tabPbLeadTeamMember.getUserId());
        if(tabPbLeadTeamMember.getOrgId() != null){
            tabPbPositives.setPositiveOrgId(tabPbLeadTeamMember.getOrgId().intValue());
        }
        //没有职务信息
        if(tabPbLeadTeamMember.getPositiveId() != null){
            tabPbPositives.setPositiveName(tabPbLeadTeamMember.getPositiveId());
        }else {
            return null;
        }
        return tabPbPositivesMapper.verifyDuplicateDuties(tabPbPositives);
    }

    /**
     * 删除职务
     * @param memberId
     */
    private void deleteMemberPositions(Long memberId){
        TabPbLeadTeamMember tabPbLeadTeamMember = tabPbLeadTeamMemberMapper.selectByPrimaryKey(memberId);
        List<TabPbPositives> tabPbPositivesList = calibrationPosition(tabPbLeadTeamMember);
        if(CollectionUtil.isNotEmpty(tabPbPositivesList)){
            for(TabPbPositives positives : tabPbPositivesList){
                if(positives.getPositiveOrgId() != null){
                    //如果user职务的组织id与领导班子成员的组织id相同,则删除职务信息
                    if(positives.getPositiveOrgId() == tabPbLeadTeamMember.getOrgId().intValue()){
                        positives.setDelFlag("1");
                        tabPbPositivesMapper.updateByPrimaryKeySelective(positives);
                    }
                }
            }
        }
    }

    @Override
    public TabPbLeadTeamMember selectByPrimaryKey(Long memberId) {
        return tabPbLeadTeamMemberMapper.selectByPrimaryKey(memberId);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(TabPbLeadTeamMember tabPbLeadTeamMember) {
        //先删除职务信息
        deleteMemberPositions(tabPbLeadTeamMember.getMemberId());
        //再添加职务信息
        addJobInformation(tabPbLeadTeamMember);
        tabPbLeadTeamMemberMapper.updateByPrimaryKeySelective(tabPbLeadTeamMember);
        int retVal = iTabPbAttachmentService.deleteByHostId(tabPbLeadTeamMember.getMemberId());
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbLeadTeamMember.getTabPbAttachments(),
                    tabPbLeadTeamMember.getMemberId(), AttachmentType.portrait);
        }
        return retVal;
    }

    @Override
    public int updateByPrimaryKey(TabPbLeadTeamMember tabPbLeadTeamMember) {
        return tabPbLeadTeamMemberMapper.updateByPrimaryKey(tabPbLeadTeamMember);
    }

    @Override
    @Transactional
    public int deleteId(Long memberId) {
        int retVal = tabPbLeadTeamMemberMapper.deleteId(memberId);
        //删除职务信息
        deleteMemberPositions(memberId);
        if (retVal > 0) {
            Long leadTeamId = tabPbLeadTeamMemberMapper.selectByLeadTeamId(memberId);
            Long counts = tabPbLeadTeamMapper.selectByNum(leadTeamId);
            Long count = counts - 1;
            tabPbLeadTeamMapper.updateById(leadTeamId, count);
        }
        return retVal;
    }

    @Override
    public List<TabPbLeadTeamMember> selectTeamMemberListByTeamId(Long leadTeamId, Page page) {
        PageHelper.startPage(page);
        List<TabPbLeadTeamMember> list = tabPbLeadTeamMemberMapper.selectTeamMemberListByTeamId(leadTeamId);
        return list;
    }

    @Override
    public int deleteMemberByMemberId(Long memberId) {

        return tabPbLeadTeamMemberMapper.deleteMemberByMemberId(memberId);

    }

    /**
     * 通过姓名,职务查询社区兼职委员列表
     *
     * @param conditions
     * @return
     */
    @Override
    public List<TabPbLeadTeamMemberDto> selectLeadTeamMemberByUser(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbLeadTeamMemberDto> leadTeamMemberDtos = tabPbLeadTeamMemberMapper.selectLeadTeamMemberByUser(conditions);
        return leadTeamMemberDtos;
    }

    @Override
    public TabPbLeadTeamMemberDto selectById(Long memberId) {
        return tabPbLeadTeamMemberMapper.selectById(memberId);
    }

    @Override
    public List<TabPbLeadTeamMemberDto> selectByTabPbLeadTeamDto(TabPbLeadTeamMemberDto tabPbLeadTeamMemberDto, Page page) {
        PageHelper.startPage(page);
        List<TabPbLeadTeamMemberDto> list = tabPbLeadTeamMemberMapper.selectByTabPbLeadTeamDto(tabPbLeadTeamMemberDto);
        return list;
    }

    /**
     * 查询上级组织领导班子成员
     *
     * @return
     */
    @Override
    public List<TabPbLeadTeamMemberDto> queryTheLeaderOfTheSuperiorOrganization(Long deptId, String personName, Page page) {
        PageHelper.startPage(page);
        return tabPbLeadTeamMemberMapper.queryTheLeaderOfTheSuperiorOrganization(deptId, personName);
    }

    /**
     * 编辑社区兼职委员
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(TabPbLeadTeamMemberDto record) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(record.getPersonName());
        sysUser.setPhone(record.getPhone());
        sysUser.setUserId(record.getUserId().intValue());
        sysUser.setTechnician(record.getPositiveId());
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        return tabPbLeadTeamMemberMapper.updateByPrimaryKeySelective(record);
    }
}
