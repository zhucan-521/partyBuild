package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.egovchina.partybuilding.partybuild.repository.TabPbUnitInfoMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbUnitInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 * 单位管理实现类
 *
 * @outhor asd
 * @create 2018-12-11 18:07
 */
@Service
public class TabPbUnitInfoServiceImpl implements ITabPbUnitInfoService {
    @Autowired
    private TabPbUnitInfoMapper tabPbUnitInfoMapper;

    @Override
    public List<TabPbUnitInfo> selectByUnitName(String unitName, Page page) {
        PageHelper.startPage(page);
        return tabPbUnitInfoMapper.selectByUnitName(unitName);
    }

    @Override
    public TabPbUnitInfo selectByUnitId(Long unitId) {
        return tabPbUnitInfoMapper.selectByPrimaryKey(unitId);
    }

    @Override
    public int insertUnitInfo(TabPbUnitInfo tabPbUnitInfo) {
        return tabPbUnitInfoMapper.insertSelective(tabPbUnitInfo);
    }

    @Override
    public int deleteUnitInfo(Long unitId) {
        TabPbUnitInfo tabPbUnitInfo = new TabPbUnitInfo();
        tabPbUnitInfo.setUnitId(unitId);
        tabPbUnitInfo.setDelFlag("1");
        return tabPbUnitInfoMapper.updateByPrimaryKeySelective(tabPbUnitInfo);
    }

    @Override
    public int updateUnitInfo(TabPbUnitInfo tabPbUnitInfo) {
        int retVal = 0;
        if (tabPbUnitInfoMapper.selectByPrimaryKey(tabPbUnitInfo.getUnitId()) != null) {
            retVal += tabPbUnitInfoMapper.updateByPrimaryKeySelective(tabPbUnitInfo);
        } else {
            throw new BusinessDataNotFoundException("该单位不存在");
        }
        return retVal;
    }

    @Override
    public List<TabPbUnitInfo> selectByOrgId(Long orgId) {
        return tabPbUnitInfoMapper.selectByOrgId(orgId);
    }

    @PaddingBaseField
    @Override
    public int batchUpdateUnitInfo(List<TabPbUnitInfo> tabPbUnitInfoList) {
        int retVal = 0;
        if (CollectionUtil.isEmpty(tabPbUnitInfoList)) {
            return retVal;
        }
        for (TabPbUnitInfo tabPbUnitInfo : tabPbUnitInfoList) {
            retVal += tabPbUnitInfoMapper.updateByPrimaryKeySelective(tabPbUnitInfo);
        }
        return retVal;
    }
}
