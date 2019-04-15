package com.egovchina.partybuilding.partybuild.entity;

import java.util.Date;

public class TabPbSurvey {
    private Long surveyId;

    private Long surveyType;

    private Long auditStage;

    private Long meetingManNumbers;

    private Date meetingDate;

    private String talker;

    private Date talkDate;

    private String eblFlag;

    private String delFlag;

    private Long orderNum;

    private String description;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private Long version;

    private String discussContent;

    private String thoughtReport;

    private String talkContent;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(Long surveyType) {
        this.surveyType = surveyType;
    }

    public Long getAuditStage() {
        return auditStage;
    }

    public void setAuditStage(Long auditStage) {
        this.auditStage = auditStage;
    }

    public Long getMeetingManNumbers() {
        return meetingManNumbers;
    }

    public void setMeetingManNumbers(Long meetingManNumbers) {
        this.meetingManNumbers = meetingManNumbers;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getTalker() {
        return talker;
    }

    public void setTalker(String talker) {
        this.talker = talker == null ? null : talker.trim();
    }

    public Date getTalkDate() {
        return talkDate;
    }

    public void setTalkDate(Date talkDate) {
        this.talkDate = talkDate;
    }

    public String getEblFlag() {
        return eblFlag;
    }

    public void setEblFlag(String eblFlag) {
        this.eblFlag = eblFlag == null ? null : eblFlag.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername == null ? null : createUsername.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(Long updateUserid) {
        this.updateUserid = updateUserid;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername == null ? null : updateUsername.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDiscussContent() {
        return discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent == null ? null : discussContent.trim();
    }

    public String getThoughtReport() {
        return thoughtReport;
    }

    public void setThoughtReport(String thoughtReport) {
        this.thoughtReport = thoughtReport == null ? null : thoughtReport.trim();
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent == null ? null : talkContent.trim();
    }
}