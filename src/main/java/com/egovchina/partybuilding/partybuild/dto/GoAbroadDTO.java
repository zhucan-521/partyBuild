package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * desc: 出国信息-数据传输对象
 * Created by FanYanGen on 2019/4/26 14:37
 */
@Data
@ApiModel(" 出国信息-数据传输对象")
public class GoAbroadDTO {

    @ApiModelProperty(value = "出国出境(主键)ID", required = true)
    @NotNull(message = "出国出境(主键)ID不能为空")
    private Long abroadId;

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "人员ID", required = true)
    @NotNull(message = "人员ID不能为空")
    private Long userId;

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

}
