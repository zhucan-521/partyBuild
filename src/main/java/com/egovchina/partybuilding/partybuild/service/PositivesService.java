package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.PositivesDTO;
import com.egovchina.partybuilding.partybuild.vo.PositivesVO;

import java.util.List;
import java.util.Map;

public interface PositivesService {
    int insertPositives(PositivesDTO positives);

    int deleteById(Integer id);

    int updateById(PositivesDTO positives);

    PositivesVO selectPositiveVOById(Integer id);

    List<PositivesVO> selectPositives(Long userId ,String positiveType);
}
