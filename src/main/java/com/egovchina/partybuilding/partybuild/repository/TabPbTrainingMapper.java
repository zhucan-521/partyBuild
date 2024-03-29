package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.TabPbTrainingDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbTraining;
import com.egovchina.partybuilding.partybuild.entity.TrainingQueryBean;
import com.egovchina.partybuilding.partybuild.vo.TrainingVO;

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
     * 条件选择党员培训情况列表
     *
     * @param tabPbTrainingDto
     * @return
     */
    List<TrainingVO> selectiveTabPbTrainingVO(TrainingQueryBean tabPbTrainingDto);

    /**
     * 单个党员培训情况详情查询
     * @param traningId
     * @return
     */
    TabPbTrainingDto selectOneById(Long traningId);


    /**
     * 单个党员培训情况详情查询
     *
     * @param traningId
     * @return
     */
    TrainingVO selectOneVoById(Long traningId);


}