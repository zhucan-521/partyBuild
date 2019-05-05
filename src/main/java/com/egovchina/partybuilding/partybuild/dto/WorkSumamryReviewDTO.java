package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.util.UserContextHolder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 工作总结审核DTO
 *
 * @author Zhang Fan
 **/
@ApiModel("工作总结审核DTO")
@Data
public class WorkSumamryReviewDTO {

    @ApiModelProperty(value = "计划id", required = true)
    @NotNull(message = "计划id不能为空")
    private Long planId;

    @ApiModelProperty(value = "审核结果 dict SHJG", required = true)
    @NotNull(message = "审核结果不能为空")
    private Long summaryCheckResult;

    @ApiModelProperty("审核说明")
    private String summaryCheck;

    private Long summaryCheckOrg;

    private Long summaryCheckUser;

    public Long getSummaryCheckOrg() {
        return UserContextHolder.getOrgId();
    }

    public Long getSummaryCheckUser() {
        return UserContextHolder.getUserId();
    }
}
