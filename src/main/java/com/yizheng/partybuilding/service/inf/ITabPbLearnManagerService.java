package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.PartyResourceDto;
import com.yizheng.commons.domain.TabPbAttachment;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/10
 */
public interface ITabPbLearnManagerService {

    /**
     * 获取学习管理相关业务附件信息
     *
     * @return
     */
     PageInfo<PartyResourceDto> find(TabPbAttachment atta, Page page);

}
