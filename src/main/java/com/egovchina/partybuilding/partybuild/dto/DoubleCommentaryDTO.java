package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * desc: 双述双评新增-数据传输对象
 * Created by FanYanGen on 2019/4/24 16:17
 */
@Data
@ApiModel("双述双评新增-数据传输对象")
public class DoubleCommentaryDTO {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "党组织不能为空")
    private Long orgId;

    @ApiModelProperty(value = "工作总结所属年度 yyyy", required = true, dataType = "Long", example = "2019")
    @NotNull(message = "工作总结所属年度不能为空")
    private String planYear;

    @ApiModelProperty(value = "上报日期 yyyy-MM-dd", required = true, dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "上报日期不能为空")
    private Date reportDate;

    @ApiModelProperty(value = "结果情况 dict", required = true)
    @NotNull(message = "结果情况不能为空")
    private Long resultSituation;

}
