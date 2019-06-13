package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 身份核查反馈VO
 *
 * @author Zhang Fan
 **/
@ApiModel(value = "身份核查反馈VO")
@Data
public class IdentityVerificationFeedbackVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "反馈类型 dict SFHCFKLX")
    @JsonSerialize(using = DictSerializer.class)
    private Long type;

    @ApiModelProperty(value = "反馈内容")
    private String content;

    @ApiModelProperty(value = "创建用户id")
    private Long createUserid;

    @ApiModelProperty(value = "创建用户姓名")
    private String createUsername;

    @ApiModelProperty(value = "创建时间 yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;
}
