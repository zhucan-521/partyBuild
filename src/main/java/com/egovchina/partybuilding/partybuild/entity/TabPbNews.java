package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TabPbNews {

    private Long newsId;

    private String title;

    private Long type;

    private Long orgId;

    private String content;

    private Date publishTime;

    private Integer publishStatus;

    private Long watchNum;

    private Integer topStatus;

    private String cover;

    private Integer delFlag;

    private Integer orderNum;

    //base field

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

}