package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author create by GuanYingxin on 2019/5/25 16:31
 * @description 消息接收者id、姓名、接收类型vo
 */
@ApiModel("消息接收者id与姓名vo")
@Data
@Accessors(chain = true)
public class MessageReceiveDTO {

    @ApiModelProperty("接收者id")
    private Long receiverId;

    @ApiModelProperty("接收者姓名")
    private String receiverName;

    @ApiModelProperty("接收者类型")
    private Long receiverType;

}
