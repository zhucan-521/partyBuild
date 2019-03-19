package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbDeptSecretary;

import java.util.List;

/**
 * @author YangYingXiang on 2019/03/01
 */
public interface ITabPbDeptSecretaryService {
    int insertSelective(TabPbDeptSecretary record);

    TabPbDeptSecretary selectByPrimaryKey(Long secretaryId);

    int updateByPrimaryKeySelective(TabPbDeptSecretary record);

    public int tombstone(Long secretaryId);

    /**
     * 返回list
     * @param record
     * @return
     */
    List<TabPbDeptSecretary> selectList(TabPbDeptSecretary record, Page page);
}
