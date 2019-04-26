package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * desc: 党员发展步骤时间-视图对象
 * Created by FanYanGen on 2019/4/23 11:23
 */
@Data
@ApiModel("党员发展步骤时间-视图对象")
public class DevPartyMemberDateVO {

    @ApiModelProperty("时间ID")
    private Long timeId;

    @ApiModelProperty(value = "党员发展步骤id")
    private Long hostId;

    @ApiModelProperty(value = "发展时间")
    @JsonFormat(timezone = "GMT+8")
    private Date devDate;

}
