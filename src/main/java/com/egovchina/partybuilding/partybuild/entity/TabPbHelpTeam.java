package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain = true)
@Data
public class TabPbHelpTeam {

    /**
     *主键
     */
    private Long teamId;

    /**
     *队伍名称
     */
    private String teamName;

    /**
     *党组织id
     */
    private Long orgId;

    /**
     *党组织名称
     */
    private String orgName;

    /**
     *成立日期
     */
    private Date setDate;

    /**
     *人数
     */
    private Long peopleNumble;

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
     *修改人姓名
     */
    private String updateUsername;

    /**
     *删除标识
     */
    private String delFlag;

}