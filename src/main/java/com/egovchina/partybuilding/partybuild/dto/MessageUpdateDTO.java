package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author create by GuanYingxin on 2019/5/23 10:31
 * @description 消息更新DTO
 */
@ApiModel("消息更新DTO")
@Data
@Accessors(chain = true)
public class MessageUpdateDTO {

    @ApiModelProperty(value = "发送id", required = true)
    @NotNull(message = "发送id不能为空")
    private Long sendId;

    @ApiModelProperty(value = "发送者id")
    private Long senderId;

    @ApiModelProperty("发送者名字")
    private String senderName;

    @ApiModelProperty("消息类别")
    private Long type;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty(value = "发送时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendTime;

    @ApiModelProperty(value = "接收人id", required = true)
    @NotNull(message = "接收人id不能为空")
    private List<Long> receiverIds;

    @ApiModelProperty("消息接收人姓名")
    private String receiverName;

    @ApiModelProperty("消息接收人类型 0 个人 1 组织")
    private Long receiverType;

}
