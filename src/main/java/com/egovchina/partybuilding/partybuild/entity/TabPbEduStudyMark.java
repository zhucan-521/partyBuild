package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbEduStudyMark {

    /**
     * 痕迹ID
     */
    private Long markId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 学习资源ID
     */
    private Long studyId;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

}