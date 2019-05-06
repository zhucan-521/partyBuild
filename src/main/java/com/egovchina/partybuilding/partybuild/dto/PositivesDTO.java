package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "职务DTO")
@Data
public class PositivesDTO {

    @ApiModelProperty(value = "id")
    private Long positiveId;

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不为空")
    private Long userId;

    @ApiModelProperty(value = "职务类型 1党内职务 2行政职务", required = true)
    @NotNull(message = "职务类型不能为空")
    private Long positiveType;

    @ApiModelProperty(value = "任职 机构/党组织id", required = true)
    @NotNull(message = "任职党组织机构的主键不能为空")
    private Long positiveOrgId;

    @ApiModelProperty(value = "职务名称，字典：党内职务（DNZW）；行政职务（HZZW）", required = true)
    @NotNull(message = "任职党组织名称不能为空")
    private Long positiveName;

    @ApiModelProperty(value = "职务级别 ZWJB")
    private Long positiveLevel;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任职开始", required = true,example ="yyyy-MM-dd")
    @NotNull(message = "任职开始日期不能为空")
    private Date positiveStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任职结束", required = true,example ="yyyy-MM-dd")
    @NotNull(message = "任职结束日期不能为空")
    private Date positiveFinished;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "离职原因 dict LZYY")
    private Long leftReason;

    @ApiModelProperty(value = "离职方式 dict LZFS")
    private Long leftType;


}
