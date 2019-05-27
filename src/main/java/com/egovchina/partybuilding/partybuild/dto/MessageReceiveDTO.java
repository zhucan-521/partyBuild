package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author create by GuanYingxin on 2019/5/25 16:31
 * @description 消息接收者id、姓名、接收类型vo
 */
@ApiModel("消息接收者id与姓名vo")
@Data
@Accessors(chain = true)
public class MessageReceiveDTO {

    @ApiModelProperty(value = "接收者id", required = true)
    @NotNull(message = "接收者id不能为空")
    private Long receiverId;

    @ApiModelProperty(value = "接收者姓名", required = true)
    @NotBlank(message = "接收者姓名不能为空")
    private String receiverName;

}
