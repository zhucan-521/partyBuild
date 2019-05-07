package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "党建专干Dto")
@Data
public class SpecialWorkerDTO {
    @ApiModelProperty(value = "专干id")
    private Long specialWorkerId;

    @ApiModelProperty(value = "用户主键", required = true)
    @NotNull(message = "请传入userId")
    private Long userId;

    @ApiModelProperty(value = "组织id")
    private Long manageOrgId;

    @ApiModelProperty(value = "所属组织ID")
    private Long deptId;

    @ApiModelProperty(value = "身份证号码", required = true)
    @NotBlank(message = "身份证号码不能为空")
    private String idCardNo;

    @ApiModelProperty(value = "接任时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "接任时间不能为空")
    private Date arriveTime;

    @ApiModelProperty(value = "离任时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leftTime;
}
