package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyOrganizationActivitiesDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbActivitiesDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbParticipant;
import com.egovchina.partybuilding.partybuild.repository.TabPbActivitiesMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbParticipantMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.ITabSysDictService;
import com.egovchina.partybuilding.partybuild.service.TabPbActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YangYingXiang on 2018/12/03
 */
@Service
@Transactional
public class TabPbActivitiesServiceImpl implements TabPbActivitiesService {
    @Autowired
    private TabPbActivitiesMapper activitiesMapper;

    @Autowired
    private TabPbParticipantMapper participantMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    private ITabSysDictService sysDictService;

//    @Override
//    public List<TabPbActivitiesDto> ActivitiesDtoList(TabPbActivitiesDto activitiesDto, Page page) {
//        PageHelper.startPage(page);
//        var list = activitiesMapper.ActivitiesDtoList(activitiesDto);
//        return list;
//    }

    @Transactional
    @Override
    public int updateWithDetailTable(TabPbActivitiesDto tabPbActivitiesDto) {

        //先修改活动表
        int retVal = activitiesMapper.updateByPrimaryKeySelective(tabPbActivitiesDto);

        List<TabPbParticipant> pendingParticipantList = tabPbActivitiesDto.getParticipantList();
        //查出数据库已有的参会人员
        List<TabPbParticipant> dbParticipantList = participantMapper.selectDetailTableByActivitiesId(tabPbActivitiesDto.getActivitiesId());

        retVal += modifyParticipant(pendingParticipantList, dbParticipantList);

        return retVal;
    }

    @Transactional
    @Override
    public int updatePartyTable(PartyOrganizationActivitiesDto activitiesDto) {
        int retVal = 0;

        List<TabPbParticipant> pendingParticipantList = activitiesDto.getTabPbParticipant();
        //数据库中查询出的人员
        List<TabPbParticipant> dbParticipantList = participantMapper.selectDetailTableByActivitiesId(activitiesDto.getActivitiesId());

        //活动id处理
        for (TabPbParticipant participant : pendingParticipantList) {
            participant.setActivitiesId(activitiesDto.getActivitiesId());
        }
        //维护人员信息
        retVal += modifyParticipant(pendingParticipantList, dbParticipantList);
        return retVal;
    }



    /**
     * 维护人员表数据
     *
     * @param pendingParticipantList 待操作人员集合
     * @param dbParticipantList      数据库人员集合
     * @return
     */
    private int modifyParticipant(List<TabPbParticipant> pendingParticipantList, List<TabPbParticipant> dbParticipantList) {
        int retVal = 0;
        if (CollectionUtil.isEmpty(pendingParticipantList)) {
            if (CollectionUtil.isNotEmpty(dbParticipantList)) {
                List<Long> participantIds = dbParticipantList.stream().map(TabPbParticipant::getParticipantId).collect(Collectors.toList());
                retVal += participantMapper.batchLogicDelete(participantIds);
                return retVal;
            }
        }

        if (CollectionUtil.isNotEmpty(dbParticipantList)) {
            List<Long> pendingParticipantIdList = pendingParticipantList.stream().map(TabPbParticipant::getParticipantId).collect(Collectors.toList());
            List<Long> dbParticipantIdList = dbParticipantList.stream().map(TabPbParticipant::getParticipantId).collect(Collectors.toList());

            //前端有的数据库没有，新增
            List<TabPbParticipant> pendingAddList = pendingParticipantList.stream().filter(tabPbParticipant -> !dbParticipantIdList.contains(tabPbParticipant.getParticipantId()))
                    .collect(Collectors.toList());

            //前端没有的，数据库有的，删除(只需要id即可)
            List<Long> pendingRemoveIdList = dbParticipantList.stream().filter(tabPbParticipant -> !pendingParticipantIdList.contains(tabPbParticipant.getParticipantId()))
                    .map(TabPbParticipant::getParticipantId).collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(pendingAddList)) {
                pendingAddList.forEach(tabPbParticipant -> participantMapper.insertSelective(tabPbParticipant));
            }
            if (CollectionUtil.isNotEmpty(pendingRemoveIdList)) {
                retVal += participantMapper.batchLogicDelete(pendingRemoveIdList);
            }

            //前端有数据库也有的，循环修改
            pendingParticipantList.stream().filter(tabPbParticipant -> dbParticipantIdList.contains(tabPbParticipant.getParticipantId()))
                    .forEach(tabPbParticipant -> participantMapper.updateByPrimaryKeySelective(tabPbParticipant));

        }
        return retVal;
    }

}
