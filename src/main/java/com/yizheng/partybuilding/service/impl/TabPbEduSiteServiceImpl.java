package com.yizheng.partybuilding.service.impl;

import com.github.pagehelper.PageHelper;
import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.util.AttachmentType;
import com.yizheng.commons.util.CollectionUtil;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbEduSiteDto;
import com.yizheng.partybuilding.entity.TabPbEduSite;
import com.yizheng.partybuilding.entity.TabPbEduSiteActivity;
import com.yizheng.partybuilding.entity.TabPbEduSiteDevice;
import com.yizheng.partybuilding.repository.TabPbEduSiteActivityMapper;
import com.yizheng.partybuilding.repository.TabPbEduSiteDeviceMapper;
import com.yizheng.partybuilding.repository.TabPbEduSiteMapper;
import com.yizheng.partybuilding.service.inf.ITabPbAttachmentService;
import com.yizheng.partybuilding.service.inf.TabPbEduSiteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 远教站点service实现
 *
 * @Author Zhang Fan
 **/
@Service("tabPbEduSiteService")
public class TabPbEduSiteServiceImpl implements TabPbEduSiteService {

    @Autowired
    private TabPbEduSiteMapper tabPbEduSiteMapper;
    @Autowired
    private TabPbEduSiteActivityMapper tabPbEduSiteActivityMapper;
    @Autowired
    private TabPbEduSiteDeviceMapper tabPbEduSiteDeviceMapper;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public List<TabPbEduSite> selectWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return tabPbEduSiteMapper.selectWithConditions(conditions);
    }

    @Override
    public TabPbEduSiteDto selectWithAboutInfoById(Long id) {
        return tabPbEduSiteMapper.selectWithAboutInfoById(id);
    }

    @Override
    public int logicDeleteById(Long id) {
        return tabPbEduSiteMapper.logicDeleteById(id);
    }

    @Transactional
    @Override
    public int insertWithAbout(TabPbEduSiteDto tabPbEduSite) {
        //新增站点信息
        int retVal = tabPbEduSiteMapper.insertSelective(tabPbEduSite);
        if (retVal > 0) {
            Long siteId = tabPbEduSite.getId();
            //站点活动
            retVal += modifyActivityList(tabPbEduSite);
            //站点设备
            retVal += modifyDeviceList(tabPbEduSite);
            //站点照片
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbEduSite.getAttachments(), siteId, AttachmentType.EDU_SITE);
        }
        return retVal;
    }

    @Transactional
    @Override
    public int updateWithAbout(TabPbEduSiteDto tabPbEduSite) {
        //修改站点信息
        int retVal = tabPbEduSiteMapper.updateByPrimaryKeySelective(tabPbEduSite);
        if (retVal > 0) {
            //站点活动
            retVal += modifyActivityList(tabPbEduSite);
            //站点设备
            retVal += modifyDeviceList(tabPbEduSite);
            //维护站点照片
            retVal += iTabPbAttachmentService.intelligentOperation(tabPbEduSite.getAttachments(),
                    tabPbEduSite.getId(), AttachmentType.EDU_SITE);
        }
        return retVal;
    }

    /**
     * 维护远教站点设备
     *
     * @param tabPbEduSite
     * @return
     */
    private int modifyDeviceList(TabPbEduSiteDto tabPbEduSite) {
        int retVal = 0;
        Long siteId = tabPbEduSite.getId();
        List<TabPbEduSiteDevice> eduDeviceList = tabPbEduSite.getDevices();
        List<TabPbEduSiteDevice> dbEduDeviceList = tabPbEduSiteDeviceMapper.selectAllBySiteId(siteId);

        if (CollectionUtil.isNotEmpty(eduDeviceList)) {
            List<Long> pendingIdList = eduDeviceList.stream().map(TabPbEduSiteDevice::getId).collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(dbEduDeviceList)) { //数据库有旧数据
                List<Long> pendingRemoveIdList = dbEduDeviceList.stream()
                        .filter(device -> !pendingIdList.contains(device.getId()))
                        .map(TabPbEduSiteDevice::getId)
                        .collect(Collectors.toList());

                if (CollectionUtil.isNotEmpty(pendingRemoveIdList)) {
                    retVal += tabPbEduSiteDeviceMapper.batchLogicDeleteById(pendingRemoveIdList);
                }
            }

            eduDeviceList.forEach(siteDevice -> {
                int callReturn;
                if (siteDevice.getId() != null && siteDevice.getId() > 0) { //修改
                    callReturn = tabPbEduSiteDeviceMapper.updateByPrimaryKeySelective(siteDevice);
                } else { //新增
                    siteDevice.setSiteId(siteId);
                    callReturn = tabPbEduSiteDeviceMapper.insertSelective(siteDevice);
                }
                if (callReturn > 0) {
                    iTabPbAttachmentService.intelligentOperation(siteDevice.getAttachments(),
                            siteDevice.getId(), AttachmentType.EDU_SITE_DEVICE);
                }
            });
        } else if (CollectionUtil.isNotEmpty(dbEduDeviceList)) {
            //逻辑删除
            retVal += tabPbEduSiteDeviceMapper.batchLogicDeleteBySiteId(siteId);
        }
        return retVal;
    }

    /**
     * 维护远教站点活动
     *
     * @param tabPbEduSite
     * @return
     */
    private int modifyActivityList(TabPbEduSiteDto tabPbEduSite) {
        int retVal = 0;
        Long siteId = tabPbEduSite.getId();
        List<TabPbEduSiteActivity> eduActivityList = tabPbEduSite.getActivitys();
        List<TabPbEduSiteActivity> dbEduActivityList = tabPbEduSiteActivityMapper.selectAllBySiteId(siteId);

        if (CollectionUtil.isNotEmpty(eduActivityList)) {
            List<Long> pendingIdList = eduActivityList.stream().map(TabPbEduSiteActivity::getId).collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(dbEduActivityList)) { //数据库有旧数据
                List<Long> pendingRemoveIdList = dbEduActivityList.stream()
                        .filter(activity -> !pendingIdList.contains(activity.getId()))
                        .map(TabPbEduSiteActivity::getId)
                        .collect(Collectors.toList());

                if (CollectionUtil.isNotEmpty(pendingRemoveIdList)) {
                    retVal += tabPbEduSiteActivityMapper.batchLogicDeleteById(pendingRemoveIdList);
                }
            }

            eduActivityList.forEach(siteActivity -> {
                int callReturn;
                if (siteActivity.getId() != null && siteActivity.getId() > 0) { //修改
                    callReturn = tabPbEduSiteActivityMapper.updateByPrimaryKeySelective(siteActivity);
                } else { //新增
                    siteActivity.setSiteId(siteId);
                    callReturn = tabPbEduSiteActivityMapper.insertSelective(siteActivity);
                }
                if (callReturn > 0) {
                    iTabPbAttachmentService.intelligentOperation(siteActivity.getAttachments(),
                            siteActivity.getId(), AttachmentType.EDU_SITE_ACTIVITY);
                }
            });
        } else if (CollectionUtil.isNotEmpty(dbEduActivityList)) {
            //逻辑删除
            retVal += tabPbEduSiteActivityMapper.batchLogicDeleteBySiteId(siteId);
        }
        return retVal;
    }
}
