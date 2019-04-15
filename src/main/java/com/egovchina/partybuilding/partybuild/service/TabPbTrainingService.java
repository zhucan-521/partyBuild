package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.TabPbTrainingDto;

import java.util.List;

public interface TabPbTrainingService {
    /**
     * 添加党员培训情况
     * @param tabPbTrainingDto
     * @return
     */
    int insert(TabPbTrainingDto tabPbTrainingDto);


    /**
     * 逻辑删除党员培训情况
     * @param traningId
     * @return
     */
    int delete(Long traningId);

    /**
     * 列表条件查询党员培训情况
     * @param tabPbTrainingDto
     * @return
     */
    List<TabPbTrainingDto> select(TabPbTrainingDto tabPbTrainingDto);

    /**
     * 修改党员培训情况 必须附带主键id
     * @param tabPbTrainingDto
     * @return
     */
    int update(TabPbTrainingDto tabPbTrainingDto);

    /**
     * 根据主键id单个详情查询培训情况
     * @param traningId
     * @return
     */
    TabPbTrainingDto selectTabPbTrainingDtoById(Long traningId);

}
