package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@ApiModel(value = "消息接收")
@Data
@Accessors(chain = true)
public class TabPbNoticeReceive implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "消息主体表id")
    private Long noticeId;

    @ApiModelProperty(value = "接收人id，党员id")
    private Long userId;

    @ApiModelProperty(value = "是否阅读，默认 false，阅读后为 ture")
    private Boolean readFlag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "消息")
    private TabPbNotice notice;


}