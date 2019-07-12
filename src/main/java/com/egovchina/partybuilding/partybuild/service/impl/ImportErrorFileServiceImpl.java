package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.partybuild.dto.ImportErrorFileDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbImportErrorFile;
import com.egovchina.partybuilding.partybuild.repository.TabPbImportErrorFileMapper;
import com.egovchina.partybuilding.partybuild.service.ImportErrorFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportErrorFileServiceImpl implements ImportErrorFileService {
    @Autowired
    private TabPbImportErrorFileMapper tabPbImportErrorFileMapper;

    @Override
    public int insertImportErrorFile(ImportErrorFileDTO importErrorFileDTO) {
        TabPbImportErrorFile tabPbImportErrorFile = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(importErrorFileDTO, TabPbImportErrorFile.class, false);
        return tabPbImportErrorFileMapper.insertSelective(tabPbImportErrorFile);
    }
}
