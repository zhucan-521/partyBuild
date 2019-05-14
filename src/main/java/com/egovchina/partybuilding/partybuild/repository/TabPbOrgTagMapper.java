package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbOrgTag;
import com.egovchina.partybuilding.partybuild.vo.OrgTagVO;

import java.util.List;

public interface TabPbOrgTagMapper {
    int deleteByPrimaryKey(Long orgTagId);

    int insert(TabPbOrgTag record);

    int insertSelective(TabPbOrgTag record);

    TabPbOrgTag selectByPrimaryKey(Long orgTagId);

    int updateByPrimaryKeySelective(TabPbOrgTag record);

    int updateByPrimaryKey(TabPbOrgTag record);

    int batchUpdateOrgTagByOrgId(TabPbOrgTag tabPbOrgTag);

    int batchInsertSelective(List<TabPbOrgTag> tabPbOrgTags);

    List<TabPbOrgTag> selectByOrgId(Long orgId);

    int batchDelOrgTag(List<TabPbOrgTag> deleteOrgTags);

    List<OrgTagVO> selectOrgTagByOrgId(Long orgId);
}