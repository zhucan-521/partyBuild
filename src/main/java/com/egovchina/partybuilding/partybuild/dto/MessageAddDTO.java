package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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

    @ApiModelProperty("消息类别")
    private Long type;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty(value = "发送时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendTime;

    @ApiModelProperty(value = "接收者信息")
    private List<MessageReceiveDTO> receivers;

}
