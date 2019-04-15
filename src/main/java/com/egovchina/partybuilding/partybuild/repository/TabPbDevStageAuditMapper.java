package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbDevStageAudit;

public interface TabPbDevStageAuditMapper {
    int deleteByPrimaryKey(Long auditId);

    int insert(TabPbDevStageAudit record);

    int insertSelective(TabPbDevStageAudit record);

    TabPbDevStageAudit selectByPrimaryKey(Long auditId);

    int updateByPrimaryKeySelective(TabPbDevStageAudit record);

    int updateByPrimaryKeyWithBLOBs(TabPbDevStageAudit record);

    int updateByPrimaryKey(TabPbDevStageAudit record);
}