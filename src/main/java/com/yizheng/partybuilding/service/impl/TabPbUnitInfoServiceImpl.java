package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbUnitInfo;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.partybuilding.repository.TabPbUnitInfoMapper;
import com.yizheng.partybuilding.service.inf.ITabPbUnitInfoService;
import com.yizheng.commons.util.CollectionUtil;
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
