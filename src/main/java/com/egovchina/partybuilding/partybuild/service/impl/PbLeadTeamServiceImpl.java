package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TabPbLeadTeamDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeam;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeamMember;
import com.egovchina.partybuilding.partybuild.repository.TabPbLeadTeamMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbLeadTeamMemberMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.PbLeadTeamService;
import com.egovchina.partybuilding.partybuild.service.TabPbLeadTeamMemberService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Author Jiang An
 **/
@Service
public class PbLeadTeamServiceImpl implements PbLeadTeamService {

    @Autowired
    private TabPbLeadTeamMapper tabPbLeadTeamMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;
    @Autowired
    private TabPbLeadTeamMemberMapper tabPbLeadTeamMemberMapper;
    @Autowired
    private TabPbLeadTeamMemberService tabPbLeadTeamMemberService;

    @Override
    public int deleteByPrimaryKey(Long leadTeamId) {
        return tabPbLeadTeamMapper.deleteByPrimaryKey(leadTeamId);
    }

    //增加领导班子
    @Transactional
    @Override
    public int insert(TabPbLeadTeam tabPbLeadTeam) throws ParseException {

        //判断2个字段不能为空
        if (tabPbLeadTeam.getCurrent() == null) {
            throw new BusinessDataIncompleteException("单届标识不能为空");
        }
        if (tabPbLeadTeam.getDuringYear() == null) {
            throw new BusinessDataIncompleteException("任期年限不能为空");
        }
        //添加党组织主键
        Long orgId = tabPbLeadTeam.getOrgId();
        if (orgId == null || orgId == 0) {
            tabPbLeadTeam.setOrgId(UserContextHolder.getOrgId()); //没传，默认当前
        }
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(tabPbLeadTeam.getElectedTime());
        calendar.add(Calendar.YEAR, tabPbLeadTeam.getDuringYear().intValue());
        tabPbLeadTeam.setChangeDate(calendar.getTime());
        int insert = tabPbLeadTeamMapper.insertSelective(tabPbLeadTeam);
        if (insert > 0) {
            insert += iTabPbAttachmentService.intelligentOperation(tabPbLeadTeam.getAttachments(),
                    tabPbLeadTeam.getLeadTeamId(), AttachmentType.LEADERSHIP_TEAM);
        }
        return insert;
    }

    @Override
    public int insertSelective(TabPbLeadTeam record) {
        return tabPbLeadTeamMapper.insertSelective(record);
    }

    //查询单个
    @Override
    public TabPbLeadTeamDto selectByPrimaryKey(Long leadTeamId) {
        return tabPbLeadTeamMapper.selectByPrimaryKey(leadTeamId);
    }

    @Override
    public int updateByPrimaryKeySelective(TabPbLeadTeam record) {
        return tabPbLeadTeamMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(TabPbLeadTeam record) {
        return tabPbLeadTeamMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(TabPbLeadTeam record) {
        return tabPbLeadTeamMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TabPbLeadTeamDto> select(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbLeadTeamDto> tabPbLeadTeams = tabPbLeadTeamMapper.select(conditions);
        return tabPbLeadTeams;
    }

    @Override
    @Transactional
    public int deleteId(Long leadTeamId) {
        List<TabPbLeadTeamMember> list = tabPbLeadTeamMemberMapper.queryTeamMembers(leadTeamId);
        //删除班子成员
        if(CollectionUtil.isNotEmpty(list)){
            list.forEach(tabPbLeadTeamMember ->
                    tabPbLeadTeamMemberService.deleteId(tabPbLeadTeamMember.getMemberId())
            );
        }
        return tabPbLeadTeamMapper.deleteId(leadTeamId);
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int updateWithAboutInfo(TabPbLeadTeam tabPbLeadTeam) {
        int retVal = tabPbLeadTeamMapper.updateByPrimaryKeySelective(tabPbLeadTeam);
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbLeadTeam.getAttachments(),
                    tabPbLeadTeam.getLeadTeamId(), AttachmentType.LEADERSHIP_TEAM);
        }
        return retVal;
    }
}
