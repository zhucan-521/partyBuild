package com.yizheng.partybuilding.service.impl;

import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.partybuilding.entity.TabPbSubjectTag;
import com.yizheng.partybuilding.repository.TabPbSubjectTagMapper;
import com.yizheng.partybuilding.service.inf.TabPbSubjectTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
