package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.SpecialWorkerDTO;
import com.egovchina.partybuilding.partybuild.entity.SpecialWorkerQueryBean;
import com.egovchina.partybuilding.partybuild.vo.SpecialWorkerVO;
import com.github.pagehelper.PageInfo;

public interface SpecialWorkerService {

    /**
     * 新增专干
     *
     * @param specialWorkerDto
     * @return
     */
    int insert(SpecialWorkerDTO specialWorkerDto);

    /**
     * 删除专干
     *
     * @param specialWorkerId
     * @return
     */
    int deleteBySpecialWorkerId(Long specialWorkerId);

    /**
     * 列表专干党员查询
     *
     * @param page
     * @param specialWorkerQueryBean
     * @return
     */
    PageInfo<SpecialWorkerVO> getSpecialWorkerList(Page page, SpecialWorkerQueryBean specialWorkerQueryBean);

    /**
     * 修改专干党员
     *
     * @param specialWorkerDto
     * @return
     */
    int updateBySpecialWorkerId(SpecialWorkerDTO specialWorkerDto);

    /**
     * 详情查询
     *
     * @param specialWorkerId
     * @return
     */
    SpecialWorkerVO selectSpecialWorkerById(Long specialWorkerId);

}
