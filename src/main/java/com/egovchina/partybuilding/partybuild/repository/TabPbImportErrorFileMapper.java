package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbImportErrorFile;
import org.springframework.stereotype.Repository;

@Repository
public interface TabPbImportErrorFileMapper {
    int insert(TabPbImportErrorFile record);

    int insertSelective(TabPbImportErrorFile record);
}