package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.util.UserContextHolder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 工作计划总结审核DTO
 *
 * @author Zhang Fan
 **/
@ApiModel("工作计划审核DTO")
@Data
public class WorkPlanReviewDTO {

    @ApiModelProperty(value = "计划id", required = true)
    @NotNull(message = "计划id不能为空")
    private Long planId;

    @ApiModelProperty(value = "审核结果 dict SHJG", required = true)
    @NotNull(message = "审核结果不能为空")
    private Long checkResult;

    @ApiModelProperty("审核说明")
    private String planCheck;

    @ApiModelProperty(value = "审核组织id", required = true)
    @NotNull(message = "审核组织不能为空")
    private Long checkOrg;

    @ApiModelProperty(value = "审核人", required = true)
    @NotNull(message = "审核人不能为空")
    private Long checkUser;

}
