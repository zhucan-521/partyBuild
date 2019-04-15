package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.partybuild.entity.TabPbSubjectTag;
import com.egovchina.partybuilding.partybuild.repository.TabPbSubjectTagMapper;
import com.egovchina.partybuilding.partybuild.service.TabPbSubjectTagService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Jiang An
 **/
@Service
public class TabPbSubjectTagServiceImpl implements TabPbSubjectTagService {


    @Autowired
    private TabPbSubjectTagMapper subjectMapper;

    /**
     * 删除标签
     *
     * @param tagId
     * @return
     */
    @Override
    public int deleteByTagId(Long tagId) {
        return subjectMapper.deleteByTagId(tagId);
    }


    /**
     * 添加主题标签
     *
     * @param subjectTag
     * @return
     */
    @PaddingBaseField
    @Override
    public int add(TabPbSubjectTag subjectTag) {
        //判断
        verify(subjectTag);
        return subjectMapper.insertSelective(subjectTag);
    }

    /**
     * 查询主题标签
     *
     * @param tagId
     * @return
     */
    @Override
    public TabPbSubjectTag selectByTagId(Long tagId) {
        return subjectMapper.selectByTagId(tagId);
    }

    /**
     * 修改主题标签
     *
     * @param subjectTag
     * @return
     */
    @PaddingBaseField
    @Override
    public int update(TabPbSubjectTag subjectTag) {
        //判断
        verify(subjectTag);
        return subjectMapper.updateByPrimaryKey(subjectTag);
    }



    /**
     * 根据活动类型查询主题标签
     *
     * @param
     * @return
     */
    @Override
    public List<TabPbSubjectTag> findByRange(List<Integer> list) {
        List<TabPbSubjectTag> byRange = subjectMapper.findByRange(list);
        return byRange;
    }



    /**
     * 分页查询标签
     * @param conditions
     * @return
     */
    @Override
    public List<TabPbSubjectTag> findByList(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbSubjectTag>list = subjectMapper.findByList(conditions);
        return list;
    }





    public void verify(TabPbSubjectTag subjectTag) {
        if (subjectTag.getStatisticFlag() == null) {
            throw new BusinessDataNotFoundException("统计不能为空");
        }
        if (subjectTag.getDelFlag() == null) {
            throw new BusinessDataNotFoundException("停用不能为空");
        }
        if (subjectTag.getName() == null) {
            throw new BusinessDataNotFoundException("标签名不能为空");
        }
    }
}
