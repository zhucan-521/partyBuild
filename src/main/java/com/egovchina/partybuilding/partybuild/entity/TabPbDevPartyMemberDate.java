package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TabPbDevPartyMemberDate {

    private Long timeId;

    private Long hostId;

    private Date devDate;

    // ===============================

    private String delFlag;

    private Date createTime;

    private Long createUserid;

}

