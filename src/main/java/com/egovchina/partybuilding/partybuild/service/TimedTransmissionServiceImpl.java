package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageReceive;
import com.egovchina.partybuilding.partybuild.entity.TabPbMessageSend;
import com.egovchina.partybuilding.partybuild.repository.TabPbMessageMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.vo.LeadTeamExpireVO;
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
@Service("timeTransmissionService")
public class TimedTransmissionServiceImpl implements TimedTransmissionService {

    @Autowired
    private TabPbMessageMapper tabPbMessageMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    /**
     * 定时提醒领导班子
     */
    @Transactional
    @Scheduled(cron = "0 0/1 * * * *")
    @Override
    public int remindTheLeadershipTeam() {
        TabPbMessageSend tabPbMessageSend = new TabPbMessageSend();
        tabPbMessageSend.setSenderId(UserContextHolder.getUserId())
                .setSenderName(UserContextHolder.getUserName())
                .setType(3L).setTitle("系统提示")
                .setContent("临近换届日期")
                .setSendTime(new Date());
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbMessageSend);
        int result = 0;
        result += tabPbMessageMapper.insertTabPbMessageSend(tabPbMessageSend);

        //获取要到届的领导班子id以及名字集合
        List<LeadTeamExpireVO> leadTeamExpireVOS = tabPbMessageMapper.queryTheLeadershipTeamThatIsDueToExpire();
        if (CollectionUtil.isNotEmpty(leadTeamExpireVOS)) {
            //批量插入接收者为将要到届的领导班子的接收表
            List<TabPbMessageReceive> tabPbMessageReceiveList = new ArrayList<>();

            leadTeamExpireVOS.forEach(r -> {
                TabPbMessageReceive tabPbMessageReceive = new TabPbMessageReceive();
                tabPbMessageReceive.setReceiverId(r.getLeadTeamId())
                        .setReceiverName(r.getLeadTeamName())
                        .setReceiverType(1L);
                tabPbMessageReceiveList.add(tabPbMessageReceive);
            });
            result += tabPbMessageMapper.batchInsertTabPbMessageReceive(tabPbMessageReceiveList);
        }
        return result;
    }

}
