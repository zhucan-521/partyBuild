package com.egovchina.partybuilding.partybuild.service.impl;

import com.alibaba.fastjson.JSON;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.PersonnelCategory;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageReceive;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageSend;
import com.egovchina.partybuilding.partybuild.entity.TabPbParticipant;
import com.egovchina.partybuilding.partybuild.repository.TabPbMessageMapper;
import com.egovchina.partybuilding.partybuild.service.MessageContentService;
import com.egovchina.partybuilding.partybuild.service.TimedTransmissionService;
import com.egovchina.partybuilding.partybuild.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * @author create by GuanYingxin on 2019/5/24 11:08
 * @description
 */
@Slf4j
@Service("timeTransmissionService")
public class TimedTransmissionServiceImpl implements TimedTransmissionService {

    @Autowired
    private TabPbMessageMapper tabPbMessageMapper;

    @Autowired
    private MessageContentService messageContentService;

    /**
     * 定时提醒领导班子
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void remindTheLeadershipTeam() {
        log.info("Scheduled occur , Event at 定时提醒领导班子换届");
        //获取要到届的领导班子成员id以及名字集合
        List<LeadTeamExpireVO> leadTeamExpireVOS = tabPbMessageMapper.queryTheLeadershipTeamThatIsDueToExpire();
        if (CollectionUtil.isNotEmpty(leadTeamExpireVOS)) {
            //往信息发送表里面插入系统信息
            TabPbMessageSend tabPbMessageSend = new TabPbMessageSend();
            tabPbMessageSend.setSenderId(1L)
                    .setSenderName("admin")
                    .setType(59680L)
                    .setTitle("系统提示")
                    .setContent(messageContentService.selectMessageContent(59686L))
                    .setSendTime(new Date());
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbMessageSend);
            tabPbMessageMapper.insertTabPbMessageSend(tabPbMessageSend);

            //批量插入接收者为将要到届的领导班子成员信息到接收表
            List<TabPbMessageReceive> tabPbMessageReceiveList = new ArrayList<>();
            leadTeamExpireVOS.forEach(r -> {
                TabPbMessageReceive tabPbMessageReceive = new TabPbMessageReceive();
                tabPbMessageReceive.setSendId(tabPbMessageSend.getSendId())
                        .setReceiverId(r.getOrgId())
                        .setReceiverName(r.getOrgName())
                        .setReceiverType(PersonnelCategory.TO_USER);
                tabPbMessageReceiveList.add(tabPbMessageReceive);
            });
            tabPbMessageMapper.batchInsertTabPbMessageReceive(tabPbMessageReceiveList);
        }
    }

    /**
     * 党员生日当天提醒
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void partyMemberBirthDayRemind() {
        log.info("Scheduled occur , Event at 定时提醒党员生日");
        //获取党员生日列表
        List<PartyMemberBirthDayVO> birthDayVOS = tabPbMessageMapper.selectPartyMemberBirthDay();
        if (CollectionUtil.isNotEmpty(birthDayVOS)) {
            //获取党员政治生日提醒模板
            final String messageContent = tabPbMessageMapper.selectMessageContent(59685L);

            List<TabPbMessageReceive> tabPbMessageReceiveList = new ArrayList<>();
            birthDayVOS.forEach(r -> {
                //往消息发送表里面插入系统消息
                TabPbMessageSend tabPbMessageSend = new TabPbMessageSend();
                String tempContent = messageContent;
                tempContent.replace("{{orgName}}", r.getOrgName());
                tabPbMessageSend.setSenderId(1L)
                        .setSenderName("admin")
                        .setType(59680L)
                        .setTitle("系统提示")
                        .setContent(tempContent)
                        .setSendTime(new Date());
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbMessageSend);
                tabPbMessageMapper.insertTabPbMessageSend(tabPbMessageSend);

                //接收表插入相应数据
                TabPbMessageReceive tabPbMessageReceive = new TabPbMessageReceive();
                tabPbMessageReceive.setSendId(tabPbMessageSend.getSendId())
                        .setReceiverId(r.getUserId())
                        .setReceiverName(r.getRealname())
                        .setReceiverType(PersonnelCategory.TO_PARTY_MEMBER);
                tabPbMessageReceiveList.add(tabPbMessageReceive);
            });
            tabPbMessageMapper.batchInsertTabPbMessageReceive(tabPbMessageReceiveList);
        }
    }


    @Transactional
    @Scheduled(cron = "0 0 0 * * * ")
    @Override
    public void addPeople() {
        log.info("Scheduled occur , Event at 定时添加前一天的活动未参加的人");

        //查询前一天的活动id跟组织id
        List<ActivityVO> activityVO = tabPbMessageMapper.selectByActivityVO();
        if (CollectionUtil.isNotEmpty(activityVO)) {

            for (ActivityVO vo : activityVO) {
                //获取活动的快照
                PartyOrganizeActivitiesVO activitiesVO = tabPbMessageMapper.selectOrganizationActivityVOById(vo.getActivitiesId());
                ActivityCandidateMemberVO candidateMemberVO = JSON.parseObject(activitiesVO.getCandidateMemberSnapshot(), ActivityCandidateMemberVO.class);
                if (vo.getUserId() != null) {
                    //获取活动已经存在的人
                    List<PersonnelEntityVO> list = tabPbMessageMapper.selectParticipantList(vo.getActivitiesId());
                    if (CollectionUtil.isNotEmpty(list)) {
                        List<Long> existsUserIds = list.stream().map(PersonnelEntityVO::getUserId).collect(Collectors.toList());
                        //处理，填充是否参加标识
                        Set<PersonnelEntityVO> groupMembers = new HashSet<>();
                        candidateMemberVO.getGroups().forEach(partyGroupVO -> groupMembers.addAll(partyGroupVO.getMembers()));
                        if (CollectionUtil.isNotEmpty(candidateMemberVO.getOnFlows())) {
                            groupMembers.addAll(candidateMemberVO.getOnFlows());
                        }
                        if (CollectionUtil.isNotEmpty(candidateMemberVO.getModerators())) {
                            groupMembers.addAll(candidateMemberVO.getModerators());
                        }
                        for (PersonnelEntityVO personnelVO : groupMembers) {

                            if (!existsUserIds.contains(personnelVO.getUserId())) {
                                PersonnelEntityVO personnelEntityVO = new PersonnelEntityVO();
                                personnelEntityVO.setUserId(personnelVO.getUserId());
                                personnelEntityVO.setActivitiesId(activitiesVO.getActivitiesId());
                                personnelEntityVO.setAbsentReason((byte) 1);
                                personnelEntityVO.setRealName(personnelVO.getRealName());
                                personnelEntityVO.setRealType(personnelVO.getRealType());
                                tabPbMessageMapper.addPersonnel(generateTargetCopyPropertiesAndPaddingBaseField(personnelEntityVO, TabPbParticipant.class, false));
                            }
                        }
                    }
                }
                if (vo.getUserId() ==null){
                    //处理，填充是否参加标识
                    Set<PersonnelEntityVO> groupMembers = new HashSet<>();
                    candidateMemberVO.getGroups().forEach(partyGroupVO -> groupMembers.addAll(partyGroupVO.getMembers()));
                    if (CollectionUtil.isNotEmpty(candidateMemberVO.getOnFlows())) {
                        groupMembers.addAll(candidateMemberVO.getOnFlows());
                    }
                    if (CollectionUtil.isNotEmpty(candidateMemberVO.getModerators())) {
                        groupMembers.addAll(candidateMemberVO.getModerators());
                    }
                    for (PersonnelEntityVO personnelVO : groupMembers) {
                        PersonnelEntityVO personnelEntityVO = new PersonnelEntityVO();
                        personnelEntityVO.setUserId(personnelVO.getUserId());
                        personnelEntityVO.setActivitiesId(activitiesVO.getActivitiesId());
                        personnelEntityVO.setAbsentReason((byte) 1);
                        personnelEntityVO.setRealName(personnelVO.getRealName());
                        personnelEntityVO.setRealType(personnelVO.getRealType());
                        tabPbMessageMapper.addPersonnel(generateTargetCopyPropertiesAndPaddingBaseField(personnelEntityVO, TabPbParticipant.class, false));
                    }
                }

            }

        }
    }
}
