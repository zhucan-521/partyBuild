package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class TabPbMsgNotice implements Serializable{

    private Long id;

    private Long deptId;

    private String deptName;

    private Long noticeType;

    private String noticeTitle;

    private String noticeContent;

    private String state;

    private Date publishTime;

    private String delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private List<TabPbAttachment> attachments;

    private List<TabPbMsgNoticeDept> noticeDeptList;

    private String signingSituation;

    private Long photo;

    private Long doc;

    private Long orgRange;

    private Date stateTime;

    private Date endTime;

}