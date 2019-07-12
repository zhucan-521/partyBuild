package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@ApiModel("书记简历")
public class ResumeVO {

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间不能空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;

    @ApiModelProperty(value = "结束时间")
    @NotNull(message = "结束时间不能为空")
    private Date endDate;

    @ApiModelProperty(value = "内容")
    @NotNull(message = "内容不能为空")
    private String content;

}
