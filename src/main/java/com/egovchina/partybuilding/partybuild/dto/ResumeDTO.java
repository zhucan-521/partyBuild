package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@ApiModel("书记简历")
public class ResumeDTO {

    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd", required = true)
    @NotNull(message = "开始时间不能空", groups = Update.class)
    private String beginDate;

    @ApiModelProperty(value = "结束时间", example = "yyyy-MM-dd", required = true)
    @NotNull(message = "结束时间不能为空", groups = Update.class)
    private String endDate;

    @ApiModelProperty(value = "内容", required = true)
    @NotNull(message = "内容不能为空", groups = Update.class)
    private String content;

}
