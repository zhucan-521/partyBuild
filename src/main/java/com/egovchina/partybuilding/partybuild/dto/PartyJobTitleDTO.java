package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "职业信息实体")
@Data
public class PartyJobTitleDTO {
    @ApiModelProperty(value = "主键")
    private Long jobTitleId;

    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "技术职务 dict JSZW")
    @NotNull(message = "技术职务不能为空")
    private Long post;

    @ApiModelProperty(value = "技术资格 dict JSZW")
    @NotNull(message = "技术资格不能为空")
    private Long qualifications;

    @ApiModelProperty(value = "获得日期 yyyy-MM-dd",example ="yyyy-MM-dd")
    @NotNull(message = "获得日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date getDate;

    @ApiModelProperty(value = "聘任起始日期 yyyy-MM-dd",example ="yyyy-MM-dd")
    @NotNull(message = "聘任起始日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointStartDate;

    @ApiModelProperty(value = "聘任终止日期 yyyy-MM-dd",example ="yyyy-MM-dd")
    @NotNull(message = "聘任终止日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointEndDate;
}
