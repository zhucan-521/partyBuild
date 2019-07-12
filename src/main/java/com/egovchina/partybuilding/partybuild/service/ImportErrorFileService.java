package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.ImportErrorFileDTO;


public interface ImportErrorFileService {
    /**
     * 新增错误文件信息
     *
     * @param importErrorFileDTO
     * @return
     */
    int insertImportErrorFile(ImportErrorFileDTO importErrorFileDTO);
}
