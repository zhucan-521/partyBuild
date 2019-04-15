package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.TabPbFlowInDto;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface TabPbFlowInService {


    /**
     * 列表查询
     * @param params
     * @param page
     * @return
     */
    PageInfo<TabPbFlowInDto> select(Map<String, Object> params, Page page);

    /**
     * 根据流入id删除
     * @param flowInId
     * @return
     */
    int deleteByPrimaryKey(Long flowInId);

    /**
     * 录入流入党员
     * @param
     * @return
     */
    int insert(TabPbFlowInDto tabPbFlowInDto);

    /**
     * 修改
     * @param tabPbFlowInDto
     * @return
     */
    int updateTabPbFlowInDto(TabPbFlowInDto tabPbFlowInDto);

    /**
     * 返回登记
     * @param
     * @return
     */
    int updateEndFlow(TabPbFlowInDto tabPbFlowInDto);


    /**
     * 单个详情查询
     * @param flowInId
     * @return
     */
    TabPbFlowInDto selectFlowInById(Long flowInId);



}
