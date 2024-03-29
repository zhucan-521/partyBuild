package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 党员发展步骤-数据传输对象
 * Created by FanYanGen on 2019/4/23 09:57
 */
@Data
@ApiModel("党员发展步骤-数据传输对象")
public class DevPartyMemberDTO {

    @ApiModelProperty(value = "主键id")
    private Long dpId;

    @ApiModelProperty(value = "环节状态,即hostId后的字段", required = true)
    private Long status;

    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    @ApiModelProperty(value = "附件类型")
    private Long attachmentType;

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

    public DevPartyMemberDTO(Long dpId, Long status) {
        this.dpId = dpId;
        this.status = status;
    }

    public DevPartyMemberDTO(Long dpId, Long status, Long userId) {
        this.dpId = dpId;
        this.status = status;
        this.userId = userId;
    }

}
