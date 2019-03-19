package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.PartyResourceDto;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.repository.TabPbAttachmentMapper;
import com.yizheng.partybuilding.service.inf.ITabPbLearnManagerService;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/10
 */
@Service
public class TabPbLearnManagerServiceImpl implements ITabPbLearnManagerService {

    @Autowired
    private TabPbAttachmentMapper mapper;

    @Override
    public PageInfo<PartyResourceDto> find(TabPbAttachment atta, Page page) {
        if (page != null) {
            PageHelper.startPage(page);
        }
        var query = mapper.selectBySomeCondition(atta);
        var list = new ArrayList<PartyResourceDto>(query.size());
        query.forEach(v -> {
            var dto = new PartyResourceDto();
            list.add(dto);
            BeanUtils.copyProperties(v, dto);
        });
        return new PageInfo<>(list);
    }

}
