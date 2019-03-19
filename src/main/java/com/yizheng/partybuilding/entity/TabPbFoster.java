package com.yizheng.partybuilding.entity;

import java.util.Date;

public class TabPbFoster {
    private Long fosterId;

    private Long fosterorId;

    private String fosteror;

    private Long auditStage;

    private Date fosterDate;

    private Date reportDate;

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

    private String fosterorReport;

    private String report;

    public Long getFosterId() {
        return fosterId;
    }

    public void setFosterId(Long fosterId) {
        this.fosterId = fosterId;
    }

    public Long getFosterorId() {
        return fosterorId;
    }

    public void setFosterorId(Long fosterorId) {
        this.fosterorId = fosterorId;
    }

    public String getFosteror() {
        return fosteror;
    }

    public void setFosteror(String fosteror) {
        this.fosteror = fosteror == null ? null : fosteror.trim();
    }

    public Long getAuditStage() {
        return auditStage;
    }

    public void setAuditStage(Long auditStage) {
        this.auditStage = auditStage;
    }

    public Date getFosterDate() {
        return fosterDate;
    }

    public void setFosterDate(Date fosterDate) {
        this.fosterDate = fosterDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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

    public String getFosterorReport() {
        return fosterorReport;
    }

    public void setFosterorReport(String fosterorReport) {
        this.fosterorReport = fosterorReport == null ? null : fosterorReport.trim();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }
}