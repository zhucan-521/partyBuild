package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.partybuild.dto.UnitInfoDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.egovchina.partybuilding.partybuild.repository.TabPbUnitInfoMapper;
import com.egovchina.partybuilding.partybuild.service.UnitInfoService;
import com.egovchina.partybuilding.partybuild.vo.UnitInfoVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.copyListPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.BeanUtil.copyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;

/**
 * 描述:
 * 单位管理实现类
 *
 * @outhor asd
 * @create 2018-12-11 18:07
 */
@Service
public class UnitInfoServiceImpl implements UnitInfoService {
    @Autowired
    private TabPbUnitInfoMapper tabPbUnitInfoMapper;

    @Override
    public List<UnitInfoVO> selectUnitInfoByUnitName(String unitName, Page page) {
        PageHelper.startPage(page);
        return tabPbUnitInfoMapper.selectUnitInfoByUnitName(unitName);
    }

    @Override
    public UnitInfoVO selectUnitInfoByUnitId(Long unitId) {
        return tabPbUnitInfoMapper.selectUnitInfoByUnitId(unitId);
    }

    @Override
    public int insertUnitInfo(UnitInfoDTO unitInfoDTO) {
        TabPbUnitInfo tabPbUnitInfo =
                copyPropertiesAndPaddingBaseField(
                        unitInfoDTO, TabPbUnitInfo.class, true, false);
        return tabPbUnitInfoMapper.insertSelective(tabPbUnitInfo);
    }

    @Override
    public int deleteUnitInfo(Long unitId) {
        TabPbUnitInfo tabPbUnitInfo = new TabPbUnitInfo();
        tabPbUnitInfo.setUnitId(unitId);
        tabPbUnitInfo.setDelFlag("1");
        paddingUpdateRelatedBaseFiled(tabPbUnitInfo);
        return tabPbUnitInfoMapper.updateByPrimaryKeySelective(tabPbUnitInfo);
    }

    @Override
    public int updateUnitInfo(UnitInfoDTO unitInfoDTO) {
        int count = 0;
        if (tabPbUnitInfoMapper.selectByPrimaryKey(unitInfoDTO.getUnitId()) != null) {
            TabPbUnitInfo tabPbUnitInfo =
                    copyPropertiesAndPaddingBaseField(
                            unitInfoDTO, TabPbUnitInfo.class, true, true);
            count += tabPbUnitInfoMapper.updateByPrimaryKeySelective(tabPbUnitInfo);
        } else {
            throw new BusinessDataNotFoundException("该单位不存在");
        }
        return count;
    }

    @Override
    public List<UnitInfoVO> selectUnitInfoByOrgId(Long orgId) {
        return tabPbUnitInfoMapper.selectUnitInfoByOrgId(orgId);
    }

    @Override
    @Transactional
    public int updateBatchUnitInfo(List<UnitInfoDTO> unitInfoDTOList) {
        int count = 0;
        if (CollectionUtil.isEmpty(unitInfoDTOList)) {
            return count;
        }
        List<TabPbUnitInfo> tabPbUnitInfos = copyListPropertiesAndPaddingBaseField(
                unitInfoDTOList, TabPbUnitInfo.class, true, true);
        for (TabPbUnitInfo tabPbUnitInfo : tabPbUnitInfos) {
            count += tabPbUnitInfoMapper.updateByPrimaryKeySelective(tabPbUnitInfo);
        }
        return count;
    }
}
