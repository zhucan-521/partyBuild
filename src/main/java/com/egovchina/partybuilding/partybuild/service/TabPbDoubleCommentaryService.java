package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbDoubleCommentary;

import java.util.List;
import java.util.Map;

/**
 * 双述双评service接口
 *
 * @Author Zhang Fan
 **/
public interface TabPbDoubleCommentaryService {

    int deleteByPrimaryKey(Long commentaryId);

    int insert(TabPbDoubleCommentary record);

    int insertSelective(TabPbDoubleCommentary record);

    TabPbDoubleCommentary selectByPrimaryKey(Long commentaryId);

    int updateByPrimaryKeySelective(TabPbDoubleCommentary record);

    int updateByPrimaryKeyWithBLOBs(TabPbDoubleCommentary record);

    int updateByPrimaryKey(TabPbDoubleCommentary record);

    boolean existsDoubleCommentary(Long planYear, Long orgId);

    int insertWithAnnexs(TabPbDoubleCommentary tabPbDoubleCommentary);

    List<TabPbDoubleCommentary> selectWithConditions(Map<String, Object> conditions, Page page);

    int updateWithAnnexs(TabPbDoubleCommentary tabPbDoubleCommentary);

    int logicDeleteById(Long commentaryId);

    int review(TabPbDoubleCommentary update);
}
