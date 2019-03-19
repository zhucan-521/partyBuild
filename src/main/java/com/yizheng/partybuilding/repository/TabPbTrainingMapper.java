package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TabPbTrainingDto;
import com.yizheng.partybuilding.entity.TabPbTraining;

import java.util.List;

public interface TabPbTrainingMapper {
    int deleteByPrimaryKey(Long traningId);

    int insert(TabPbTraining record);

    int insertSelective(TabPbTraining record);

    TabPbTraining selectByPrimaryKey(Long traningId);

    int updateByPrimaryKeySelective(TabPbTraining record);

    int updateByPrimaryKeyWithBLOBs(TabPbTraining record);

    int updateByPrimaryKey(TabPbTraining record);

    /**
     * 条件选择党员培训情况列表
     * @param tabPbTrainingDto
     * @return
     */
    List<TabPbTrainingDto> selectiveTabPbTrainingDto(TabPbTrainingDto tabPbTrainingDto);

    /**
     * 单个党员培训情况详情查询
     * @param traningId
     * @return
     */
    TabPbTrainingDto selectOneById(Long traningId);

}