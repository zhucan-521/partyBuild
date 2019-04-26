package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.UnitInfoDTO;
import com.egovchina.partybuilding.partybuild.vo.UnitInfoVO;

import java.util.List;

public interface UnitInfoService {
    List<UnitInfoVO> selectUnitInfoByUnitName(String unitName, Page page);

    UnitInfoVO selectUnitInfoByUnitId(Long unitId);

    int insertUnitInfo(UnitInfoDTO unitInfoDTO);

    int deleteUnitInfo(Long unitId);

    int updateUnitInfo(UnitInfoDTO unitInfoDTO);

    List<UnitInfoVO> selectUnitInfoByOrgId(Long orgId);

    int updateBatchUnitInfo(List<UnitInfoDTO> unitInfoDTOList);
}
