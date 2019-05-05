package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * desc: 出国出境-数据传输对象
 * Created by FanYanGen on 2019/4/22 13:46
 */
@Data
@ApiModel("出国出境-数据传输对象")
public class ReturnAbroadDTO {

    @ApiModelProperty(value = "出国出境(主键)ID")
    private Long abroadId;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "人员Id")
    @NotNull(message = "人员ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "回国情况 HGQK", notes = "字典")
    private Long returnStatus;

    @ApiModelProperty(value = "恢复组织生活情况 HFZZSHQK")
    private Long returnActivitiesStatus;

    @ApiModelProperty(value = "批准恢复组织生活日期", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date allowActivitiesDate;

    @ApiModelProperty(value = "申请恢复组织生活日期", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyActivitiesDate;

    @ApiModelProperty(value = "应归时间", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planReturn;

    @ApiModelProperty(value = "实归时间", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    @ApiModelProperty(value = "组织关系出境时是否转往国外")
    private Byte isTransOut;

    @ApiModelProperty(value = "党员基本情况")
    private String baseStatus;

    @ApiModelProperty(value = "出国境定居时间", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date settleTime;

    @ApiModelProperty(value = "出国（境）事项说明")
    private String comment;

}
