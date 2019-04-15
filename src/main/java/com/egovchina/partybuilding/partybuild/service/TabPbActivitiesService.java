package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.PartyOrganizationActivitiesDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbActivitiesDto;

/**
 * @author YangYingXiang on 2018/12/03
 */
public interface TabPbActivitiesService {

    /**
     * 修改活动包括详情表数据
     * @param tabPbActivitiesDto
     * @return
     */
    int updateWithDetailTable(TabPbActivitiesDto tabPbActivitiesDto);

    //党组织生活修改
   int updatePartyTable(PartyOrganizationActivitiesDto activitiesDto);

//    /**
//     * 在职党员活动list数据
//     * @param activitiesDto
//     * @return
//     */
//    List<TabPbActivitiesDto> ActivitiesDtoList(TabPbActivitiesDto activitiesDto, Page page);

}
