package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "组织关系接转，审核dto")
@Data
public class TransferItemDto {

    @ApiModelProperty(value = "主键Id")
    private Long itemId;

    @ApiModelProperty(value = "接转主键Id")
    private Long transferId;

    @ApiModelProperty(value = "审核状态：组织接转审核，数据字典")
    private Integer status;

    @ApiModelProperty(value = "审核结果：审批通过、审批未通过")
    private String optResult;
}
