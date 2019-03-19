package com.yizheng.partybuilding.service.inf;

import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbDoubleCommentary;

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
