package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class TabPbNotice implements Serializable{

    private Long noticeId;

    private Integer noticeType;

    private String noticeTitle;

    private String noticeSubTitle;

    private Boolean noticeState;

    private String coverId;

    private String noticeSource;

    private Date publishTime;

    private Long browseCount;

    private Long orderNum;

    private String delFlag;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private String noticeContent;

    private String videoId;

    private List<TabPbNoticeReceive> tabPbNoticeReceives;
}