package com.yizheng.partybuilding.entity;

import java.util.Date;

public class TabPbDevStageAudit {
    private Long auditId;

    private String cultivator;

    private Long auditStage;

    private Byte passed;

    private Date cultivateDate;

    private Date reportDate;

    private Long branchMeetingCount;

    private Date branchMeetingDate;

    private String talker;

    private Date predictionDate;

    private Date devDate;

    private String introducer;

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

    private String cultivatorOpinion;

    private String thoughtReport;

    private String talkContent;

    private String branchOpinion;

    private String branchDicuss;

    private String higherleveAudit;

    private String rwardPunish;

    private String groupOpinion;

    private String massesOpinion;

    private String introduce;

    private String declaring;

    private String committeerOpinion;

    private String committeeAudit;

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getCultivator() {
        return cultivator;
    }

    public void setCultivator(String cultivator) {
        this.cultivator = cultivator == null ? null : cultivator.trim();
    }

    public Long getAuditStage() {
        return auditStage;
    }

    public void setAuditStage(Long auditStage) {
        this.auditStage = auditStage;
    }

    public Byte getPassed() {
        return passed;
    }

    public void setPassed(Byte passed) {
        this.passed = passed;
    }

    public Date getCultivateDate() {
        return cultivateDate;
    }

    public void setCultivateDate(Date cultivateDate) {
        this.cultivateDate = cultivateDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Long getBranchMeetingCount() {
        return branchMeetingCount;
    }

    public void setBranchMeetingCount(Long branchMeetingCount) {
        this.branchMeetingCount = branchMeetingCount;
    }

    public Date getBranchMeetingDate() {
        return branchMeetingDate;
    }

    public void setBranchMeetingDate(Date branchMeetingDate) {
        this.branchMeetingDate = branchMeetingDate;
    }

    public String getTalker() {
        return talker;
    }

    public void setTalker(String talker) {
        this.talker = talker == null ? null : talker.trim();
    }

    public Date getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(Date predictionDate) {
        this.predictionDate = predictionDate;
    }

    public Date getDevDate() {
        return devDate;
    }

    public void setDevDate(Date devDate) {
        this.devDate = devDate;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer == null ? null : introducer.trim();
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

    public String getCultivatorOpinion() {
        return cultivatorOpinion;
    }

    public void setCultivatorOpinion(String cultivatorOpinion) {
        this.cultivatorOpinion = cultivatorOpinion == null ? null : cultivatorOpinion.trim();
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

    public String getBranchOpinion() {
        return branchOpinion;
    }

    public void setBranchOpinion(String branchOpinion) {
        this.branchOpinion = branchOpinion == null ? null : branchOpinion.trim();
    }

    public String getBranchDicuss() {
        return branchDicuss;
    }

    public void setBranchDicuss(String branchDicuss) {
        this.branchDicuss = branchDicuss == null ? null : branchDicuss.trim();
    }

    public String getHigherleveAudit() {
        return higherleveAudit;
    }

    public void setHigherleveAudit(String higherleveAudit) {
        this.higherleveAudit = higherleveAudit == null ? null : higherleveAudit.trim();
    }

    public String getRwardPunish() {
        return rwardPunish;
    }

    public void setRwardPunish(String rwardPunish) {
        this.rwardPunish = rwardPunish == null ? null : rwardPunish.trim();
    }

    public String getGroupOpinion() {
        return groupOpinion;
    }

    public void setGroupOpinion(String groupOpinion) {
        this.groupOpinion = groupOpinion == null ? null : groupOpinion.trim();
    }

    public String getMassesOpinion() {
        return massesOpinion;
    }

    public void setMassesOpinion(String massesOpinion) {
        this.massesOpinion = massesOpinion == null ? null : massesOpinion.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getDeclaring() {
        return declaring;
    }

    public void setDeclaring(String declaring) {
        this.declaring = declaring == null ? null : declaring.trim();
    }

    public String getCommitteerOpinion() {
        return committeerOpinion;
    }

    public void setCommitteerOpinion(String committeerOpinion) {
        this.committeerOpinion = committeerOpinion == null ? null : committeerOpinion.trim();
    }

    public String getCommitteeAudit() {
        return committeeAudit;
    }

    public void setCommitteeAudit(String committeeAudit) {
        this.committeeAudit = committeeAudit == null ? null : committeeAudit.trim();
    }
}