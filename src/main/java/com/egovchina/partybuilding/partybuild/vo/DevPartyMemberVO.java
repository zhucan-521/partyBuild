package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 党员发展步骤-视图对象
 * Created by FanYanGen on 2019/4/23 11:23
 */
@Data
@ApiModel("党员发展步骤-视图对象")
public class DevPartyMemberVO {

    @ApiModelProperty(value = "发展主键")
    private Long dpId;

    @ApiModelProperty(value = "发展ID")
    private Long deptId;

    @ApiModelProperty(value = "状态")
    private Long status;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户ID")
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

}
