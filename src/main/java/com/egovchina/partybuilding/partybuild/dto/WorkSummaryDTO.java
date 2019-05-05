package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 工作总结DTO
 *
 * @author Zhang Fan
 **/
@ApiModel("工作总结DTO")
@Data
public class WorkSummaryDTO {

    @ApiModelProperty(value = "计划ID", required = true)
    @NotNull(message = "计划id不能为空")
    private Long planId;

    @ApiModelProperty(value = "工作总结", required = true)
    @NotBlank(message = "总结内容不能为空")
    private String planSummary;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;
}
