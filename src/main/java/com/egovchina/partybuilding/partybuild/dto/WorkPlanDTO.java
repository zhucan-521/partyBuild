package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * 工作计划DTO
 *
 * @author Zhang Fan
 **/
@ApiModel("工作计划DTO")
@Data
public class WorkPlanDTO {

    @ApiModelProperty(value = "计划ID")
    private Long planId;

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织id不能为空")
    private Long orgId;

    @ApiModelProperty(value = "计划年份", required = true)
    @NotNull(message = "计划年份不能为空")
    @Pattern(regexp = "\\d{4}", message = "计划年份无效")
    private Long planYear;

    private Date reportDate;

    @ApiModelProperty(value = "计划内容", required = true)
    @NotBlank(message = "计划内容不能为空")
    private String planContent;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

}
