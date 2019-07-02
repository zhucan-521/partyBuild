package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/5/23 13:49
 * @description 消息发送VO
 */
@ApiModel("消息发送VO")
@Data
@Accessors(chain = true)
public class MessageSendVO {

    @ApiModelProperty("消息发送id")
    private Long sendId;

    @ApiModelProperty("发送者id")
    private Long senderId;

    @ApiModelProperty("发送者名称")
    private String senderName;

    @ApiModelProperty("消息接收者id")
    private Long receiverId;

    @ApiModelProperty("消息类别")
    @JsonSerialize(using = DictSerializer.class)
    private Long type;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sendTime;

    @ApiModelProperty("消息接受状态，是否已读(null代表没提醒 0 未读  1 已读)")
    private Byte receiveStatus;

    @ApiModelProperty("接受类型者 0 个人 1 组织")
    private Byte receiverType;

}
