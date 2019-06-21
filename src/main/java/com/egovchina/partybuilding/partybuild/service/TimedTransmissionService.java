package com.egovchina.partybuilding.partybuild.service;

/**
 * @author create by GuanYingxin on 2019/5/24 11:07
 * @description 定时发送service
 */
public interface TimedTransmissionService {

    /**
     * 领导班子换届提醒
     */
    void remindTheLeadershipTeam();

    /**
     * 党员生日当天提醒
     */
    void partyMemberBirthDayRemind();

    /**
     * 提醒党员参加月份活动
     */
    void remindToParticipateInTheActivityByMonth();

    /**
     * 提醒党员参加季度活动
     */
    void remindToParticipateInTheActivityByQuarter();

    /**
     * 提醒党员参加年度活动
     */
    void remindToParticipateInTheActivityByYear();

    /**
     * 定时添加人
     */
    void  addPeople();
}
