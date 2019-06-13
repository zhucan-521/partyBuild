package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "身份核查反馈DTO")
@Data
public class IdentityVerificationFeedbackDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "党员id", required = true)
    @NotNull(message = "党员id不能为空")
    private Long userId;

    @ApiModelProperty(value = "反馈类型 dict SFHCFKLX", required = true)
    @NotNull(message = "反馈类型不能为空")
    private Long type;

    @ApiModelProperty(value = "反馈内容", required = true)
    @NotBlank(message = "反馈内容不能为空")
    private String content;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;
}