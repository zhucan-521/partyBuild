package com.egovchina.partybuilding.partybuild.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author create by GuanYingxin on 2019/6/14 14:14
 * @description 党员生日VO
 */
@Accessors(chain = true)
@Data
public class PartyMemberBirthDayVO {

    /**
     * 党员id
     */
    private Long userId;

    /**
     * 党员姓名
     */
    private String realname;

}
