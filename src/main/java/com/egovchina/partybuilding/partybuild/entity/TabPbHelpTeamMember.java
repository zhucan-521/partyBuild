package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain = true)
@Data
public class TabPbHelpTeamMember {

    /**
     *成员Id
     */
    private Long memberId;

    /**
     *队伍主键
     */
    private Long teamId;

    /**
     *用户Id
     */
    private Long userId;

    /**
     *姓名
     */
    private String username;

    /**
     *创建时间
     */
    private Date createTime;

    /**
     *创建人id
     */
    private Long createUserid;

    /**
     *创建人姓名
     */
    private String createUsername;

    /**
     *修改时间
     */
    private Date updateTime;

    /**
     *修改人id
     */
    private Long updateUserid;

    /**
     *修改姓名
     */
    private String updateUsername;

    /**
     *删除标识
     */
    private String delFlag;

}