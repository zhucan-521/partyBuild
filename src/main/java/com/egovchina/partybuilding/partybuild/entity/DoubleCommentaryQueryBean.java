package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 双述双评模块-匹配条件
 * Created by FanYanGen on 2019/4/25 14:13
 */
@Data
@ApiModel("双述双评模块-匹配条件")
public class DoubleCommentaryQueryBean {

    @ApiModelProperty(value = "双述双评主键ID", required = true)
    @NotNull(message = "双述双评主键ID不能为空")
    private Long commentaryId;

    @ApiModelProperty(value = "审核结果", required = true)
    @NotNull(message = "审核结果不能为空")
    private Long checkResult;

    @ApiModelProperty(value = "审核说明", required = true)
    private String checkDesc;

}
