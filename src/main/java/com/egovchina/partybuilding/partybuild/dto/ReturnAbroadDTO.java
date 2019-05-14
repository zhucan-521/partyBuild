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

    @ApiModelProperty(value = "出国出境(主键)ID", required = true)
    @NotNull(message = "出国出境(主键)ID不能为空")
    private Long abroadId;

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "人员ID", required = true)
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

    @ApiModelProperty(value = "实归时间", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    @ApiModelProperty(value = "组织关系出境时是否转往国外")
    private Integer isTransOut;

    @ApiModelProperty(value = "出国（境）事项说明")
    private String comment;

    @ApiModelProperty(value = "描述")
    private String description;

}
