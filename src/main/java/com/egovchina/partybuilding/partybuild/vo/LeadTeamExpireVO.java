package com.egovchina.partybuilding.partybuild.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author create by GuanYingxin on 2019/5/25 14:04
 * @description 将要到届的领导班子VO
 */
@Data
@Accessors(chain = true)
public class LeadTeamExpireVO {

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 人员id
     */
    private Long userId;

    /**
     * 人员名称
     */
    private String realname;

}
