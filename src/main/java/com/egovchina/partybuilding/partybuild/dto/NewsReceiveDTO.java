package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 党务公开收组织DTO
 * Created by FanYanGen on 2019-05-24 11:06
 */
@Data
@ApiModel("党务公开接收组织-数据传输对象")
class NewsReceiveDTO {

    @ApiModelProperty(value = "接收方组织ID", required = true)
    @NotNull(message = "接收方组织ID不能为空")
    private Long receiveOrgId;

}
