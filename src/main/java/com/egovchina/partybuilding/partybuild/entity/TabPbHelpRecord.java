package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class TabPbHelpRecord {

    /**
     * 主键
     */
    private Long recordId;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 记录日趋
     */
    private Date recordDate;

    /**
     * 记录标题
     */
    private String recordTitle;

    /**
     * 快照
     */
    private String teamSnapshot;

    /**
     * 创建时间 yyyy-MM-dd HH:mm:ss
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private Long createUserid;

    /**
     * 创建人姓名
     */
    private String createUsername;

    /**
     * 修改时间 yyyy-MM-dd HH:mm:ss
     */
    private Date updateTime;

    /**
     * 修改人id
     */
    private Long updateUserid;

    /**
     * 修改人姓名
     */
    private String updateUsername;

    /**
     * 删除标识 1已删除；0未删除；默认0
     */
    private String delFlag;

    /**
     * 记录内容
     */
    private String content;
}