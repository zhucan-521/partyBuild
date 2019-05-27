package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageReceive;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageSend;
import com.egovchina.partybuilding.partybuild.repository.TabPbMessageMapper;
import com.egovchina.partybuilding.partybuild.service.TimedTransmissionService;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamExpireVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author create by GuanYingxin on 2019/5/24 11:08
 * @description
 */
@Slf4j
@Service("timeTransmissionService")
public class TimedTransmissionServiceImpl implements TimedTransmissionService {

    @Autowired
    private TabPbMessageMapper tabPbMessageMapper;

    /**
     * 定时提醒领导班子
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void remindTheLeadershipTeam() {
        log.info("Scheduled occur , Event at 定时提醒领导班子换届");
        //获取要到届的领导班子id以及名字集合
        List<LeadTeamExpireVO> leadTeamExpireVOS = tabPbMessageMapper.queryTheLeadershipTeamThatIsDueToExpire();
        if (CollectionUtil.isNotEmpty(leadTeamExpireVOS)) {
            //往信息发送表里面插入系统信息
            TabPbMessageSend tabPbMessageSend = new TabPbMessageSend();
            tabPbMessageSend.setSenderId(1L)
                    .setSenderName("admin")
                    .setType(3L).setTitle("系统提示")
                    .setContent("临近换届日期")
                    .setSendTime(new Date());
            PaddingBaseFieldUtil.paddingBaseFiled(tabPbMessageSend);
            tabPbMessageMapper.insertTabPbMessageSend(tabPbMessageSend);

            //批量插入接收者为将要到届的领导班子的接收表
            List<TabPbMessageReceive> tabPbMessageReceiveList = new ArrayList<>();
            leadTeamExpireVOS.forEach(r -> {
                TabPbMessageReceive tabPbMessageReceive = new TabPbMessageReceive();
                tabPbMessageReceive.setSendId(tabPbMessageSend.getSendId()).setReceiverId(r.getOrgId())
                        .setReceiverName(r.getOrgName())
                        .setReceiverType(1L);
                tabPbMessageReceiveList.add(tabPbMessageReceive);
            });
            tabPbMessageMapper.batchInsertTabPbMessageReceive(tabPbMessageReceiveList);
        }
    }

}
