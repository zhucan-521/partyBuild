package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.partybuild.dto.PartyResourceDto;
import com.github.pagehelper.PageInfo;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/10
 */
public interface TabPbLearnManagerService {

    /**
     * 获取学习管理相关业务附件信息
     *
     * @return
     */
     PageInfo<PartyResourceDto> find(TabPbAttachment atta, Page page);

}
