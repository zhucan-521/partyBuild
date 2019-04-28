package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("党员发展信息")
public class TabPbDevPartyMember {

    private Long dpId;

    private Long status;

    private Long userId;

    private Long hostId11;

    private Long hostId12;

    private Long hostId21;

    private Long hostId22;

    private Long hostId23;

    private Long hostId24;

    private Long hostId31;

    private Long hostId32;

    private Long hostId33;

    private Long hostId34;

    private Long hostId35;

    private Long hostId41;

    private Long hostId42;

    private Long hostId43;

    private Long hostId44;

    private Long hostId45;

    private Long hostId46;

    private Long hostId47;

    private Long hostId51;

    private Long hostId52;

    private Long hostId53;

    private Long hostId54;

    private Long hostId55;

    private Long hostId56;

    private Long hostId57;

    // ===============================

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private String delFlag;

    private Long deptId;

}