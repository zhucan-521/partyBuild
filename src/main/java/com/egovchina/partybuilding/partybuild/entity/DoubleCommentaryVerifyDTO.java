package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 双述双评审核DTO
 * Created by FanYanGen on 2019/4/25 14:13
 */
@Data
@ApiModel("双述双评审核DTO")
public class DoubleCommentaryVerifyDTO {

    @ApiModelProperty(value = "审核组织ID", required = true)
    @NotNull(message = "审核组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "双述双评主键ID", required = true)
    @NotNull(message = "双述双评主键ID不能为空")
    private Long commentaryId;

    @ApiModelProperty(value = "评定结果 dict", required = true)
    @NotNull(message = "评定结果不能为空")
    private Long checkResult;

    @ApiModelProperty(value = "审核说明")
    private String checkDesc;

}
