package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabSpecialWorkerResultDto;

public interface TabSpecialWorkerService {


    /**
     * 新增专干
     * @param dto
     * @return
     */
    int insert(TabSpecialWorkerResultDto dto);

    /**
     * 删除专干
     * @param specialWorkerId
     * @return
     */
    int deleteBySpecialWorkerId(Long specialWorkerId);

    /**
     * 列表专干党员查询
     * @param page
     * @param dto
     * @return
     */
    PageInfo<TabSpecialWorkerResultDto> selectAllTabSpecialWorkerDto(Page page,TabSpecialWorkerResultDto dto);

    /**
     * 修改专干党员
     * @param dto
     * @return
     */
    int updateBySpecialWorkerId(TabSpecialWorkerResultDto dto);


    /**
     * 详情查询
     * @param specialWorkerId
     * @return
     */
    TabSpecialWorkerResultDto selectOneById(Long specialWorkerId);


}
