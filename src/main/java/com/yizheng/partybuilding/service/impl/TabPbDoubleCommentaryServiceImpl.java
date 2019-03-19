package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.util.AttachmentType;
import com.yizheng.commons.util.PaddingBaseFieldUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbDoubleCommentary;
import com.yizheng.partybuilding.repository.TabPbDoubleCommentaryMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.TabPbDoubleCommentaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author Zhang Fan
 **/
@Service("tabPbDoubleCommentaryService")
public class TabPbDoubleCommentaryServiceImpl implements TabPbDoubleCommentaryService {

    @Autowired
    private TabPbDoubleCommentaryMapper tabPbDoubleCommentaryMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public int deleteByPrimaryKey(Long commentaryId) {
        return tabPbDoubleCommentaryMapper.deleteByPrimaryKey(commentaryId);
    }

    @Override
    public int insert(TabPbDoubleCommentary record) {
        return tabPbDoubleCommentaryMapper.insert(record);
    }

    @Override
    public int insertSelective(TabPbDoubleCommentary record) {
        return tabPbDoubleCommentaryMapper.insertSelective(record);
    }

    @Override
    public TabPbDoubleCommentary selectByPrimaryKey(Long commentaryId) {
        return tabPbDoubleCommentaryMapper.selectByPrimaryKey(commentaryId);
    }

    @Override
    public int updateByPrimaryKeySelective(TabPbDoubleCommentary record) {
        return tabPbDoubleCommentaryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(TabPbDoubleCommentary record) {
        return tabPbDoubleCommentaryMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(TabPbDoubleCommentary record) {
        return tabPbDoubleCommentaryMapper.updateByPrimaryKey(record);
    }

    @Override
    public boolean existsDoubleCommentary(Long planYear, Long orgId) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("planYear", planYear);
        conditions.put("orgId", orgId);
        conditions.put("delFlag", "0");
        return tabPbDoubleCommentaryMapper.selectWithConditions(conditions).size() > 0;
    }

    @Transactional
    @Override
    public int insertWithAnnexs(TabPbDoubleCommentary tabPbDoubleCommentary) {
        tabPbDoubleCommentary.setCheckDate(new Date());
        int retVal = tabPbDoubleCommentaryMapper.insertSelective(tabPbDoubleCommentary);
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbDoubleCommentary.getTabPbAttachments(),
                    tabPbDoubleCommentary.getCommentaryId(), AttachmentType.DOUBLE_COMMENTARY);
        }
        return retVal;
    }

    @Override
    public List<TabPbDoubleCommentary> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbDoubleCommentary> list = tabPbDoubleCommentaryMapper.selectWithConditions(conditions);
        return list;
    }

    @Transactional
    @Override
    public int updateWithAnnexs(TabPbDoubleCommentary tabPbDoubleCommentary) {
        int retVal = tabPbDoubleCommentaryMapper.updateByPrimaryKeySelective(tabPbDoubleCommentary);
        if (retVal > 0) {
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbDoubleCommentary.getTabPbAttachments(),
                    tabPbDoubleCommentary.getCommentaryId(), AttachmentType.DOUBLE_COMMENTARY);
        }
        return retVal;
    }

    @Override
    public int logicDeleteById(Long commentaryId) {
        return tabPbDoubleCommentaryMapper.logicDeleteById(commentaryId);
    }

    @Override
    public int review(TabPbDoubleCommentary update) {
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(update);
        return tabPbDoubleCommentaryMapper.updateByPrimaryKeySelective(update);
    }

}
