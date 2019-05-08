package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * desc: 出国回国信息-数据传输对象
 * Created by FanYanGen on 2019/5/8 14:05
 */
@Data
@ApiModel("出国回国信息-数据传输对象")
public class AbroadDTO {

    @ApiModelProperty(value = "出国出境(主键)ID", required = true)
    private Long abroadId;

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "人员ID", required = true)
    @NotNull(message = "人员ID不能为空")
    private Long userId;

    // 出国信息

    @ApiModelProperty(value = "前往国家地区 字典:CGCJ", notes = "字典CGCJ")
    @NotNull(message = "出境国家不能为空")
    private Long goCountry;

    @ApiModelProperty(value = "出国原因 CGYY")
    private Long abroadReason;

    @ApiModelProperty(value = "党籍处理方式 CLQK")
    private Long registryMode;

    @ApiModelProperty(value = "出国时间", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "出国（境）日期不能为空")
    private Date abroadDate;

    @ApiModelProperty(value = "联系情况 LXQK")
    private Long linkStatus;

    @ApiModelProperty(value = "申请保留停止党籍时间", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registryReverseDate;

    // 回国信息

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
    private Integer isTransOut;

    @ApiModelProperty(value = "党员基本情况")
    private String baseStatus;

    @ApiModelProperty(value = "出国境定居时间", dataType = "Date", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date settleTime;

    @ApiModelProperty(value = "出国（境）事项说明")
    private String comment;

    @ApiModelProperty(value = "描述")
    private String description;

}
