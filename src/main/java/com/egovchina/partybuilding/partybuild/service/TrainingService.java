package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.entity.TrainingQueryBean;
import com.egovchina.partybuilding.partybuild.dto.TrainingDTO;
import com.egovchina.partybuilding.partybuild.vo.TrainingVO;

import java.util.List;

public interface TrainingService {
    /**
     * 添加党员培训情况
     * @param tabPbTrainingDTO
     * @return
     */
    int addTraningDTO(TrainingDTO tabPbTrainingDTO);


    /**
     * 逻辑删除党员培训情况
     * @param traningId
     * @return
     */
    int deletTrainingDTO(Long traningId);

    /**
     * 列表条件查询党员培训情况
     * @param tabPbTrainingDto
     * @return
     */
    List<TrainingVO> getTrainingVOById(TrainingQueryBean tabPbTrainingDto);

    /**
     * 修改党员培训情况 必须附带主键id
     * @param tabPbTrainingDTO
     * @return
     */
    int updateTrainingDTO(TrainingDTO tabPbTrainingDTO);

    /**
     * 根据主键id单个详情查询培训情况
     * @param traningId
     * @return
     */
    TrainingVO getTrainingVOById(Long traningId);

}
