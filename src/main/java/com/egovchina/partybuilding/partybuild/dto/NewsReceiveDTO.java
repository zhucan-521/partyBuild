package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 党务公开收组织-数据传输对象
 * Created by FanYanGen on 2019-05-24 11:06
 */
@Data
@ApiModel("党务公开接收组织-数据传输对象")
public class NewsReceiveDTO {

    @ApiModelProperty("接收方组织ID")
    private Long receiveOrgId;

}
