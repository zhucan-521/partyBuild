package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbFlowOutDto;
import com.yizheng.partybuilding.system.entity.SysUser;

public interface  FlowOutService {

    /**
     * 流出党员列表条件查询
     * @param dto
     * @return
     */
    PageInfo<TabPbFlowOutDto> selectActiveTabPbFlowOutDto(TabPbFlowOutDto dto, Page page);


    /**
     * 登记流出党员信息
     * @param dto
     * @return 返回流出党员主键
     */
    int insert(TabPbFlowOutDto dto);


    /**
     * 修改流出党员
     * @param dto
     * @return
     */
    int update(TabPbFlowOutDto dto);


    /**
     * 单个查询详情
     * @param id
     * @return
     */
    TabPbFlowOutDto findOne(Long id);

    /**
     * 删除根据id
     * @param id
     * @return
     */
    int delete(Long id);


}
