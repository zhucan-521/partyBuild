package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author create by GuanYingxin on 2019/5/23 21:00
 * @description
 */
@ApiModel("消息添加DTO")
@Data
@Accessors(chain = true)
public class MessageAddDTO {

    @ApiModelProperty(value = "发送id")
    private Long sendId;

    @ApiModelProperty(value = "消息类别", required = true)
    @NotNull(message = "消息类别不能为空")
    private Long type;

    @ApiModelProperty(value = "消息标题", required = true)
    @NotNull(message = "消息标题不能为空")
    private String title;

    @ApiModelProperty(value = "消息内容", required = true)
    @NotNull(message = "消息内容不能为空")
    private String content;

    @ApiModelProperty(value = "接收者信息", required = true)
    @NotNull(message = "接收者信息不能为空")
    private List<MessageReceiveDTO> receivers;

    @ApiModelProperty(value = "接收者类型", required = true)
    @NotNull(message = "接收者id不能为空")
    private Long receiverType;

}
