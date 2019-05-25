package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 党务公开接收方VO
 * Created by FanYanGen on 2019-05-24 14:23
 */
@Data
@ApiModel("党务公开接收方VO")
public class NewsReceiveVO {

    @ApiModelProperty("接收组织ID")
    private Long orgId;

    @ApiModelProperty("接收组织ID")
    private Long receiveOrgId;

    @ApiModelProperty("接收组织名称")
    private String orgName;

    @ApiModelProperty("接收组织简称")
    private String orgShortName;

}
