package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.util.BeanUtil;
import com.yizheng.partybuilding.dto.AssessUserDto;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbActivitiesAssessDto;
import com.yizheng.partybuilding.entity.TabPbActivitiesAssess;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.repository.TabPbActivitiesAssessMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.TabPbActivitiesAssessService;
import com.yizheng.commons.util.AttachmentType;
import com.yizheng.commons.util.CollectionUtil;
import com.yizheng.commons.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 民主评议service层
 *
 * @Author Zhang Fan
 **/
@Service("tabPbActivitiesAssessService")
public class TabPbActivitiesAssessServiceImpl implements TabPbActivitiesAssessService {

    @Autowired
    private TabPbActivitiesAssessMapper tabPbActivitiesAssessMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public List<TabPbActivitiesAssessDto> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return tabPbActivitiesAssessMapper.selectWithConditions(conditions);
    }

    @Override
    public TabPbActivitiesAssess selectByUserIdAndYear(Long userId, Integer year) {
        return tabPbActivitiesAssessMapper.selectByUserIdAndYear(userId, year);
    }

    @Override
    public TabPbActivitiesAssessDto detailWithAbout(Long id) {
        return tabPbActivitiesAssessMapper.detailWithAbout(id);
    }

    @Transactional
    @Override
    public int insertWithAbout(TabPbActivitiesAssessDto tabPbActivitiesAssess) {
        tabPbActivitiesAssess.setHostId(IdWorker.getLongId());
        List<TabPbActivitiesAssess> pendingInsertList = new ArrayList<>();
        //正式党员
        List<AssessUserDto> formalUsers = tabPbActivitiesAssess.getFormalUsers();
        if (CollectionUtil.isNotEmpty(formalUsers)) {
            formalUsers.forEach(formal -> {
                TabPbActivitiesAssess pendingAssess = new TabPbActivitiesAssess();
                BeanUtil.copyPropertiesIgnoreNull(tabPbActivitiesAssess, pendingAssess);
                pendingAssess.setUserId(formal.getUserId());
                pendingAssess.setUsername(formal.getUsername());
                pendingAssess.setAssessResult(tabPbActivitiesAssess.getFormalResult());
                pendingAssess.setType("2"); //正式党员
                pendingInsertList.add(pendingAssess);
            });
        }
        //预备党员
        List<AssessUserDto> preUsers = tabPbActivitiesAssess.getPreUsers();
        if (CollectionUtil.isNotEmpty(preUsers)) {
            preUsers.forEach(pre -> {
                TabPbActivitiesAssess pendingAssess = new TabPbActivitiesAssess();
                BeanUtil.copyPropertiesIgnoreNull(tabPbActivitiesAssess, pendingAssess);
                pendingAssess.setUserId(pre.getUserId());
                pendingAssess.setUsername(pre.getUsername());
                pendingAssess.setAssessResult(tabPbActivitiesAssess.getPreResult());
                pendingAssess.setType("1"); //预备党员
                pendingInsertList.add(pendingAssess);
            });
        }
        int retVal = tabPbActivitiesAssessMapper.batchInsert(pendingInsertList);
        if (retVal > 0) {
            retVal += modifyAttachment(tabPbActivitiesAssess);
        }
        return retVal;
    }

    @Override
    public int updateWithAbout(TabPbActivitiesAssess tabPbActivitiesAssess) {
        int retVal = tabPbActivitiesAssessMapper.updateByPrimaryKeySelective(tabPbActivitiesAssess);
        if (retVal > 0) {
            retVal += modifyAttachment(tabPbActivitiesAssess);
        }
        return retVal;
    }

    @Override
    public HashMap<String, List<AssessUserDto>> preAssessMemberList(Map<String, Object> conditions) {
        HashMap<String, List<AssessUserDto>> result = new HashMap<>();
        conditions.put("identity_type", "223");//正式党员
        List<AssessUserDto> formalMemberList = tabPbActivitiesAssessMapper.selectPreAssessMemberWithConditions(conditions);
        conditions.put("identity_type", "224");//预备党员
        List<AssessUserDto> preMemberList = tabPbActivitiesAssessMapper.selectPreAssessMemberWithConditions(conditions);
        result.put("formalMembers", formalMemberList);
        result.put("preMembers", preMemberList);
        return result;
    }

    @Override
    public int signInById(Long id) {
        return tabPbActivitiesAssessMapper.signInById(id);
    }

    /**
     * 维护附件
     *
     * @param tabPbActivitiesAssess
     * @return
     */
    private int modifyAttachment(TabPbActivitiesAssess tabPbActivitiesAssess) {
        List<TabPbAttachment> docAttachments = tabPbActivitiesAssess.getDocAttachments();
        List<TabPbAttachment> imgAttachments = tabPbActivitiesAssess.getImgAttachments();
        List<TabPbAttachment> videoAttachments = tabPbActivitiesAssess.getVideoAttachments();
        List<TabPbAttachment> attachmentList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(docAttachments)) {
            attachmentList.addAll(docAttachments);
        }
        if (CollectionUtil.isNotEmpty(imgAttachments)) {
            attachmentList.addAll(imgAttachments);
        }
        if (CollectionUtil.isNotEmpty(videoAttachments)) {
            attachmentList.addAll(videoAttachments);
        }
        if (CollectionUtil.isNotEmpty(attachmentList)) {
            return iTabPbAttachmentService.intelligentOperation(attachmentList,
                    tabPbActivitiesAssess.getHostId(), AttachmentType.PartyMaster);
        }
        return 0;
    }

    @Override
    public int logicDeleteById(Long id) {
        return tabPbActivitiesAssessMapper.logicDeleteById(id);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return tabPbActivitiesAssessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TabPbActivitiesAssess record) {
        return tabPbActivitiesAssessMapper.insert(record);
    }

    @Override
    public int insertSelective(TabPbActivitiesAssess record) {
        return tabPbActivitiesAssessMapper.insertSelective(record);
    }

    @Override
    public TabPbActivitiesAssess selectByPrimaryKey(Long id) {
        return tabPbActivitiesAssessMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TabPbActivitiesAssess record) {
        return tabPbActivitiesAssessMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TabPbActivitiesAssess record) {
        return tabPbActivitiesAssessMapper.updateByPrimaryKey(record);
    }
}
